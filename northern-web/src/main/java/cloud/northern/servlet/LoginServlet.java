package cloud.northern.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cloud.northern.util.PropertyUtil;

/**
 * Login<br>
 * redirect to AWS Cognito Login
 *
 * @author SoulP
 *
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String location = PropertyUtil.get("base.auth.url");
        location += PropertyUtil.get("aws.login.end_point");
        location += "?response_type=" + PropertyUtil.get("aws.login.response_type");
        location += "&client_id=" + PropertyUtil.get("aws.client_id");
        location += "&redirect_uri=" + PropertyUtil.get("base.redirect_uri");
        try {
            StringBuffer state = new StringBuffer();
            LocalDateTime dateTime = LocalDateTime.now();
            for (byte b : MessageDigest.getInstance("SHA-512")
                    .digest((request.getSession().getId() + ":" + dateTime.toString()).getBytes("UTF-8"))) {
                state.append(String.format("%02X", b));
            }
            location += "&state=" + state.toString();
            request.getSession().setAttribute("state", state.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        response.sendRedirect(location);
    }
}
