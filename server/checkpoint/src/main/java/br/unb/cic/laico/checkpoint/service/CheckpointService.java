package br.unb.cic.laico.checkpoint.service;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unb.cic.laico.checkpoint.business.CheckpointBO;
import br.unb.cic.laico.checkpoint.model.CheckpointInformation;

public class CheckpointService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String uploadPath;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.uploadPath = getServletConfig().getInitParameter("upload.path");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String command = request.getParameter("c");
		if ("start".equals(command)) {
			this.doCommandStart(request, response);

		} else if ("list".equals(command)) {
			this.doCommandList(request, response);

		} else if ("view".equals(command)) {
			this.doCommandView(request, response);

		} else if ("save".equals(command)) {
			this.doCommandSave(request, response);

		} else if ("upload".equals(command)) {
			this.doCommandUpload(request, response);

		} else if ("finish".equals(command)) {
			this.doCommandFinish(request, response);

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void doCommandStart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CheckpointBO checkpointBO = CheckpointBO.getInstance();
		CheckpointInformation info = checkpointBO.create(request.getRemoteAddr());

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(info.getKey());
	}

	private void doCommandList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CheckpointBO checkpointBO = CheckpointBO.getInstance();
		Set<String> keySet = checkpointBO.listKeys();

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{keys:[");
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			response.getWriter().write(key);
			if (it.hasNext()) {
				response.getWriter().write(",");
			}
		}
		response.getWriter().write("]}");
	}

	private void doCommandView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CheckpointBO checkpointBO = CheckpointBO.getInstance();
		CheckpointInformation info = checkpointBO.getByKey(request.getParameter("key"));
		if (info != null) {

			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(info.toString());

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Checkpoint information not found.");
		}
	}

	private void doCommandSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CheckpointBO checkpointBO = CheckpointBO.getInstance();
		CheckpointInformation info = checkpointBO.getByKey(request.getParameter("key"));
		if (info != null) {

			info.getCompletedSequences().add(request.getParameter("sequence"));
			response.setStatus(HttpServletResponse.SC_OK);

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Checkpoint information not found.");
		}
	}

	private void doCommandUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CheckpointBO checkpointBO = CheckpointBO.getInstance();
		CheckpointInformation info = checkpointBO.getByKey(request.getParameter("key"));
		if (info != null) {

			try {
				checkpointBO.persistContent(request.getParameter("key"), request.getParameter("sequence"),
						request.getParameter("description"), request.getParameter("content"),
						request.getServletContext().getRealPath(uploadPath));
				response.setStatus(HttpServletResponse.SC_OK);

			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			}

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Checkpoint information not found.");
		}
	}

	private void doCommandFinish(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CheckpointBO checkpointBO = CheckpointBO.getInstance();
		CheckpointInformation info = checkpointBO.getByKey(request.getParameter("key"));
		if (info != null) {

			info.setActive(false);
			info.setEndTime(new Date(System.currentTimeMillis()));
			response.setStatus(HttpServletResponse.SC_OK);

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Checkpoint information not found.");
		}
	}
}
