package ws;

import java.io.Serializable;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/sendmsg")
public class MessageEndPoint implements Serializable{

	private static final long serialVersionUID = -3538397497899905150L;
	
	static ArrayList<Session> sessions = new ArrayList<>();
	
	@OnMessage
	public void messageReceiver(String message)	{
		System.out.println("Messagem recebida: "+message);
	}
	
	@OnOpen
	public void onOpen(Session session){
		System.out.println("Sessao aberta: "+session.getId());
		sessions.add(session);
	}
	
	@OnClose
	public void onClose(Session session){
		System.out.println("Sessao fechou: "+session.getId());
		sessions.remove(session);
	}

	public static ArrayList<Session> getSessions() {
		return sessions;
	}

	public static void setSessions(ArrayList<Session> sessions) {
		MessageEndPoint.sessions = sessions;
	}

}
