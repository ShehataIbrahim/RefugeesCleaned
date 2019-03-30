package net.hitachifbbot.servlet;

import net.arnx.jsonic.JSON;
import net.hitachifbbot.model.PasswordHash;
import org.apache.commons.codec.DecoderException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordHashAPIServlet extends APIServlet {

    /**
     * POSTメソッド処理
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String password = req.getParameter("password");
        if(password == null || password.isEmpty()){
            error(resp,HTTPStatusCode.BadRequest,-1);
            return;
        }
        PasswordHash hash = null;
        try {
            hash = new PasswordHash(password);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | DecoderException e) {
            error(resp,HTTPStatusCode.InternalServerError,-2);
            return;
        }
        Result result = new Result();
        result.hash = hash.hash;
        result.salt = hash.salt;
        success(resp,result);
        return;
    }

    public static class Result{
        public int status = 0;
        public String hash;
        public String salt;
    }
}
