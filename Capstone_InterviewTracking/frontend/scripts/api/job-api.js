import { fetchHandler } from "./fetch-handler.js";

export function getJobsApi() {
  return fetchHandler("/jobs");
}
