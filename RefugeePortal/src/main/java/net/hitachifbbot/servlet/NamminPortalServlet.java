package net.hitachifbbot.servlet;

import com.refugees.db.model.RefugeeUser;
import net.hitachifbbot.DB;
import net.hitachifbbot.model.NamminUserData;
import net.hitachifbbot.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class NamminPortalServlet extends AppServlet {

    /**
     * GETメソッド処理
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
        	int id=-1;
        	NamminUserData userData= AppSession.getNamminUserData(req);
        	if(userData instanceof RefugeeUser)
        		id=((RefugeeUser)userData).getDBUserData().namminID;
        	else
        		id=userData.getDbUserData().namminID;
            if(DB.getLastScreeningAnswer(id).size() == 0){
                req.setAttribute("noScreening", true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        forwardJSP("/nammin/portal.jsp",req,resp);
    }
}
