document
  .getElementById("loginForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();
    const form = event.target;
    const data = {
      username: form.username.value,
      password: form.password.value,
    };

    fetch("/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Authentication failed");
        }
        return response.json();
      })
      .then((result) => {
        if (result.accessToken) {
          // Save tokens to localStorage
          localStorage.setItem("accessToken", result.accessToken);
          localStorage.setItem("refreshToken", result.refreshToken);
          console.log(
            "Tokens stored:",
            result.accessToken,
            result.refreshToken
          ); // Log the tokens

          // Redirect to the index page
          window.location.href = "/html/index.html";
        } else {
          alert("Authentication failed");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Authentication failed");
      });
  });
