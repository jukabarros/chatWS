var host = "ws://"+document.location.host + "/chatWS/sendmsg";
var wSocket = new WebSocket(host);
wSocket.binaryType = "arraybuffer";
var browserSupport = ("WebSocket" in window) ? true : false;
var inputText;
function initWebSocket() {

	if (browserSupport)
	{
		wSocket.onopen = function()
		{
			
			console.log("******* WebSocket Aberto");
		};
	}
	else
	{
		// The browser doesn't support WebSocket
		alert("WebSocket is NOT supported by your Browser!");
	}


	wSocket.onmessage = function(evt)
	{
		onMessage(evt);
	};

	wSocket.onclose = function(evt)
	{
		console.log("****** Socket Fechou!");
	};

	wSocket.onerror = function (evt){
		onError(evt);
	};
}

function sendText() {
	inputText = document.getElementById("createUser:nickname").value;
	var msgWS = '{"text":"' + inputText + '", "userSender": "usuario", "date":"", "dateStr":""}';
	wSocket.send(msgWS);
	console.log("******** JSON: " + msgWS);
}

function onMessage(evt) {
	// called when a message is received
	var received_msg = JSON.parse(evt.data);
	console.log("** Remetente: " + received_msg.userSender);
	console.log("** Texto: " + received_msg.text);
	console.log("** Data: " + received_msg.date);
	// Add Usuario na lista de usuarios
	// Enviar para o controlador userController e add na lista
	var textArea = document.getElementById("chatArea");
	textArea.value += "<b>"+received_msg.text +","+received_msg.date+"</b>\n";
	textArea.scrollTop = textArea.scrollHeight;
	
	inputText = document.getElementById("createUser:nickname").value = "";
	inputText.focus();
}

function onError(evt) {
	console.log("Deu merda: "+evt.data);
//	writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(msg) {
    var output = document.getElementById("output");
    output.innerHTML += msg + "<br>";
}
