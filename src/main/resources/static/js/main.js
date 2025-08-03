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
    nickname = document.querySelector('#nickname').value.trim();
    fullname = document.querySelector('#fullName').value.trim();
    if (nickname && fullName) {
        usernamePage.classList.add("hidden");
        chatPage.classList.remove("hidden");

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault()
}

    function onConnected(){

     stompClient.subscribe('/user/${username}/queue/messages', onMassageReceived);
     stompClient.subscribe('/user/public', onMassageReceived);

     // register the connected user
     stompClient.send('/app/user.addUser',
         {},
         JSON.stringify({nickName:nickname, fullName:fullname, status: 'ONLINE'}));
     document.querySelector('#connected-user-fullname').textContent = fullname;
     // find and display the connected user
        findAndDisplayConnectedUser().then()
    }
    
    async function findAndDisplayConnectedUser() {
        const connectedUserResponse = await fetch('/users');
        let connectedUsers = await connectedUserResponse.json();
        connectedUsers = connectedUsers.filter(user => user.nickName !== nickname);
        const connectedUserlist = document.getElementById('connectedUsers');

        connectedUsersList.innerHTML = '';

        connectedUsers.forEach(user => {
            appendUserElement(user, connectedUsersList);
            if (connectedUsers.index(user) < connectedUsers.length -1){
                const separator = document.createElement('li');
                separator.classList.add('separator');
                connectedUsersList.appendChild(separator);

            }
        })
          


    }
    
    function appendUserElement(user, connectedUsersList) {
        const listItem = document.createElement('li');
        listItem.classList.add('user-item');
        listItem.id = user.nickName;

        const userImaga = document.createElement('img');
        userImaga.src = '../img/user_icon.png';
        userImaga.alt = user.fullName;

        const userNameSpan = document.createElement('span');
        userNameSpan.textContent = user.fullName;

        const receivedMsgs = document.createElement('span');
        receivedMsgs.textContent = '0';
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
      messageContainer.appendChild(messageContainer);
      chatArea.appendChild(messageContainer);
    }
    
    function onError() {

    }

    function sendMessage(event) {
        const messageContent = messageInput.value.trim();
        if (messageContent && stompClient){
           const chatMessage = {
               senderId: nickname,
               recipientId: selectedUserId,
               content: messageContent,
               timestamp: new Date()
           };
           stompClient.send('/app/chat', {}, JSON.stringify(chatMessage));
           displayMessage(nickname, messageContent);
        }
        event.preventDefault();

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

         const notifiedUser = document.querySelector('${message.senderId}');
         if (notifiedUser && notifiedUser.classList.contains('active')){
             const nbrMsg = notifiedUser.querySelector('.nbr-msg');
             nbrMsg.classList.remove('hidden');
             nbrMsg.textContent = '';
         }
    }

    function onLogout() {
      stompClient.send('/app/user/disconnectUser',
          {},
          JSON.stringify({nickName:nickname, fullName:fullname, status: 'ONLINE'}));
      window.location.reload();
    }

    usernameForm.addEventListener('submit', connect, true);

    usernameForm.addEventListener('submit', sendMessage, true);

    logout.addEventListener('click', onLogout, true);

    window.onbeforeunload = () => onLogout();
