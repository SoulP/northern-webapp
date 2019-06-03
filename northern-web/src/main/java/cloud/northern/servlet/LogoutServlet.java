package cloud.northern.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cloud.northern.util.PropertyUtil;
import cloud.northern.util.Utility;

/**
 * Logout<br>
 * redirect to AWS Cognito Logout
 *
 * @author SoulP
 *
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String location = PropertyUtil.get("base.auth.url");
        location += PropertyUtil.get("aws.logout.end_point");
        location += "?client_id=" + PropertyUtil.get("aws.client_id");
        location += "&logout_uri=" + PropertyUtil.get("base.logout_uri");

        Utility.setCookie(request, response, "access_token", "");
        response.sendRedirect(location);
    }
}
