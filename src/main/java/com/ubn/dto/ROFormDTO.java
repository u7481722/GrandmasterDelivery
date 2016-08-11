package com.ubn.dto;

public class ROFormDTO {

	private String publickey;
	
	private String contentId;
	
	private String deviceId;
	
	private String token;
	
	private PurchaseDTO purchaseDTO;

	public String getPublickey() {
		return publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public PurchaseDTO getPurchaseDTO() {
		return purchaseDTO;
	}

	public void setPurchaseDTO(PurchaseDTO purchaseDTO) {
		this.purchaseDTO = purchaseDTO;
	}

	
}
