import { loginApi, signupApi } from "../api/auth-api.js";
import { saveAuthUser, saveToken } from "../utils/storage.js";

const loginForm = document.getElementById("login-form");

if (loginForm) {
  loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const error = document.getElementById("error");

    try {
      error.innerText = "";

      const data = await loginApi({ email, password });
      saveToken(data.token);
      saveAuthUser({
        email: data.email,
        role: data.role,
      });

      alert("Login success");
    } catch (err) {
      console.error(err);
      error.innerText = err.message;
    }
  });
}

const signupForm = document.getElementById("signup-form");

if (signupForm) {
  signupForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;
    const error = document.getElementById("error");

    try {
      error.innerText = "";

      await signupApi({ email, password, role });

      alert("Account created. Please check your email for the password setup link.");
      window.location.href = "login.html";
    } catch (err) {
      console.error(err);
      error.innerText = err.message;
    }
  });
}
