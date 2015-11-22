package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import app.ChatMemory;
import model.MessageWs;
import ws.MessageEndPoint;

@ManagedBean(name="chatController")
@SessionScoped
public class ChatController implements Serializable{

	private static final long serialVersionUID = -3770573459254222700L;
	
	private boolean createUserPanel;
	
	private String nickname;
	
	private String textMessage;
	
	private List<String> allNicknames;
	
	public ChatController() {
		this.createUserPanel = true;
		this.nickname = null;
		this.allNicknames = ChatMemory.allOnlines;
	}
	
	public String addUser() throws IOException, EncodeException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (!this.allNicknames.contains(this.nickname)){
			this.createUserPanel = false;
			this.allNicknames.add(this.nickname);
			
			MessageWs msgWS = new MessageWs();
			msgWS.setSource(getNickname());
			msgWS.setDestination("all");
			msgWS.setBody("Usuário "+this.nickname+" acabou de entrar");
			msgWS.setOperation("addUser");
			msgWS.setTimestamp(new Date());
			
			this.sendMsgWSBroadcast(msgWS);
			return "chat.xhtml?faces-redirect=true";
		}else{
			System.err.println("*** Ja existe usuario com esse apelido");
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Apelido já está sendo usado", "")); 
			return "index.xhtml?faces-redirect=true";
		}
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
	
	public String logoutUser() throws IOException, EncodeException{
		MessageWs msgWS = new MessageWs();
		msgWS.setSource(getNickname());
		msgWS.setDestination("all");
		msgWS.setBody("Usuário "+this.nickname+" acabou de sair");
		msgWS.setOperation("logoutUser");
		msgWS.setTimestamp(new Date());
		this.createUserPanel = true;
		this.allNicknames.remove(this.nickname);
		this.sendMsgWSBroadcast(msgWS);
		
		return "index.xhtml?faces-redirect=true";
	}
	
	private void sendMsgWSBroadcast(MessageWs msgws) throws IOException, EncodeException{
		List<Session> sessions = MessageEndPoint.getSessions();
		for (Session s : sessions){
			if (s.isOpen()) {
				s.getBasicRemote().sendObject(msgws);
			}
		}
		System.out.println("Mensagem enviada para todos: "+msgws.getOperation());
	}
	
	public void insertManyUsers(){
		for (int i = 0; i < 100; i++) {
			this.allNicknames.add("teste"+i);
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
