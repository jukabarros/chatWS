var host = "ws://"+document.location.host + "/chatWS/sendmsg";
var wSocket = new WebSocket(host);
wSocket.binaryType = "arraybuffer";
var browserSupport = ("WebSocket" in window) ? true : false;
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
	var inputTextArea = document.getElementById("chatPanel:insertMSG").value;
	var myNickName = document.getElementById("chatPanel:myNickName").innerHTML;
	var msgWS = '{"source":"' + myNickName + '", "destination": "all", "body":"'+ inputTextArea +'", "timestamp":""}';
	console.log("**** JSON: "+msgWS);
	wSocket.send(msgWS);
}

function onMessage(evt) {
	// called when a message is received
	var received_msg = JSON.parse(evt.data);
	
	var textArea = document.getElementById("chatPanel:chatArea");
	console.log("** Remetente: " + received_msg.source);
	console.log("** Destinatario: " + received_msg.destination);
	console.log("** Texto: " + received_msg.body);
	console.log("** Data: " + received_msg.timestamp);
	
	if (received_msg.body != "addUser"){
		
		textArea.value += received_msg.source +", "+received_msg.timestamp+"\n"
		+received_msg.body+"\n";
		
		textArea.scrollTop = textArea.scrollHeight;
		
		document.getElementById("chatPanel:insertMSG").value = "";
		document.getElementById("chatPanel:insertMSG").focus();
	}
}

function onError(evt) {
	console.log("Deu merda: "+evt.data);
//	writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(msg) {
    var output = document.getElementById("output");
    output.innerHTML += msg + "<br>";
}
