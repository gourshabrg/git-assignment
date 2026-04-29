import { SITE_CONFIG } from "../config/site-config.js";
import { fetchHandler } from "./fetch-handler.js";

const API_BASE_URL = SITE_CONFIG.apiUrl;

if (!API_BASE_URL) {
  throw new Error("API base URL is not configured");
}

export function loginApi(data) {
  return fetchHandler(`${API_BASE_URL}/auth/login`, {
    method: "POST",
    body: JSON.stringify(data),
  });
}

export function signupApi(data) {
  return fetchHandler(`${API_BASE_URL}/auth/register`, {
    method: "POST",
    body: JSON.stringify(data),
  });
}
