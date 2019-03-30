package net.hitachifbbot.servlet;

import net.arnx.jsonic.JSON;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class APIServlet extends HttpServlet{

    protected void success(HttpServletResponse resp, Object result) throws IOException {
        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.getWriter().append(JSON.encode(result));
    }

    protected void error(HttpServletResponse resp, HTTPStatusCode statusCode, int errorCode) throws IOException {
        resp.setStatus(200);
        resp.setContentType("application/json");
        CommonErrorResult result = new CommonErrorResult();
        result.result = errorCode;
        resp.getWriter().append(JSON.encode(result));
    }

    public enum HTTPStatusCode{
        OK(200),
        BadRequest(400),
        InternalServerError(500)
        ;
        private int code;

        HTTPStatusCode(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }
    }

    protected class CommonErrorResult{
        public int result;
    }
}
