package ws;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import model.MessageWs;

@ServerEndpoint(value = "/sendmsg", encoders=MessageWSEncoder.class, decoders = MessageWSDecoder.class)
public class MessageEndPoint implements Serializable{

	private static final long serialVersionUID = -3538397497899905150L;
	
	private static ArrayList<Session> sessions = new ArrayList<Session>();
	
	
	public MessageEndPoint() {
	}

	@OnMessage
	public void messageReceiver(Session session, MessageWs msgWS)	{
		try{
			for (Session s : session.getOpenSessions()){
				if (s.isOpen()) {
					s.getBasicRemote().sendObject(msgWS);
				}
			}
			System.out.println("Mensagem enviada para todos: "+msgWS.getOperation());

		}catch(IOException | EncodeException e){
			System.err.println("***** Deu merda onMessage: "+e.getMessage());
		}
	}
	
	@OnOpen
	public void onOpen(Session session){
		System.out.println("Sessao aberta ID: "+session.getId());
		sessions.add(session);
		// Para add atributos na sessao
		// session.getUserProperties().put("atributo", valor);
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason){
		System.out.println("Sessao fechou ID: "+session.getId());
		System.out.println(closeReason);
		sessions.remove(session);
	}
	
	@OnError
    public void onError(Throwable t) {
		System.out.println("*** Error Event");
        t.printStackTrace();
    }
	
	// GET AND SET
	public static ArrayList<Session> getSessions() {
		return sessions;
	}

	public static void setSessions(ArrayList<Session> sessions) {
		MessageEndPoint.sessions = sessions;
	}

}
