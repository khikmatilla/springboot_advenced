// js/new-chat.js
document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    const username = localStorage.getItem("username");

    fetch("/api/users/all", {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
        .then(res => res.json())
        .then(users => {
            const list = document.getElementById("userList");
            users.filter(user => user.username !== username)
                .forEach(user => {
                    const li = document.createElement("li");
                    li.textContent = user.username;
                    li.style.cursor = "pointer";
                    li.onclick = () => startChatWith(user.username);
                    list.appendChild(li);
                });
        });
});

function startChatWith(receiverUsername) {
    const token = localStorage.getItem("token");

    fetch("/api/chats/start", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({ receiverUsername })
    })
        .then(res => res.json())
        .then(data => {
            localStorage.setItem("chatId", data.chatId);
            localStorage.setItem("receiver", receiverUsername);
            window.location.href = "/chat.html";
        });
}
