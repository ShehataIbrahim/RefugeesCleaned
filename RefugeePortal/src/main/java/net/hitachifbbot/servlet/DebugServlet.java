package net.hitachifbbot.servlet;

import net.hitachifbbot.Consts;
import net.hitachifbbot.model.PasswordHash;
import net.hitachifbbot.utils.DBUtils;
import org.apache.commons.codec.DecoderException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class DebugServlet extends AppServlet {

    /**
     * GETメソッド処理
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        forwardJSP("/debug.jsp", req, resp);
    }

    /**
     * POST処理
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            final String mode = req.getParameter("mode");

            if (mode.equals("add_doctor")) {
                final String id = req.getParameter("id");
                final String pass = req.getParameter("pass");
                PasswordHash hash = new PasswordHash(pass);
                DBUtils.preparedStatement("INSERT INTO doctor_user (DOCTOR_ID,PASS_HASH,PASS_SALT) VALUES (?,?,?)",
                        ps -> {
                            ps.setString(1, id);
                            ps.setString(2, hash.hash);
                            ps.setString(3, hash.salt);
                            ps.executeUpdate();
                        });
            }
            forwardJSP("/debug.jsp", req, resp);
        } catch (SQLException | InvalidKeySpecException | NoSuchAlgorithmException | DecoderException e) {
            throw new IOException(e);
        }
    }
}
