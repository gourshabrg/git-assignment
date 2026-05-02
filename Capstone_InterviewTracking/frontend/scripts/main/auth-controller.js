import { loginApi, signupApi } from "../api/auth-api.js";
import { saveAuthUser, saveToken } from "../utils/storage.js";

const loginForm = document.getElementById("login-form");
const signupBtn = document.getElementById("signup-btn");
const loader = document.getElementById("loader");
const btnText = document.getElementById("btn-text");
const formContainer = document.querySelector(".form-container");

if (loginForm) {
  loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const error = document.getElementById("error");
    const gender = document.getElementById("gender").value;

    error.innerText = "";

    if (!/^[0-9]{10}$/.test(phone)) {
      error.innerText = "Enter valid 10 digit mobile number";
      return;
    }
    if (!gender) {
      error.innerText = "Please select gender";
      return;
    }
    try {
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
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value;
    const dob = document.getElementById("dob").value;
    const error = document.getElementById("error");

    if (new Date(dob) > new Date()) {
      alert("Date of birth cannot be in future");
      return;
    }
    try {
      //  START LOADING
      loader.classList.remove("hidden");
      btnText.textContent = "Sending...";
      signupBtn.disabled = true;
      formContainer.classList.add("blur");

      await signupCandidate({ name, email, phone, dob });

      alert("Verification email sent!");
      window.location.href = "login.html";
    } catch (error) {
      alert("Signup failed");
    } finally {
      //  STOP LOADING
      loader.classList.add("hidden");
      btnText.textContent = "Send setup link";
      signupBtn.disabled = false;
      formContainer.classList.remove("blur");
    }
  });
}

// Login redirect
const loginLink = document.getElementById("loginLink");
if (loginLink) {
  loginLink.addEventListener("click", () => {
    window.location.href = "login.html";
  });
}

// Home page
// const homeBtn = document.getElementById("home-btn");
// if (homeBtn) {
//   homeBtn.addEventListener("click", () => {
//     window.location.href = "../index.html";
//   });
// }

// ================= SET PASSWORD =================

const setPasswordForm = document.getElementById("set-password-form");

if (setPasswordForm) {
  setPasswordForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm-password").value;
    const error = document.getElementById("error");

    //  get token from URL
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get("token");

    error.innerText = "";

    if (!token) {
      error.innerText = "Invalid or missing token";
      return;
    }

    if (password.length < 6) {
      error.innerText = "Password must be at least 6 characters";
      return;
    }

    if (password !== confirmPassword) {
      error.innerText = "Passwords do not match";
      return;
    }

    try {
      await setPasswordApi({ token, password });

      alert("Password set successfully!");

      window.location.href = "login.html";
    } catch (err) {
      console.error(err);
      error.innerText = err.message;
    }
  });
}
