package ws;

import java.text.Format;
import java.text.SimpleDateFormat;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import model.MessageWs;

/**
 * @author juccelino
 */
public class MessageWSEncoder implements Encoder.Text<MessageWs>{

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(EndpointConfig config) {
		
	}

	@Override
	public String encode(MessageWs msgWS) throws EncodeException {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String dateStr = formatter.format(msgWS.getTimestamp());
		return Json.createObjectBuilder()
				.add("source", msgWS.getSource())
				.add("destination", msgWS.getDestination())
				.add("body", msgWS.getBody())
				.add("timestamp", dateStr)
				.add("operation", msgWS.getOperation())
				.build().toString();
	}

}
