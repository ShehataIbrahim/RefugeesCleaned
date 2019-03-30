package net.hitachifbbot.servlet;

import net.hitachifbbot.Consts;
import net.hitachifbbot.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DoctorLogoutServlet extends AppServlet {

    /**
     * POSTメソッド処理
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        AppSession.setUserData(req,null);
        resp.sendRedirect(Consts.LOGIN_DOCTOR_SERVLET_URL);
    }
}
