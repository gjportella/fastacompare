package br.unb.cic.laico.checkpoint.service;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unb.cic.laico.checkpoint.business.CheckpointBO;
import br.unb.cic.laico.checkpoint.model.CheckpointInformation;

public class CheckpointService extends HttpServlet {

	private static final long serialVersionUID = 1L;

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

		} else if ("detailLastSequence".equals(command)) {
			this.getCommandDetailLastCompletedSequence(request, response);

		} else if ("save".equals(command)) {
			this.doCommandSave(request, response);

		} else if ("finish".equals(command)) {
			this.doCommandFinish(request, response);

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void doCommandStart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CheckpointBO checkpointBO = CheckpointBO.getInstance();
		CheckpointInformation info = checkpointBO.create(
				request.getParameter("key"), request.getRemoteAddr());

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

	private void getCommandDetailLastCompletedSequence(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CheckpointBO checkpointBO = CheckpointBO.getInstance();
		CheckpointInformation info = checkpointBO.getByKey(request.getParameter("key"));
		if (info != null && info.isActive()) {
			
			String lastSequence = "";
			List<String> sequences = info.getCompletedSequences();
			if (sequences.size() > 0) {
				lastSequence = sequences.get(sequences.size() - 1);
			}

			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(lastSequence);

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
