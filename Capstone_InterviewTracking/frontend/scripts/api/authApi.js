const API_BASE_URL = window.APP_CONFIG?.API_BASE_URL;

if (!API_BASE_URL) {
  throw new Error("API base URL is not configured");
}

async function readErrorMessage(res, fallbackMessage) {
  const contentType = res.headers.get("content-type") || "";

  if (contentType.includes("application/json")) {
    const data = await res.json();
    return data.message || fallbackMessage;
  }

  const text = await res.text();
  return text || fallbackMessage;
}

async function loginApi(data) {
  const res = await fetch(`${API_BASE_URL}/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!res.ok) {
    throw new Error(await readErrorMessage(res, "Login failed"));
  }

  return res.json();
}

async function signupApi(data) {
  const res = await fetch(`${API_BASE_URL}/auth/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!res.ok) {
    throw new Error(await readErrorMessage(res, "Signup failed"));
  }

  return res.json();
}
