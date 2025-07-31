// js/dashboard.js
document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    const username = localStorage.getItem("username");
    if (!token) {
        window.location.href = "/login.html";
        return;
    }

    document.getElementById("currentUser").innerText = username;

    fetch("/api/chats/my", {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
        .then(res => res.json())
        .then(data => {
            const chatList = document.getElementById("chatList");
            data.forEach(chat => {
                const li = document.createElement("li");
                li.textContent = `Chat with ${chat.otherUser}`;
                li.style.cursor = "pointer";
                li.onclick = () => {
                    localStorage.setItem("chatId", chat.chatId);
                    localStorage.setItem("receiver", chat.otherUser);
                    window.location.href = "/chat.html";
                };
                chatList.appendChild(li);
            });
        });
});

function createNewChat() {
    window.location.href = "/new-chat.html";
}
