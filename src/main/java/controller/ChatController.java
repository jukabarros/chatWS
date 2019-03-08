package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
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
		this.nickname = this.nickname.trim();
		if (this.nickname.contains(" ")){
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não pode inserir usuário com espaço em branco", "")); 
			
			return null;
		}else if (!this.allNicknames.contains(this.nickname)){
			this.createUserPanel = false;
			this.allNicknames.add(this.nickname);
			
			MessageWs msgWS = new MessageWs();
			msgWS.setSource(this.nickname);
			msgWS.setDestination("all");
			msgWS.setBody("Usuário "+this.nickname+" acabou de entrar");
			msgWS.setOperation("addUser");
			msgWS.setTimestamp(new Date());
			this.sendMsgWSBroadcast(msgWS);
			// Memorizando o ultimo usuario online
			// Para add o atributo na sessao WS
			ChatMemory.lastUserOnline = getNickname();
			
			return "chat.xhtml?faces-redirect=true";
		}else{
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Apelido já está sendo usado", "")); 
			return null;
		}
	}
	
	public String logoutUser() throws IOException, EncodeException{
		this.createUserPanel = true;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		return "index.xhtml?faces-redirect=true";
	}
	
	/**
	 * Manda msg broadcast para todos os outros usuarios. 
	 * Importante: A sessao do WS ainda nao foi criada, quando o usuario envia
	 * a msg atraves do metodo addUser.
	 * 
	 * @param msgws JSON da MSG
	 * @throws IOException
	 * @throws EncodeException
	 */
	private void sendMsgWSBroadcast(MessageWs msgws) throws IOException, EncodeException{
		List<Session> sessions = MessageEndPoint.getSessions();
		for (Session s : sessions){
			if (s.isOpen()) {
				s.getBasicRemote().sendObject(msgws);
			}
		}
	}
	
	/**
	 * Metodo utilizado para testar o
	 * painel de usuarios onlines
	 */
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
