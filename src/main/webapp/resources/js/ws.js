var host = "ws://localhost:8080/chatWS/sendmsg";
var wSocket = new WebSocket(host);
var browserSupport = ("WebSocket" in window) ? true : false;

function initWebSocket() {

	if (browserSupport)
	{
		wSocket.onopen = function()
		{
			console.log("WebSocket Openned");
		};
	}
	else
	{
		// The browser doesn't support WebSocket
		alert("WebSocket is NOT supported by your Browser!");
	}


	wSocket.onmessage = function(event)
	{
		onMessage(event);
	};

	wSocket.onclose = function(evento)
	{
		console.log("Socket Fechou!");
		alert("Socket Fechou");
	};

	wSocket.onerror = function (evento){
		onError(evento);
	};
}

function sendText(evento) {
	console.log("******** Enviando MSG: " + evento.data);
	websocket.send(evento);
	alert("Enviando MSG: " + evento.data);
}

function onMessage(evento) {
	// called when a message is received
	var received_msg = evento.data;
	wSocket.send(received_msg);
	console.log("** MSG Recebida: " + received_msg);
	alert("MSG Recebida: " + evento.data);
}

function onError(evento) {
	console.log("Deu merda: "+evento.data);
	writeToScreen('<span style="color: red;">ERROR:</span> ' + evento.data);
}
