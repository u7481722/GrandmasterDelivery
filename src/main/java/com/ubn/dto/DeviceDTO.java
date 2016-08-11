package com.ubn.dto;

public class DeviceDTO {

	private int returnCode;
	
	private String returnMessage;
	
	private String deviceId;
	
	private String p12;

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getP12() {
		return p12;
	}

	public void setP12(String p12) {
		this.p12 = p12;
	}
	
	
}
