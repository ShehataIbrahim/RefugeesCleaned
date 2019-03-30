package net.hitachifbbot.servlet;

import net.arnx.jsonic.JSON;
import net.hitachifbbot.DB;
import net.hitachifbbot.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class NamminScreeningServlet extends AppServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<DB.ScreeningResultSet> answer = DB.getLastScreeningAnswer(AppSession.getNamminUserData(req).getDbUserData().namminID);
            HashMap<String, String> answers = new HashMap<>();
            for(DB.ScreeningResultSet a : answer){
                answers.put("" + a.screeningQID, a.answer);
            }
            req.setAttribute("answers", JSON.encode(answers));

            forwardJSP("/nammin/screening.jsp",req,resp);
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
