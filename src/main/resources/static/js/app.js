document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');
    const errorDiv = document.getElementById('error');

    loginForm.addEventListener('submit', async function (e) {
        e.preventDefault();

        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Login failed');
            }

            const data = await response.json();
            // JWT token ni localStorage'ga saqlaymiz
            localStorage.setItem('token', data.token);
            localStorage.setItem('username', username);

            // Shaxsiy kabinetga yo‘naltirish
            window.location.href = '/cabinet.html';
        } catch (error) {
            errorDiv.textContent = error.message;
        }
    });
});
