document
  .getElementById("registrationForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent form from reloading

    let isValid = validateForm();

    if (isValid) {
      alert("Registration successful!");
    }
  });

// Add event listeners for blur validation (when user exits a field)
document.getElementById("name").addEventListener("blur", validateName);
document.getElementById("username").addEventListener("blur", validateUsername);
document.getElementById("password").addEventListener("blur", validatePassword);
document
  .getElementById("repeat-password")
  .addEventListener("blur", validateRepeatPassword);
document.getElementById("email").addEventListener("blur", validateEmail);
document.getElementById("male").addEventListener("change", validateGender);
document.getElementById("male").addEventListener("blur", validateGender);
document.getElementById("female").addEventListener("change", validateGender);

document
  .getElementById("telephone")
  .addEventListener("blur", validateTelephone);
document
  .getElementById("registrationForm")
  .addEventListener("reset", clearAllErrors);

// Validation functions

function validateName() {
  const name = document.getElementById("name").value.trim();
  return checkField("nameError", name !== "", "Name is required.");
}

function validateUsername() {
  const username = document.getElementById("username").value.trim();
  return checkField("usernameError", username !== "", "Username is required.");
}

function validatePassword() {
  const password = document.getElementById("password").value;
  if (password === "")
    return checkField("passwordError", false, "Password is required.");
  return checkField(
    "passwordError",
    password.length >= 6,
    "Password must be at least 6 characters."
  );
}

function validateRepeatPassword() {
  const password = document.getElementById("password").value;
  const repeatPassword = document.getElementById("repeat-password").value;
  return checkField(
    "repeatPasswordError",
    repeatPassword === password && repeatPassword !== "",
    "Passwords must match."
  );
}

function validateEmail() {
  const email = document.getElementById("email").value.trim();
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return checkField(
    "emailError",
    emailPattern.test(email),
    "Invalid email format."
  );
}

function validateGender() {
  const isMale = document.getElementById("male").checked;
  const isFemale = document.getElementById("female").checked;
  return checkField(
    "genderError",
    isMale || isFemale,
    "Please select a gender."
  );
}

function validateTelephone() {
  const telephone = document.getElementById("telephone").value.trim();
  const phonePattern = /^\+?[0-9]+$/;
  return checkField(
    "telephoneError",
    phonePattern.test(telephone),
    "Invalid phone number. Only digits and optional + at the start."
  );
}

function validateForm() {
  let isValid = true; // Assume the form is valid

  if (!validateName()) isValid = false;
  if (!validateUsername()) isValid = false;
  if (!validatePassword()) isValid = false;
  if (!validateRepeatPassword()) isValid = false;
  if (!validateEmail()) isValid = false;
  if (!validateGender()) isValid = false;
  if (!validateTelephone()) isValid = false;

  return isValid; // Return true only if all validations pass
}

// Helper function to show/hide error messages
function checkField(errorId, condition, errorMessage) {
  if (condition) {
    clearError(errorId);
    return true;
  } else {
    showError(errorId, errorMessage);
    return false;
  }
}

// Error handling functions
function showError(id, message) {
  document.getElementById(id).textContent = message;
  document.getElementById(id).style.display = "block";
}

function clearError(id) {
  document.getElementById(id).textContent = "";
  document.getElementById(id).style.display = "none";
}

function clearAllErrors() {
  [
    "nameError",
    "usernameError",
    "passwordError",
    "repeatPasswordError",
    "emailError",
    "genderError",
    "telephoneError",
  ].forEach(clearError);
}
