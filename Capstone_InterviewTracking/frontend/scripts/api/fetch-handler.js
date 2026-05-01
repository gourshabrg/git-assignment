import { getToken } from "../utils/storage.js";
import { SITE_CONFIG } from "../config/site-config.js";

export async function fetchHandler(path, options = {}) {
  const headers = new Headers(options.headers || {});
  const token = getToken();

  // Set content type
  if (!headers.has("Content-Type") && options.body) {
    headers.set("Content-Type", "application/json");
  }

  // Add auth token if available
  if (token) {
    headers.set("Authorization", `Bearer ${token}`);
  }

  const url = `${SITE_CONFIG.API_URL}${path}`;

  const response = await fetch(url, {
    ...options,
    headers,
    body: options.body ? JSON.stringify(options.body) : null,
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
