import { loginApi, signupApi, setPasswordApi } from "../api/auth-api.js";
import { saveAuthUser, saveToken } from "../utils/storage.js";
import { validatePassword, validateEmail } from "../utils/validation.js";

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
    error.innerText = "";
    if (!email || !password) {
      error.innerText = "Email and password are required";
      return;
    }
    if (!validateEmail(email)) {
      error.innerText = "invalid email";
      return;
    }

    try {
      //const encodedPassword = encodeBase64(password);
      const data = await loginApi({ email, password });
      saveToken(data.token);
      saveAuthUser({
        email: data.email,
        role: data.role,
      });
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
    const gender = document.getElementById("gender").value;

    if (new Date(dob) > new Date()) {
      alert("Date of birth cannot be in future");
      return;
    }
    if (!validateEmail(email)) {
      error.innerText = "invalid email";
      return;
    }

    try {
      //  START LOADING
      loader.classList.remove("hidden");
      btnText.textContent = "Sending...";
      signupBtn.disabled = true;
      formContainer.classList.add("blur");

      await signupApi({ fullName: name, email, phone, dob, gender });

      window.location.href = "login.html";
    } catch (err) {
      console.error(err);
      if (err.message) {
        error.innerText = err.message;
      } else {
        error.innerText = "Something went wrong";
      }
    } finally {
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

    const errors = validatePassword(password);
    if (errors.length > 0) {
      error.innerText = errors.join("\n");
      return;
    }
    if (password !== confirmPassword) {
      error.innerText = "Passwords do not match";
      return;
    }

    try {
      // const encodedPassword = encodeBase64(password);
      await setPasswordApi({ token, password });
      window.location.href = "login.html";
    } catch (err) {
      console.error(err);
      error.innerText = err.message;
    }
  });
}
