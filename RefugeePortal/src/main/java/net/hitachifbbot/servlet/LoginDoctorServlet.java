package net.hitachifbbot.servlet;

import net.hitachifbbot.Consts;
import net.hitachifbbot.DB;
import net.hitachifbbot.model.DoctorUser;
import net.hitachifbbot.model.DoctorUserData;
import net.hitachifbbot.model.PasswordHash;
import net.hitachifbbot.session.AppSession;
import net.hitachifbbot.utils.DBUtils;
import org.apache.commons.codec.DecoderException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class LoginDoctorServlet extends AppServlet {

    /**
     * GETメソッド処理
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        forwardJSP("/login/doctor.jsp",req,resp);
    }

    /**
     * POST処理
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            final String user = req.getParameter("user");
            final String password = req.getParameter("password");
            if(user == null || password == null){// 認証失敗
                req.setAttribute("retry", Boolean.TRUE);
                forwardJSP("/login/doctor.jsp",req,resp);
                return;
            }
            DoctorUser doctorUser = DB.getDoctorUserByID(user);

            if(doctorUser == null){// 認証失敗
                req.setAttribute("retry", Boolean.TRUE);
                forwardJSP("/login/doctor.jsp",req,resp);
                return;
            }

            PasswordHash hash = new PasswordHash(doctorUser.passHash,doctorUser.passSalt);

            if(hash.isMatch(password)){ // 認証成功
                DoctorUserData userData = new DoctorUserData();
                userData.setDbUserData(doctorUser);
                AppSession.setUserData(req,userData);
                resp.sendRedirect(Consts.CATEGORY_LIST_URL);
                return;
            }else{ // 認証失敗
                req.setAttribute("retry", Boolean.TRUE);
                forwardJSP("/login/doctor.jsp",req,resp);
                return;
            }
        } catch (SQLException | InvalidKeySpecException | NoSuchAlgorithmException | DecoderException e) {
            throw new IOException(e);
        }
    }
}
