'use strict';
const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const connectingElement = document.querySelector('#connecting');
const messageInput = document.querySelector('#message');
const chatArea = document.querySelector('#chat-messages');
const logout = document.querySelector('#logout');

let stompClient = null;
let nickname = null;
let fullname = null;
let selectedUserId = null;

function connect(event) {
    chatPage.style.display = "none";
    nickname = document.querySelector('#nickname').value.trim();
    fullname = document.querySelector('#fullName').value.trim();
    if (nickname && fullName) {
        usernamePage.style.display = "none";
        chatPage.style.display = "flex";
        usernamePage.classList.add("hidden");
        chatPage.classList.remove("hidden");

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }

    const user = JSON.parse(localStorage.getItem("user"));

    if(user) {
        usernamePage.style.display = "none";
        chatPage.style.display = "flex";
        nickname = user?.nickName;
        fullname = user?.fullName;
        if (user?.nickName && user?.fullName) {
            usernamePage.classList.add("hidden");
            chatPage.classList.remove("hidden");
            const socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);

            stompClient.connect({}, onConnected, onError);
        }

    }

    if(event) {
        event.preventDefault()
    }

}

    function onConnected(){

     stompClient.subscribe('/user/' + nickname + '/queue/messages', onMassageReceived);
     stompClient.subscribe('/user/public', onMassageReceived);

     // register the connected user
     stompClient.send('/app/user.addUser',
         {},
         JSON.stringify({nickName:nickname, fullName:fullname, status: 'ONLINE'}));
     // document.querySelector('#connected-user-fullname').textContent = fullname;

        localStorage.setItem("user", JSON.stringify({nickName:nickname, fullName:fullname, status: 'ONLINE'}))
     // find and display the connected user
        findAndDisplayConnectedUser().then()
    }
    
    async function findAndDisplayConnectedUser() {
        const connectedUsersList = document.querySelector('#connectedUsers');
        const connectedUserResponse = await fetch('/users');
        let connectedUsers = await connectedUserResponse.json();
        connectedUsers = connectedUsers.filter(user => user.nickName !== nickname);

        connectedUsersList.innerHTML = '';
        connectedUsers.forEach((user, index) => {
            appendUserElement(user, connectedUsersList);

            // if (index < connectedUsers.length - 1) {
            //     const separator = document.createElement('li');
            //     separator.classList.add('separator');
            //     connectedUsersList.appendChild(separator);
            // }
        });
          


    }
    
    function appendUserElement(user, connectedUsersList) {
        const listItem = document.createElement('li');
        listItem.classList.add('user-item');
        listItem.id = user.nickName;
        listItem.style.display = "flex";
        listItem.style.alignItems = "center";
        listItem.style.gap = "10px"
        const userImaga = document.createElement('img');
        userImaga.src = '../img/user.jfif';
        userImaga.alt = user.fullName;
        userImaga.width = 30;
        userImaga.height = 30;
        userImaga.style.borderRadius = "100%";
        userImaga.style.border = "1px solid #000"
        const userNameSpan = document.createElement('span');
        userNameSpan.textContent = user.fullName;

        const receivedMsgs = document.createElement('span');
        const isOnline = user?.status === "ONLINE"
        receivedMsgs.textContent = "";
        receivedMsgs.style.width = "10px";
        receivedMsgs.style.height = "10px";
        receivedMsgs.style.borderRadius = "100%";
        receivedMsgs.style.backgroundColor = isOnline ? "green" : "E0E0E0";

        receivedMsgs.classList.add('nbr-msg', 'hidden');

        listItem.appendChild(userImaga);
        listItem.appendChild(userNameSpan);
        listItem.appendChild(receivedMsgs);

        listItem.addEventListener('click', userItemClick)

        connectedUsersList.appendChild(listItem);

    }

    function userItemClick () {
        document.querySelectorAll('.user-item').forEach(item => {
            item.classList.remove('active');
            messageForm.classList.remove('hidden');
        });
            const clickedUser = event.currentTarget;
            clickedUser.classList.add('active');

            selectedUserId = clickedUser.getAttribute('id');
            fetchAndDisplayUserChat().then();

            const nbrMsg = clickedUser.querySelector('.nbr-msg');
            nbrMsg.classList.add('hidden');
    }

    async function fetchAndDisplayUserChat(){
        const userChatResponse = await fetch('/messages/'+ nickname +'/'+selectedUserId);
        const userChat = await userChatResponse.json();
        chatArea.innerHTML = '';

        userChat.forEach(chat => {
            displayMessage(chat.senderId, chat.content);
        });
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    function displayMessage(senderId, content) {
      const messageContainer = document.createElement('div');
      messageContainer.classList.add('message');
      if (senderId === nickname){
          messageContainer.classList.add('sender');
      }else {
          messageContainer.classList.add('receiver');
      }
      const message = document.createElement('p');
      message.textContent = content;
      messageContainer.appendChild(message);
      chatArea.appendChild(messageContainer);
    }
    
    function onError() {

    }

    function sendMessage(event) {
        event.preventDefault();
        let messageContent = messageInput.value.trim();
        if (messageContent && stompClient){
           const chatMessage = {
               senderId: nickname,
               recipientId: selectedUserId,
               content: messageContent,
           };
           stompClient.send('/app/chat', {}, JSON.stringify(chatMessage));
           displayMessage(nickname, messageContent);
          messageInput.value = ""
            chatArea.scrollTop = chatArea.scrollHeight;
        }


    }

   async function onMassageReceived(payload) {
         await findAndDisplayConnectedUser();
         const message = JSON.parse(payload.body);
         if (selectedUserId && selectedUserId === message.senderId){
             displayMessage(message.senderId, message.content);
             chatArea.scrollTop = chatArea.scrollHeight;
         }
         if (selectedUserId){
             document.querySelector('${selectedUserId}').classList.add('active');
         }else {
             messageForm.classList.add('hidden');
         }


       const notifiedUser = document.querySelector(`#${message.senderId}`);
         if (notifiedUser && notifiedUser.classList.contains('active')){
             const nbrMsg = notifiedUser.querySelector('.nbr-msg');
             nbrMsg.classList.remove('hidden');
             nbrMsg.textContent = '';
         }
    }

    function onLogout() {
      stompClient.send('/app/user/disconnectUser',
          {},
          JSON.stringify({nickName:nickname, fullName:fullname, status: 'OFFLINE'}));
      localStorage.removeItem("user")
      window.location.reload();
    }

    usernameForm.addEventListener('submit', connect, true);

    messageForm.addEventListener('submit', sendMessage, true);

    logout.addEventListener('click', onLogout);

    // window.onbeforeunload = () => onLogout();
