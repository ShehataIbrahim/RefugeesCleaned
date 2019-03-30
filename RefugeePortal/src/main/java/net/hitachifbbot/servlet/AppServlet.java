package net.hitachifbbot.servlet;

import net.hitachifbbot.model.NamminUserData;
import net.hitachifbbot.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class AppServlet extends HttpServlet {

    protected HashSet<String> convertedLangCodes = null;

    protected synchronized HashSet<String> getConvertedLangCodes(HttpServletRequest req){
        if(convertedLangCodes != null){
            return convertedLangCodes;
        }
        convertedLangCodes = new HashSet<>();
        File jspDir = new File(req.getServletContext().getRealPath("/WEB-INF/jsp/nammin/"));
        for (File child : jspDir.listFiles()) {
            if(child.isDirectory() && !child.getName().startsWith("_")){
                convertedLangCodes.add(child.getName());
            }
        }
        return convertedLangCodes;
    }

    protected void forwardJSP(String pageName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(pageName.startsWith("/nammin/")){ // 難民申請時は言語コードによって遷移先を変える
            NamminUserData data = AppSession.getNamminUserData(req);
            if (data != null){
                String langCode = data.getDbUserData().translateLangCode;
                if(!getConvertedLangCodes(req).contains(langCode)){
                    langCode = "en";
                }
                req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/nammin/" + langCode + pageName.replace("/nammin",""))
                        .forward(req, resp);
                return;
            }
        }

        // JSPに飛ばす
        req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/" + pageName).forward(req, resp);
    }
}
