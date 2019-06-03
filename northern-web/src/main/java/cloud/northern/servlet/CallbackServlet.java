package cloud.northern.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cloud.northern.bean.AWSOAuthTokenBean;
import cloud.northern.util.ContentType;
import cloud.northern.util.Header;
import cloud.northern.util.PropertyUtil;
import cloud.northern.util.Utility;

/**
 * Callback from OAuth
 *
 * @author SoulP
 *
 */
@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String state = request.getParameter("state");
        if (state == null) { throw new IOException("error: state is null"); }

        if (!state.equals(request.getSession().getAttribute("state"))) {
            throw new IOException("error: invalid state");
        }

        String code = request.getParameter("code");
        if (code != null) {
            Map<String, String> headers = new LinkedHashMap<String, String>();
            headers.put(Header.CONTENT_TYPE.toString(), ContentType.POST.getValue());
            headers.put(Header.AUTHORIZATION.toString(), "Basic " + Utility
                    .encodeBase64(PropertyUtil.get("aws.client_id") + ":" + PropertyUtil.get("aws.client_secret")));

            Map<String, String> parameters = new LinkedHashMap<String, String>();
            parameters.put("grant_type", PropertyUtil.get("asw.oauth.token.grant_type"));
            parameters.put("client_id", PropertyUtil.get("aws.client_id"));
            parameters.put("redirect_uri", PropertyUtil.get("base.redirect_uri"));
            parameters.put("code", code);

            String url = PropertyUtil.get("base.auth.url");
            url += PropertyUtil.get("aws.oauth.token.end_point");
            String json = Utility.httpPost(url, headers, parameters);

            AWSOAuthTokenBean oauthToken = Utility.json2obj(json, AWSOAuthTokenBean.class);
            if (oauthToken.getAccess_token() == null || oauthToken.getAccess_token().equals("")) {
                throw new IOException("error: access_token is null");
            }

            Utility.setCookie(request, response, "access_token", oauthToken.getAccess_token(), null, 60 * 60 * 24 * 7,
                    PropertyUtil.get("base.domain"), null, true);
        }
        response.sendRedirect(PropertyUtil.get("base.url"));
    }
}
