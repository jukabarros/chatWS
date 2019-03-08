package ws;

import java.util.List;

import javax.websocket.Session;

public class ThreadSender {

	public ThreadSender() {

	}

	public static void sendMessageBroadCast(String message){
		List<Session> sessions = MessageEndPoint.getSessions();
		for (Session s : sessions){
			if (s.isOpen()) {
				s.getAsyncRemote().sendText(message);
			}
		}
	}

}
