import { fetchHandler } from "./fetch-handler.js";

export function loginApi(data) {
  return fetchHandler(`/auth/login`, {
    method: "POST",
    body: data,
  });
}

export function signupApi(data) {
  return fetchHandler(`/auth/signup`, {
    method: "POST",
    body: data,
  });
}
