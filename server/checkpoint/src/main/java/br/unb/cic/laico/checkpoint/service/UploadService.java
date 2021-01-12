package br.unb.cic.laico.checkpoint.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import br.unb.cic.laico.checkpoint.business.CheckpointBO;
import br.unb.cic.laico.checkpoint.model.CheckpointInformation;

@MultipartConfig
public class UploadService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String uploadPath;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.uploadPath = getServletConfig().getInitParameter("upload.path");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Part filePart = request.getPart("content");
		InputStream fileContent = filePart.getInputStream();

		final int bufferSize = 1024;
		final char[] buffer = new char[bufferSize];
		final StringBuilder content = new StringBuilder();
		Reader in = new InputStreamReader(fileContent, StandardCharsets.UTF_8);
		int charsRead;
		while((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
			content.append(buffer, 0, charsRead);
		}
		
		CheckpointBO checkpointBO = CheckpointBO.getInstance();
		CheckpointInformation info = checkpointBO.getByKey(request.getParameter("key"));
		if (info != null) {

			try {
				checkpointBO.persistContent(request.getParameter("key"), request.getParameter("sequence"),
						request.getParameter("description"), content.toString(),
						request.getServletContext().getRealPath(uploadPath));
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			}

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Checkpoint information not found.");
		}
	}
}
