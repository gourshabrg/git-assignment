// LOGIN
const loginForm = document.getElementById("loginForm");

if (loginForm) {
  const prefill = getSignupLoginPrefill();

  if (prefill.email && prefill.password) {
    document.getElementById("email").value = prefill.email;
    document.getElementById("password").value = prefill.password;
  }

  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();

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

// SIGNUP
const signupForm = document.getElementById("signupForm");

if (signupForm) {
  signupForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;
    const error = document.getElementById("error");

    try {
      error.innerText = "";

      await signupApi({ email, password, role });
      saveSignupLoginPrefill(email, password);

      alert("Signup success");
      window.location.href = "login.html";
    } catch (err) {
      console.error(err);
      error.innerText = err.message;
    }
  });
}
