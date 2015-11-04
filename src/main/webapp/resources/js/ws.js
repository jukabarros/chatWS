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
	inputText = document.getElementById("chatPanel:insertMSG").value;
	var msgWS = '{"text":"' + inputText + '", "userSender": "usuario", "date":"", "dateStr":""}';
	wSocket.send(msgWS);
}

function onMessage(evt) {
	// called when a message is received
	var received_msg = JSON.parse(evt.data);
//	console.log("** Remetente: " + received_msg.userSender);
//	console.log("** Texto: " + received_msg.text);
//	console.log("** Data: " + received_msg.date);
	var textArea = document.getElementById("chatPanel:chatArea");
	textArea.value += received_msg.text +", "+received_msg.date+"\n"
					+received_msg.text+"\n";
	
	textArea.scrollTop = textArea.scrollHeight;
	
	document.getElementById("chatPanel:insertMSG").value = "";
	document.getElementById("chatPanel:insertMSG").focus();
}

function onError(evt) {
	console.log("Deu merda: "+evt.data);
//	writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(msg) {
    var output = document.getElementById("output");
    output.innerHTML += msg + "<br>";
}
