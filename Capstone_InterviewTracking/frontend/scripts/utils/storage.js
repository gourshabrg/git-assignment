function saveToken(token) {
  localStorage.setItem("token", token);
}

function getToken() {
  return localStorage.getItem("token");
}

function saveAuthUser(user) {
  localStorage.setItem("email", user.email);
  localStorage.setItem("role", user.role);
}

function getAuthRole() {
  return localStorage.getItem("role");
}

function clearAuth() {
  localStorage.removeItem("token");
  localStorage.removeItem("email");
  localStorage.removeItem("role");
}

function saveSignupLoginPrefill(email, password) {
  sessionStorage.setItem("signupEmail", email);
  sessionStorage.setItem("signupPassword", password);
}

function getSignupLoginPrefill() {
  const email = sessionStorage.getItem("signupEmail");
  const password = sessionStorage.getItem("signupPassword");

  sessionStorage.removeItem("signupEmail");
  sessionStorage.removeItem("signupPassword");

  return { email, password };
}
