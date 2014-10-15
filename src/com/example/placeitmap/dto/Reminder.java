package com.example.placeitmap.dto;

public class Reminder {
	public static final int WHEN_I_LEAVE=1, WHEN_I_ARRIVE =0;
	private int ID;
	private String remiderText;
	private double latitude;
	private double longitude ;
	private float radius;
	private int reminderType ;
	private int isReminded ;
	private java.util.Date created;
	private int priority;
	public int getID() {
		return ID;
	}
	public int getisReminded(){
	    return isReminded;
	}
	public void setisReminded(int ISReminded){
		isReminded=ISReminded;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getRemiderText() {
		return remiderText;
	}
	public void setRemiderText(String remiderText) {
		this.remiderText = remiderText;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public int getReminderType() {
		return reminderType;
	}
	public void setReminderType(int reminderType) {
		this.reminderType = reminderType;
	}
	public java.util.Date getCreated() {
		return created;
	}
	public void setCreated(java.util.Date created) {
		this.created = created;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
