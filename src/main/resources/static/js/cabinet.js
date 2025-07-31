document.addEventListener('DOMContentLoaded', async function () {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');

    if (!token || !username) {
        window.location.href = '/login.html';
        return;
    }

    document.getElementById('currentUser').textContent = username;

    try {
        const response = await fetch('/api/users/online', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });

        if (!response.ok) {
            throw new Error('Unable to load users');
        }

        const users = await response.json();
        renderUserList(users);
    } catch (err) {
        console.error(err);
        alert('Error loading user list');
    }
});

function renderUserList(users) {
    const userList = document.getElementById('userList');
    userList.innerHTML = '';

    const currentUsername = localStorage.getItem('username');

    users.forEach(user => {
        if (user.username === currentUsername) return; // o'zini chiqarma

        const div = document.createElement('div');
        div.className = 'user';
        div.textContent = user.username;
        div.onclick = () => {
            localStorage.setItem('chatRecipient', user.username);
            window.location.href = '/chat.html';
        };
        userList.appendChild(div);
    });
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('chatRecipient');
    window.location.href = '/login.html';
}
