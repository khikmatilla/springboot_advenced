let stompClient;
let chatId;
let username;
let receiverUsername;

document.addEventListener("DOMContentLoaded", () => {
    username = localStorage.getItem("username");
    chatId = localStorage.getItem("chatId");
    receiverUsername = localStorage.getItem("receiver");
    console.log("receiverUsername from localStorage:", receiverUsername);


    if (!chatId || !username || !receiverUsername) {
        window.location.href = "/dashboard.html";
        return;
    }

    document.getElementById("receiverName").innerText = receiverUsername;

    const socket = new SockJS("/ws/chat"); // Backenddagi /ws endpoint
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        // Chat xonasiga obuna bo‘lish
        stompClient.subscribe(`/topic/messages/${receiverUsername}`, (message) => {
            const msg = JSON.parse(message.body);
            displayMessage(msg.sender, msg.content);
        });

        // Chat tarixini olish
        fetch(`/api/messages/${receiverUsername}`, {
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then(res => res.json())
            .then(messages => {
                messages.forEach(msg => displayMessage(msg.sender, msg.content));
            });
    });
});

document.getElementById("messageInput").addEventListener("keydown", function(event) {
    if (event.key === "Enter" && !event.shiftKey) {
        event.preventDefault(); // Enter bosishda forma yuborilmasligi uchun
        sendMessage(); // Sizning xabaringizni yuboruvchi funksiyangiz
    }
});

function sendMessage() {
    const input = document.getElementById("messageInput");
    const content = input.value.trim();
    if (content === "") return;

    const message = {
        content: content,
        receiverUsername: receiverUsername,
        username: localStorage.getItem("username") || "N/A"
    };



    // STOMP orqali yuborish
    stompClient.send("/app/chat", {}, JSON.stringify(message));
    // displayMessage(username, content); // o‘zingiz yuborgan xabarni ko‘rsatish
    input.value = "";
    input.focus();
}
console.log("userName from localStorage:", senderUsername);


function displayMessage(sender, content) {
    const chatBox = document.getElementById("chatBox");
    const div = document.createElement("div");

    div.textContent = `${sender}: ${content}`;
    div.className = sender === username ? "my-message" : "their-message";

    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
}
