package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import model.MessageWs;
import ws.MessageEndPoint;

@ManagedBean(name="userController")
@ViewScoped
public class UserController implements Serializable{

	private static final long serialVersionUID = -3770573459254222700L;
	
	private boolean createUserPanel;
	
	private String nickname;
	
	private String textMessage;
	
	private List<String> allNicknames;
	

	public UserController() {
		System.out.println("*** Construtor!!");
		this.createUserPanel = true;
		this.nickname = null;
		this.allNicknames = new ArrayList<>();
	}
	
	public String addUser() throws IOException, EncodeException {
		
		MessageWs msgWS = new MessageWs();
		msgWS.setSource(getNickname());
		msgWS.setDestination("all");
		msgWS.setBody("addUser");
		msgWS.setTimestamp(new Date());
		
		if (!this.allNicknames.contains(this.nickname)){
			this.createUserPanel = false;
			this.allNicknames.add(this.nickname);
			this.sendMsgWSBroadcast(msgWS);
		}else{
			System.out.println("Ja existe usuario com esse apelido");
		}
		return null;
	}
	
	public String sendMessage() throws IOException, EncodeException{
		MessageWs msgWS = new MessageWs();
		msgWS.setSource(getNickname());
		msgWS.setDestination("all");
		msgWS.setBody(getTextMessage());
		msgWS.setTimestamp(new Date());
		
		this.sendMsgWSBroadcast(msgWS);
		
		return null;
	}
	
	private void sendMsgWSBroadcast(MessageWs msgws) throws IOException, EncodeException{
		List<Session> sessions = MessageEndPoint.getSessions();
		for (Session s : sessions){
			if (s.isOpen()) {
				System.out.println("Enviando MSG para: " + s.getId());
				s.getBasicRemote().sendObject(msgws);
			}
		}
	}
	
	// GET AND SET
	
	public boolean isCreateUserPanel() {
		return createUserPanel;
	}

	public void setCreateUserPanel(boolean createUserPanel) {
		this.createUserPanel = createUserPanel;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<String> getAllNicknames() {
		return allNicknames;
	}

	public void setAllNicknames(List<String> allNicknames) {
		this.allNicknames = allNicknames;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

}
