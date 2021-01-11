package br.unb.cic.laico.checkpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.unb.cic.laico.checkpoint.util.DateUtil;

public class CheckpointInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean active;
	private String key;
	private String ipAddress;
	private Date startTime;
	private Date endTime;
	private List<String> completedSequences;

	public CheckpointInformation() {
		completedSequences = new ArrayList<String>();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<String> getCompletedSequences() {
		return completedSequences;
	}

	public void setCompletedSequences(List<String> completedSequences) {
		this.completedSequences = completedSequences;
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder("{");
		str.append("active").append(":").append(this.active).append(",");
		str.append("key").append(":").append(this.key).append(",");
		str.append("ipAddress").append(":").append(this.ipAddress).append(",");
		str.append("startTime").append(":").append(DateUtil.formatDateToText(this.startTime)).append(",");
		if (this.endTime != null) {
			str.append("endTime").append(":").append(DateUtil.formatDateToText(this.endTime)).append(",");
		} else {
			str.append("endTime").append(":").append("").append(",");
		}
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
