package ws;

import java.io.StringReader;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import model.MessageWs;


public class MessageWSDecoder implements Decoder.Text<MessageWs>{

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(EndpointConfig config) {
		
	}

	@Override
	public MessageWs decode(String messageText) throws DecodeException {
		MessageWs msgWS = new MessageWs();
		Date dateOfReceived = new Date();
		JsonObject jsonObj = Json.createReader(new StringReader(messageText))
				.readObject();
		msgWS.setSource(jsonObj.getString("source"));
		msgWS.setDestination(jsonObj.getString("destination"));
		msgWS.setBody(jsonObj.getString("body"));
		msgWS.setTimestamp(dateOfReceived);
		msgWS.setOperation(jsonObj.getString("operation"));
		return msgWS;
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

}
