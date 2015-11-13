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
	// Melhorar o JSON (caracteres especiais)
	// fazer a validacao dos destinatarios ('@')
	var msgWS = '{"source":"' + myNickName + '", "destination": "all", "body":"'+ inputTextArea +
	'", "timestamp":"", "operation":"sendText"}';
	wSocket.send(msgWS);
}

function onMessage(evt) {
	// called when a message is received
	var receivedMsg = JSON.parse(evt.data);
	
	if (receivedMsg.operation == "addUser"){
		welcomeMSG(receivedMsg.body, receivedMsg.timestamp);
		addUserOnlinePanel(receivedMsg.source);
		
	}else if (receivedMsg.operation == "logoutUser"){
		logoutUser(receivedMsg.source);

	}else{
		addMSGArea(receivedMsg.source, receivedMsg.body, 
				receivedMsg.timestamp, receivedMsg.destination);
	}
}

function welcomeMSG(body, timestamp){
	var textArea = document.getElementById("chatPanel:chatArea");
	textArea.value += timestamp+"\n"+body+"\n";
	textArea.scrollTop = textArea.scrollHeight;
}

function addUserOnlinePanel(user){
	// Add o index da lista no id da label
	// Corrigir (nao atualiza qnd novo usuario entra)
	if (user != null){
		var myIndexOfList = document.getElementById("chatPanel:myIndexOflist").value;
		var liElement = document.createElement("li");
		liElement.innerHTML = '<li class="ui-datalist-item"><label id="chatPanel:allOnlines:'+myIndexOfList+
		':userOnline">'+user+'</label></li>';

		document.getElementById("chatPanel:allOnlines_list").appendChild(liElement);
		
	}
	
	console.log("** Novo Usuário Online: " + user);
}

function addMSGArea(user, body, timestamp, destination){
	var textArea = document.getElementById("chatPanel:chatArea");
	textArea.value += user +", "+ timestamp+"\n"+body+"\n";
	
	textArea.scrollTop = textArea.scrollHeight;
	
	document.getElementById("chatPanel:insertMSG").value = "";
	document.getElementById("chatPanel:insertMSG").focus();
	
}

function logoutUser(user){
	document.getElementById("chatPanel:allOnlines_list").innerHTML;
	
	console.log("*** Usuário logout: "+user);
	// Remover elemento HTML
}

function onError(evt) {
	console.log("Deu merda: "+evt.data);
	writeToScreen('<span style="color: red;">ERROR: </span> ' + evt.data);
	wSocket.onclose(evt);
}

function writeToScreen(msg) {
    var textArea = document.getElementById("chatPanel:chatArea");
	textArea.value += msg+"\n";
}
