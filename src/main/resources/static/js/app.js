document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("loginForm");
    const errorDiv = document.getElementById("error");

    loginForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        const userName = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const rememberMe = document.getElementById("rememberMe");
        console.log(userName)
        try {
            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ userName, password, rememberMe: "true"})
            });

            if (!response.ok) {
                const text = await response.text();
                errorDiv.textContent = `Login failed: ${text}`;
                return;
            }

            const result = await response.json(); // token ni kutamiz
            if (result.token) {
                localStorage.setItem("token", result.token);
                localStorage.setItem("username", username);
                window.location.href = "/dashboard.html"; // login bo‘lgach shaxsiy kabinetga o‘tamiz
            } else {
                errorDiv.textContent = "Token not found in response.";
            }
        } catch (error) {
            errorDiv.textContent = "Error during login: " + error.message;
        }
    });
});
