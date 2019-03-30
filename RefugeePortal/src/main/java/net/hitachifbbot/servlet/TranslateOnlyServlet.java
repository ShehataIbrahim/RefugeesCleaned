package net.hitachifbbot.servlet;

import net.hitachifbbot.utils.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class TranslateOnlyServlet extends AppServlet {
	private static final long serialVersionUID = -2198136201415633365L;
	private static final HashSet<String> whiteList;
    static {
        whiteList = new HashSet<String>();
        whiteList.addAll(Arrays.asList(
                "/nammin/ncds.html",
                "/nammin/mch.html",
                "/error/500.html"
        ));
    }

    /**
     * GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getRequestURI();
        if(whiteList.contains(path)){ 
            if (path.endsWith(".html")){ 
                path = path.substring(0,path.length() - ".html".length());
            }
            if (path.startsWith("/")){ 
                path = path.substring("/".length(), path.length());
            }
            Template.responseTranslated(path, new HashMap<>(),req,resp);
            return;
        }
        resp.sendRedirect("/error/500.html");
    }
}
