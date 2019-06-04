package cloud.northern.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cloud.northern.common.util.PropertyUtil;

/**
 * Signout
 *
 * @author SoulP
 *
 */
@WebServlet("/signout")
public class SignoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(PropertyUtil.get("base.url"));
    }
}
