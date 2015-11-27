package model;

import java.io.Serializable;
import java.util.Date;

public class MessageWs implements Serializable{
	
	private static final long serialVersionUID = -2169420340503250005L;

	private String source; // origin
	
	private String destination;
	
	private String body; // content msg
	
	private String operation; // function
	
	private Date timestamp;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "MessageWs [source=" + source + ", destination=" + destination
				+ ", body=" + body + ", operation=" + operation + "]";
	}


}
