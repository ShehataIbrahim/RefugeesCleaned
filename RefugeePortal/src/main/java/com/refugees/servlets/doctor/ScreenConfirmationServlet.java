package com.refugees.servlets.doctor;

import com.refugees.db.service.ScreeningService;
import net.hitachifbbot.servlet.AppServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ScreenConfirmationServlet extends AppServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
			ScreeningService.confirmScreen(id);
			return;
	}
}
