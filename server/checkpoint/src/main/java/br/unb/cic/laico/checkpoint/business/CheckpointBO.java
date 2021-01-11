package br.unb.cic.laico.checkpoint.business;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
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

	public CheckpointInformation create(String ipAddress) {
		CheckpointInformation info = new CheckpointInformation();
		info.setActive(true);
		info.setIpAddress(ipAddress);
		info.setStartTime(new Date(System.currentTimeMillis()));
		info.setKey(randomString.nextString());
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
}