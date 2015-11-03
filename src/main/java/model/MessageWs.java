package model;

import java.io.Serializable;
import java.util.Date;

public class MessageWs implements Serializable{
	
	private static final long serialVersionUID = -666065130845000251L;

	private String text;
	
	private String userSender;
	
	private Date dateOfReceived;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserSender() {
		return userSender;
	}

	public void setUserSender(String userSender) {
		this.userSender = userSender;
	}

	public Date getDateOfReceived() {
		return dateOfReceived;
	}

	public void setDateOfReceived(Date dateOfReceived) {
		this.dateOfReceived = dateOfReceived;
	}

}
