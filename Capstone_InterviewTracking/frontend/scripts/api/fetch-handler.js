import { getToken } from "../utils/storage.js";

export async function fetchHandler(path, options = {}) {
  const headers = new Headers(options.headers || {});
  const token = getToken();

  if (!headers.has("Content-Type") && options.body) {
    headers.set("Content-Type", "application/json");
  }

  if (token) {
    headers.set("Authorization", `Bearer ${token}`);
  }

  const response = await fetch(path, {
    ...options,
    headers,
  });

  const data = await readResponse(response);

  if (!response.ok) {
    throw new Error(data?.message || "Request failed");
  }

  return data?.data ?? data;
}

async function readResponse(response) {
  const contentType = response.headers.get("content-type") || "";

  if (contentType.includes("application/json")) {
    return response.json();
  }

  const text = await response.text();
  return text ? { message: text } : null;
}
