
var roomId = document.getElementById('room-id').value;
var username = document.getElementById('loginUser-username').value;
function enterRoom(socket){

    var enterMsg=
        {
            "type" : "ENTER",
            "roomId": roomId,
            "sender":username,
            "msg":""
        };
    socket.send(JSON.stringify(enterMsg));
}
let socket = new WebSocket("ws://localhost:8888/ws/chat");

socket.onopen = function (e) {

    console.log('open server!')
    enterRoom(socket);
};
socket.onclose=function(e){

    console.log('disconnet');
}

socket.onerror = function (e){

    console.log(e);
}

//메세지 수신했을 때 이벤트.
socket.onmessage = function (e) {

    console.log(e.data);
    let msgArea = document.querySelector('.msgArea');
    let newMsg = document.createElement('div');
    newMsg.innerText=e.data;
    msgArea.append(newMsg);
}


//메세지 보내기 버튼 눌렀을 떄..
function sendMsg() {

    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    let content=document.querySelector('.content').value;
    var talkMsg={
        "type" : "TALK",
        "roomId":roomId,
        "sender":username,
        "msg":content};
    socket.send(JSON.stringify(talkMsg));

    fetch('/message/create',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify(talkMsg)
        })
        .then(response =>{
            return response.json()
        })
        .then(data => {

        })
        .catch(error => console.error('데이터를 받지 못했습니다.', error));
}

function quit(){

    var quitMsg={
        "type" : "QUIT",
        "roomId": roomId,
        "sender":username,
        "msg":""};
    socket.send(JSON.stringify(quitMsg));
    socket.close();
    location.href="/chat/chatList";
}