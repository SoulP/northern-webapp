package cloud.northern.auth;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cloud.northern.bean.BadRequestBean;
import cloud.northern.util.PropertyUtil;

/**
 * GitHub OAuth
 *
 * @author SoulP
 *
 */
@Path("GitHub")
public class GitHubOAuth {

    @Context
    private HttpServletRequest  request;

    @Context
    private HttpServletResponse response;

    @GET
    @Path("/")
    public void auth() throws URISyntaxException, NoSuchAlgorithmException, IOException {
        String location = PropertyUtil.get("github.oauth.authorize_url");
        location += "?client_id=" + PropertyUtil.get("github.oauth.client_id");
        location += "&redirect_uri=" + PropertyUtil.get("github.oauth.redirect_uri");
        location += "&scope=" + PropertyUtil.get("github.oauth.scope");
        StringBuffer state = new StringBuffer();
        LocalDateTime dateTime = LocalDateTime.now();
        for (byte b : MessageDigest.getInstance("SHA-512")
                .digest((request.getSession().getId() + ":" + dateTime.toString()).getBytes("UTF-8"))) {
            state.append(String.format("%02X", b));
        }
        location += "&state=" + state.toString();
        request.getSession().setAttribute("state", state.toString());

        response.sendRedirect(location);
    }

    @GET
    @Path("callback")
    public Response callback(@QueryParam("code") String code, @QueryParam("state") String State) {
        String state = request.getParameter("state");
        if (state == null) {
            return Response.status(Status.BAD_REQUEST).entity(new BadRequestBean("state is null")).build();
        }

        if (!state.equals(request.getSession().getAttribute("state"))) {
            return Response.status(Status.UNAUTHORIZED).entity(new BadRequestBean("invalid state")).build();
        }

        return null;
    }
}
