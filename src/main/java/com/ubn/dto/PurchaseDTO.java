package com.ubn.dto;

public class PurchaseDTO {

	private String sDuration_type;
	
	private String sStart_datetime;
	
	private String sEnd_datetime;
	
	private String sOffline_allowed;
	
	private String sOffline_counts_allowed;

	public String getsDuration_type() {
		return sDuration_type;
	}

	public void setsDuration_type(String sDuration_type) {
		this.sDuration_type = sDuration_type;
	}

	public String getsStart_datetime() {
		return sStart_datetime;
	}

	public void setsStart_datetime(String sStart_datetime) {
		this.sStart_datetime = sStart_datetime;
	}

	public String getsEnd_datetime() {
		return sEnd_datetime;
	}

	public void setsEnd_datetime(String sEnd_datetime) {
		this.sEnd_datetime = sEnd_datetime;
	}

	public String getsOffline_allowed() {
		return sOffline_allowed;
	}

	public void setsOffline_allowed(String sOffline_allowed) {
		this.sOffline_allowed = sOffline_allowed;
	}

	public String getsOffline_counts_allowed() {
		return sOffline_counts_allowed;
	}

	public void setsOffline_counts_allowed(String sOffline_counts_allowed) {
		this.sOffline_counts_allowed = sOffline_counts_allowed;
	}
	
    
}
