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
		// Remover usuario Online
		console.log("****** Socket Fechou! "+evt.data);
	};

	wSocket.onerror = function (evt){
		onError(evt);
	};
}

function sendText() {
	var inputTextArea = document.getElementById("chatPanel:insertMSG").value;
	var myNickName = document.getElementById("chatPanel:myNickName").innerHTML;
	// Melhorar o JSON (caracteres especiais)
	// fazer a validacao dos destinatarios ('@')
	var msgWS = '{"source":"' + myNickName + '", "destination": "all", "body":"'+ inputTextArea +'", "timestamp":"", "operation":"sendText"}';
	wSocket.send(msgWS);
}

function onMessage(evt) {
	// called when a message is received
	var receivedMsg = JSON.parse(evt.data);
	
	console.log("** Remetente: " + receivedMsg.source);
	console.log("** Destinatario: " + receivedMsg.destination);
	console.log("** Texto: " + receivedMsg.body);
	console.log("** Op: " + receivedMsg.operation);
	console.log("** Data: " + receivedMsg.timestamp);
	
	if (receivedMsg.operation != "addUser"){
		
		addMSGArea(receivedMsg.source, receivedMsg.body, receivedMsg.timestamp, receivedMsg.destination);
		
	}else{
		welcomeMSG(receivedMsg.source, receivedMsg.body, receivedMsg.timestamp);
		addUserOnlinePanel(receivedMsg.source);
	}
}

function welcomeMSG(newUser, body, timestamp){
	// Refresh na lista
	var textArea = document.getElementById("chatPanel:chatArea");
	textArea.value += timestamp+"\n"+body+"\n";
	textArea.scrollTop = textArea.scrollHeight;
}

function addUserOnlinePanel(user){
	document.getElementById("chatPanel:allOnlines_list").innerHTML += '<li class="ui-datalist-item"><label>'+user+'</label></li>';
	console.log("** All Onlines: "+allOnlines);
	console.log("** Novo Usuário Online: " + user);
}

function addMSGArea(user, body, timestamp, destination){
	var textArea = document.getElementById("chatPanel:chatArea");
	textArea.value += user +", "+ timestamp+"\n"+body+"\n";
	
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
