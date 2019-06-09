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
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import cloud.northern.bean.DiscordTokenBean;
import cloud.northern.common.util.ContentType;
import cloud.northern.common.util.Header;
import cloud.northern.common.util.PropertyUtil;
import cloud.northern.common.util.Utility;

/**
 * Discord OAuth
 *
 * @author SoulP
 *
 */
@Path("Discord")
public class DiscordOAuth {
    @Context
    private HttpServletRequest  request;

    @Context
    private HttpServletResponse response;

    @GET
    public void auth() throws URISyntaxException, NoSuchAlgorithmException, IOException {
        String location = PropertyUtil.get("discord.oauth.authorize_url");
        location += "?client_id=" + PropertyUtil.get("discord.oauth.client_id");
        location += "&redirect_uri=" + PropertyUtil.get("discord.oauth.redirect_uri");
        location += "&response_type=" + PropertyUtil.get("discord.oauth.response_type");
        location += "&scope=" + PropertyUtil.get("discord.oauth.scope");
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
    public void callback(@QueryParam("code") String code, @QueryParam("state") String state) throws IOException {
        if (state == null) {
            response.sendError(Status.BAD_REQUEST.getStatusCode(), "state is null");
        }

        if (!state.equals(request.getSession().getAttribute("state"))) {
            response.sendError(Status.UNAUTHORIZED.getStatusCode(), "invalid state");
        }

        DiscordTokenBean oauthToken = getToken(code, false);

        Utility.setCookie(request, response, "provider", "Discord", null, 60 * 60 * 24 * 7,
                PropertyUtil.get("base.domain"), null, true);
        Utility.setCookie(request, response, "access_token", oauthToken.getAccess_token(), null,
                oauthToken.getExpires_in(), PropertyUtil.get("base.domain"), null, true);
        Utility.setCookie(request, response, "refresh_token", oauthToken.getRefresh_token(), null, 60 * 60 * 24 * 7,
                PropertyUtil.get("base.domain"), null, true);

        response.sendRedirect(PropertyUtil.get("base.url"));
    }

    @GET
    @Path("refresh")
    public void refresh(@CookieParam("refresh_token") String refresh_token) throws IOException {
        if (refresh_token == null) {
            response.sendError(Status.BAD_REQUEST.getStatusCode(), "refresh_token is null");
        }

        DiscordTokenBean oauthToken = getToken(refresh_token, true);

        Utility.setCookie(request, response, "provider", "Discord", null, 60 * 60 * 24 * 7,
                PropertyUtil.get("base.domain"), null, true);
        Utility.setCookie(request, response, "access_token", oauthToken.getAccess_token(), null,
                oauthToken.getExpires_in(), PropertyUtil.get("base.domain"), null, true);
        Utility.setCookie(request, response, "refresh_token", oauthToken.getRefresh_token(), null, 60 * 60 * 24 * 7,
                PropertyUtil.get("base.domain"), null, true);

        response.sendRedirect(PropertyUtil.get("base.url"));
    }

    private DiscordTokenBean getToken(String value, boolean refresh) throws IOException {
        if (value == null) {
            response.sendError(Status.BAD_REQUEST.getStatusCode(), ((refresh) ? "refresh_token" : "code") + " is null");
        }

        Map<String, String> headers = new LinkedHashMap<String, String>();
        headers.put(Header.CONTENT_TYPE.toString(), ContentType.POST.getValue());

        Map<String, String> parameters = new LinkedHashMap<String, String>();
        parameters.put("client_id", PropertyUtil.get("discord.oauth.client_id"));
        parameters.put("client_secret", PropertyUtil.get("discord.oauth.client_secret"));
        parameters.put("grant_type", (refresh) ? "refresh_token" : "authorization_code");
        parameters.put((refresh) ? "refresh_token" : "code", value);
        parameters.put("redirect_uri", PropertyUtil.get("discord.oauth.redirect_uri"));
        parameters.put("scope", PropertyUtil.get("discord.oauth.scope"));

        String json = "";
        try {
            json = Utility.httpPost(PropertyUtil.get("discord.oauth.token_url"), headers, parameters);
        } catch (IOException e) {
            response.sendError(Status.UNAUTHORIZED.getStatusCode(),
                    "invalid " + ((refresh) ? "refresh_token" : "code"));
        }

        DiscordTokenBean oauthToken = Utility.json2obj(json, DiscordTokenBean.class);
        if (oauthToken.getAccess_token() == null || oauthToken.getAccess_token().equals("")) {
            throw new IOException("error: access_token is null");
        }

        return oauthToken;
    }
}
