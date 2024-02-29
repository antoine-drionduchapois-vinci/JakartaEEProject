// Importing authentication utility functions
import { getRememberMe, setAuthenticatedUser, setRememberMe } from '../../utils/auths';
// Importing rendering utility functions
import { clearPage, renderPageTitle } from '../../utils/render';
// Importing Navbar component
import Navbar from '../Navbar/Navbar';
// Importing navigation function
import Navigate from '../Router/Navigate';

// LoginPage component definition
const LoginPage = () => {
  // Clearing the page
  clearPage();
  // Rendering page title as 'Login'
  renderPageTitle('Login');
  // Rendering login form
  renderLoginForm();
};

// Function to render the login form
function renderLoginForm() {
  // Selecting main element from the DOM
  const main = document.querySelector('main');
  // Creating form element
  const form = document.createElement('form');
  form.className = 'p-5';
  // Creating input field for email
  const username = document.createElement('input');
  username.type = 'email';
  username.id = 'email';
  username.placeholder = 'email';
  username.required = true;
  username.className = 'form-control mb-3';
  // Creating label for email input
  const usernameLabel = document.createElement('label');
  usernameLabel.htmlFor = 'email';
  usernameLabel.textContent = 'Email';
  usernameLabel.className = 'form-label';
  // Creating input field for password
  const password = document.createElement('input');
  password.type = 'password';
  password.id = 'password';
  password.required = true;
  password.placeholder = 'password';
  password.className = 'form-control mb-3';
  // Creating label for password input
  const passwordLabel = document.createElement('label');
  passwordLabel.htmlFor = 'password';
  passwordLabel.textContent = 'Password';
  passwordLabel.className = 'form-label';
  // Creating submit button
  const submit = document.createElement('input');
  submit.value = 'Login';
  submit.type = 'submit';
  submit.className = 'btn btn-info';
  // Creating wrapper for remember me checkbox
  const formCheckWrapper = document.createElement('div');
  formCheckWrapper.className = 'mb-3 form-check';
  // Creating remember me checkbox
  const rememberme = document.createElement('input');
  rememberme.type = 'checkbox';
  rememberme.className = 'form-check-input';
  rememberme.id = 'rememberme';
  // Getting remember me status from localStorage
  const remembered = getRememberMe();
  rememberme.checked = remembered;
  // Adding event listener for checkbox click
  rememberme.addEventListener('click', onCheckboxClicked);
  // Creating label for remember me checkbox
  const checkLabel = document.createElement('label');
  checkLabel.htmlFor = 'rememberme';
  checkLabel.className = 'form-check-label';
  checkLabel.textContent = 'Remember me';
  // Appending elements to form
  form.appendChild(usernameLabel);
  form.appendChild(username);
  form.appendChild(passwordLabel);
  form.appendChild(password);
  form.appendChild(formCheckWrapper);
  form.appendChild(submit);
  // Appending checkbox and label to form check wrapper
  formCheckWrapper.appendChild(rememberme);
  formCheckWrapper.appendChild(checkLabel);
  // Appending form to main element
  main.appendChild(form);
  // Adding event listener for form submission
  form.addEventListener('submit', onLogin);
}

// Function to handle remember me checkbox click event
function onCheckboxClicked(e) {
  // Setting remember me status in localStorage
  setRememberMe(e.target.checked);
}

// Function to handle login form submission
async function onLogin(e) {
  e.preventDefault();
  // Getting email and password from input fields
  const email = document.querySelector('#email').value;
  const password = document.querySelector('#password').value;
  console.log(email, password);
  // Creating options object for fetch
  const options = {
    method: 'POST',
    body: JSON.stringify({ email, password }),
    headers: {
      'Content-Type': 'application/json',
    },
  };
  // Sending login request to server
  const response = await fetch(`http://localhost:8080/auths/login`, options);
  // Handling non-successful response from server
  if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
  // Parsing response body as JSON
  const authenticatedUser = await response.json();
  // Logging authenticated user data
  console.log('Authenticated user : ', authenticatedUser);
  // Setting authenticated user in localStorage
  setAuthenticatedUser(authenticatedUser);
  // Rendering Navbar component
  Navbar();
  // Navigating to dashboard page
  Navigate('/dashboard');
}

// Exporting LoginPage component
export default LoginPage;
