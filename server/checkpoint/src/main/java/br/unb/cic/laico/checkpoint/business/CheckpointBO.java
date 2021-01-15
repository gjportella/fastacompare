package br.unb.cic.laico.checkpoint.business;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.unb.cic.laico.checkpoint.business.base.BusinessObject;
import br.unb.cic.laico.checkpoint.model.CheckpointInformation;
import br.unb.cic.laico.checkpoint.util.RandomString;

public class CheckpointBO extends BusinessObject {

	private static final Logger logger = LogManager.getLogger(CheckpointBO.class);
	
	private static final int RANDOM_STRING_LENGTH = 8;

	private static CheckpointBO instance;

	private Map<String, CheckpointInformation> checkpointMap;
	private RandomString randomString;

	private CheckpointBO() {
		checkpointMap = new HashMap<String, CheckpointInformation>();
		randomString = new RandomString(RANDOM_STRING_LENGTH);
	}

	public static synchronized CheckpointBO getInstance() {
		if (instance == null) {
			instance = new CheckpointBO();
		}
		return instance;
	}

	public CheckpointInformation create(String key, String ipAddress) {

		CheckpointInformation info = new CheckpointInformation();
		if (key != null && key.trim().length() > 0) {
			info.setKey(key);
		} else {
			info.setKey(randomString.nextString());
		}

		info.setActive(true);
		info.setIpAddress(ipAddress);
		info.setStartTime(new Date(System.currentTimeMillis()));
		checkpointMap.put(info.getKey(), info);
		return info;
	}

	public CheckpointInformation getByKey(String key) {
		return checkpointMap.get(key);
	}

	public Set<String> listKeys() {
		return checkpointMap.keySet();
	}

	public void persistContent(String key, String sequence, String description,
			String content, String directoryPath)
			throws Exception {

		String outputFilePath = directoryPath + File.separator +
				key + "_" + sequence + "_" + description + ".txt";
		logger.info("Writing file: " + outputFilePath);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outputFilePath), "UTF-8"));
			output.write(content);
			output.flush();

		} catch (IOException ex) {
			throw ex;

		} finally {
			if (output != null) {
				output.close();
			}
		}
	}
	
	public String getCompletedSequencesAsJSON(String key) {
		
		CheckpointInformation info = getByKey(key);
		List<String> completedSequences = info.getCompletedSequences();

		StringBuilder str = new StringBuilder("{");
		str.append("completedSequences:[");
		Iterator<String> it = completedSequences.iterator();
		while (it.hasNext()) {
			String sequenceName = it.next();
			str.append("{");
			str.append("sequenceName").append(":").append(sequenceName);
			str.append("}");
			if (it.hasNext()) {
				str.append(",");
			}
		}
		str.append("]");
		str.append("}");
		return str.toString();
	}
}
