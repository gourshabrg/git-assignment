export function validatePassword(password) {
  const errors = [];

  if (password.length < 6) {
    errors.push("At least 6 characters required");
  }

  if (!/[0-9]/.test(password)) {
    errors.push("Must include a number");
  }

  if (!/[!@#$%^&*]/.test(password)) {
    errors.push("Must include a special character");
  }

  return errors;
}

export function validateEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}
