import { getJobsApi } from "../api/job-api.js";
const jobsGrid = document.getElementById("jobsGrid");
const jobModal = document.getElementById("jobModal");
const jobDetailsContent = document.getElementById("jobDetailsContent");

document.addEventListener("DOMContentLoaded", function () {
  loadJobs();
  setupScrollBehavior();
});

async function loadJobs() {
  if (!jobsGrid) return;

  try {
    jobsGrid.innerHTML = "<div class='loading'>Loading jobs...</div>";

    const response = await getJobsApi();
    console.log(response);

    const result = await response;

    console.log("API Response:", result);

    const jobs = result || [];

    jobsGrid.innerHTML = "";

    if (!jobs || jobs.length === 0) {
      jobsGrid.innerHTML = "<div class='loading'>No jobs available</div>";
      return;
    }

    jobs.forEach((job) => {
      const jobElement = createJobCard(job);
      jobsGrid.appendChild(jobElement);
    });
  } catch (error) {
    console.error(error);
    jobsGrid.innerHTML = "<div class='loading'>Error loading jobs</div>";
  }
}

function createJobCard(job) {
  const div = document.createElement("div");
  div.className = "job-card card-hover";
  div.onclick = () => openJobModal(job);

  const salaryRange = `₹${formatSalary(job.minSalary)} - ₹${formatSalary(job.maxSalary)}`;

  const experience = `${job.minExperience} - ${job.maxExperience} yrs`;

  div.innerHTML = `
    <div class="job-card-header">
      <h3 class="job-title">${escapeHtml(job.title)}</h3>
      <p class="job-company">Interview Platform</p>
    </div>
    
    <div class="job-meta">
      <div class="meta-item">
        <div class="meta-label">Location</div>
        <div class="meta-value">${escapeHtml(job.location)}</div>
      </div>
      <div class="meta-item">
        <div class="meta-label">Experience</div>
        <div class="meta-value">${experience}</div>
      </div>
    </div>
    
    <p class="job-description">${escapeHtml(job.description.substring(0, 100))}...</p>
    
    <div class="job-footer">
      <div class="job-salary">${salaryRange}</div>
      <div class="job-card-action">
        <a href="#">View More →</a>
      </div>
    </div>
  `;

  return div;
}

function openJobModal(job) {
  const salaryRange = `₹${formatSalary(job.minSalary)} - ₹${formatSalary(job.maxSalary)}`;
  const experience = `${job.minExperience} - ${job.maxExperience} yrs`;

  jobDetailsContent.innerHTML = `
    <div>
      <h2>${escapeHtml(job.title)}</h2>
      <p style="color: #0f766e; font-weight: 700; margin-bottom: 8px;">
        Interview Platform • ${escapeHtml(job.location)}
      </p>

      <div style="background: #f7fafc; padding: 16px; border-radius: 6px; margin: 20px 0;">
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px;">
          
          <div>
            <p style="font-weight: 700; font-size: 13px;">Salary Range</p>
            <p style="font-weight: 700; color: #0f766e;">${salaryRange}</p>
          </div>

          <div>
            <p style="font-weight: 700; font-size: 13px;">Experience</p>
            <p style="font-weight: 700;">${experience}</p>
          </div>

        </div>
      </div>

      <h3>Description</h3>
      <p>${escapeHtml(job.description)}</p>

      <h3>Skills</h3>
      <p>${escapeHtml(job.skills || "Not specified")}</p>

      <div style="margin-top: 20px;">
        <button class="btn" onclick="applyForJob(${job.id})">Apply Now</button>
        <button class="btn-secondary" onclick="closeJobModal()">Close</button>
      </div>
    </div>
  `;

  jobModal.classList.add("show");
}

function closeJobModal() {
  jobModal.classList.remove("show");
  jobDetailsContent.innerHTML = "";
}

function applyForJob(jobId) {
  const token = localStorage.getItem("token");
  const userRole = localStorage.getItem("role");

  if (!token) {
    alert("Please login to apply for jobs");
    window.location.href = "./auth/login.html";
    return;
  }

  if (!userRole || userRole.toLowerCase() !== "candidate") {
    alert("Only candidates can apply for jobs");
    return;
  }

  window.location.href = `./auth/login.html`;
}

function scrollToJobs() {
  const jobsSection = document.getElementById("jobs");
  if (jobsSection) {
    jobsSection.scrollIntoView({ behavior: "smooth" });
  }
}

function setupScrollBehavior() {
  document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
    anchor.addEventListener("click", function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute("href"));
      if (target) {
        target.scrollIntoView({ behavior: "smooth" });
      }
    });
  });
}

function formatSalary(salary) {
  return (
    new Intl.NumberFormat("en-US", {
      style: "decimal",
      maximumFractionDigits: 0,
    }).format(salary / 1000) + "K"
  );
}

function escapeHtml(text) {
  const map = {
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#039;",
  };
  return text.replace(/[&<>"']/g, (m) => map[m]);
}

window.addEventListener("click", function (event) {
  if (event.target === jobModal) {
    closeJobModal();
  }
});

window.scrollToJobs = scrollToJobs;
window.closeJobModal = closeJobModal;
window.applyForJob = applyForJob;
