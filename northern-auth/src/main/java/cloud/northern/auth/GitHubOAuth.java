package cloud.northern.auth;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import cloud.northern.bean.GitHubTokenBean;
import cloud.northern.common.util.ContentType;
import cloud.northern.common.util.Header;
import cloud.northern.common.util.PropertyUtil;
import cloud.northern.common.util.Utility;

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
    public void callback(@QueryParam("code") String code, @QueryParam("state") String State) throws IOException {
        String state = request.getParameter("state");
        if (state == null) {
            response.sendError(Status.BAD_REQUEST.getStatusCode(), "state is null");
        }

        if (!state.equals(request.getSession().getAttribute("state"))) {
            response.sendError(Status.UNAUTHORIZED.getStatusCode(), "invalid state");
        }

        if (code == null) {
            response.sendError(Status.BAD_REQUEST.getStatusCode(), "code is null");
        }

        Map<String, String> headers = new LinkedHashMap<String, String>();
        headers.put(Header.ACCEPT.toString(), ContentType.JSON.getValue());

        Map<String, String> parameters = new LinkedHashMap<String, String>();
        parameters.put("client_id", PropertyUtil.get("github.oauth.client_id"));
        parameters.put("client_secret", PropertyUtil.get("github.oauth.client_secret"));
        parameters.put("code", code);
        parameters.put("redirect_uri", PropertyUtil.get("github.oauth.redirect_uri"));
        parameters.put("state", state);

        String json = "";
        try {
            json = Utility.httpPost(PropertyUtil.get("github.oauth.token_url"), headers, parameters);
        } catch (IOException e) {
            response.sendError(Status.UNAUTHORIZED.getStatusCode(), "invalid code");
        }

        GitHubTokenBean oauthToken = Utility.json2obj(json, GitHubTokenBean.class);
        if (oauthToken.getAccess_token() == null || oauthToken.getAccess_token().equals("")) {
            throw new IOException("error: access_token is null");
        }

        Utility.setCookie(request, response, "provider", "GitHub", null, 60 * 60 * 24 * 7,
                PropertyUtil.get("base.domain"), null, true);
        Utility.setCookie(request, response, "access_token", oauthToken.getAccess_token(), null, 60 * 60 * 24 * 7,
                PropertyUtil.get("base.domain"), null, true);

        response.sendRedirect(PropertyUtil.get("base.url"));
    }

}