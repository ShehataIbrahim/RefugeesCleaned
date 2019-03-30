package net.hitachifbbot.servlet;

import net.hitachifbbot.model.PasswordHash;
import net.hitachifbbot.node.HubotCaller;
import org.apache.commons.codec.DecoderException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class NotificationRequestAPIServlet extends APIServlet {

    /**
     * POSTメソッド処理
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String namminIDStr = req.getParameter("n_id");
        if(namminIDStr == null || namminIDStr.isEmpty()){
            error(resp,HTTPStatusCode.BadRequest,-1);
            return;
        }
        try {
            int namminID = Integer.parseInt(namminIDStr);
            HubotCaller.requestNotification(namminID);
            success(resp,new Result());
        }catch (NumberFormatException e){
            error(resp,HTTPStatusCode.BadRequest,-2);
        }
        return;
    }

    public static class Result{
        public int status = 0;
    }
}
