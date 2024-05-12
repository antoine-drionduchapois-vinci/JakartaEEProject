// Dependency imports
import { getRememberMe, setAuthenticatedUser, setRememberMe } from '../../utils/auths';
import Redirect from '../../utils/redirect';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

// Definition of the LoginPage component
const LoginPage = () => {
  // Clear the page
  clearPage();
  // Render page title as "Connexion"
  renderPageTitle('Connexion');
  // Render login form
  renderLoginForm();
};

// Function to render the login form
function renderLoginForm() {
  // Select the main element in the DOM
  const main = document.querySelector('main');

  // Create centered form container
  const container = document.createElement('div');
  container.className = ' d-flex justify-content-center align-items-center vh-100';

  // Create form within a bordered frame
  const form = document.createElement('form');
  form.className = ' border rounded bg-light shadow bg-white';
  form.style.padding = '100px';

  // Create email input field
  const username = createInputElement('email', 'emailInput', 'Email', 'form-control mb-3');

  // Create password input field
  const password = createInputElement('password', 'passwordInput', 'Password', 'form-control mb-3');

  // Create submit button
  const submit = createSubmitButton();

  // Create wrapper for "Remember me" checkbox
  const formCheckWrapper = createRememberMeCheckbox();

  // Create an empty paragraph element to display error message
  const errorMessageElement = document.createElement('p');
  errorMessageElement.className = 'help is-danger';
  errorMessageElement.id = 'loginErrorMessage';

  const registerLink = document.createElement('p');
  registerLink.innerHTML = `Vous n'avez pas de compte ? <a id="registerLink" style="color: blue; cursor: pointer;">S'enregistrer</a>`;
  // Add event listener for redirection to registration page
  registerLink
    .querySelector('#registerLink')
    .addEventListener('click', () => Navigate('/register'));

  // Add elements to form
  form.append(
    username.label,
    username.input,
    password.label,
    password.input,
    formCheckWrapper,
    errorMessageElement,
    submit,
    registerLink,
  );

  // Add form to container
  container.appendChild(form);

  // Add container to main element
  main.appendChild(container);

  // Add event listener for form submission
  form.addEventListener('submit', onLogin);
}

// Create an HTML input field
function createInputElement(type, id, labelText, className) {
  const input = document.createElement('input');
  input.type = type;
  input.id = id;
  input.className = className;

  const label = document.createElement('label');
  label.htmlFor = id;
  label.textContent = labelText;
  label.className = 'form-label';

  return { input, label };
}

// Create the submit button
function createSubmitButton() {
  const submit = document.createElement('input');
  submit.value = 'Login';
  submit.type = 'submit';
  submit.className = 'btn btn-dark d-block mx-auto mb-4';

  return submit;
}

// Create the "Remember me" checkbox
function createRememberMeCheckbox() {
  const formCheckWrapper = document.createElement('div');
  formCheckWrapper.className = 'mb-3 form-check';

  const rememberme = document.createElement('input');
  rememberme.type = 'checkbox';
  rememberme.className = 'form-check-input';
  rememberme.id = 'rememberme';
  rememberme.checked = getRememberMe();
  rememberme.addEventListener('click', onCheckboxClicked);

  const checkLabel = document.createElement('label');
  checkLabel.htmlFor = 'rememberme';
  checkLabel.className = 'form-check-label';
  checkLabel.textContent = 'Remember me';

  formCheckWrapper.append(rememberme, checkLabel);

  return formCheckWrapper;
}

// Handle click on "Remember me" checkbox
function onCheckboxClicked(e) {
  setRememberMe(e.target.checked);
}

// Handle login form submission
async function onLogin(e) {
  e.preventDefault();
  resetFormErrors();

  const email = document.querySelector('#emailInput').value;
  const password = document.querySelector('#passwordInput').value;

  if (!email || !password) {
    const errorMessageElement = document.getElementById('loginErrorMessage');
    errorMessageElement.textContent = 'Veuillez remplir tous les champs';
    errorMessageElement.style.display = 'block';
    errorMessageElement.style.fontSize = '16px';

    if (!email) addRedBorder('emailInput');
    if (!password) addRedBorder('passwordInput');
    return;
  }

  const options = {
    method: 'POST',
    body: JSON.stringify({ email, password }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  try {
    const response = await fetch(`http://localhost:8080/auths/login`, options);
    const errorMessageElement = document.getElementById('loginErrorMessage');

    if (!response.ok) {
      if (response.status === 401) {
        errorMessageElement.textContent = 'Mauvais mot de passe';
        errorMessageElement.style.display = 'block';
        errorMessageElement.style.fontSize = '16px';
      } else {
        errorMessageElement.textContent = 'Email incorrect';
        errorMessageElement.style.display = 'block';
        errorMessageElement.style.fontSize = '16px';
      }

      return;
    }

    const authenticatedUser = await response.json();
    console.log(authenticatedUser.role);
    setAuthenticatedUser(authenticatedUser);
    Navbar();
    Redirect.redirect(authenticatedUser.role);
  } catch (error) {
    console.error('Error during login:', error);
  }
}

function addRedBorder(elementId) {
  const element = document.getElementById(elementId);
  if (element) {
    element.style.border = '1px solid red';
  }
}

function resetFormErrors() {
  const errorMessage = document.getElementById('loginErrorMessage');
  errorMessage.textContent = '';
  errorMessage.style.display = 'none';

  const inputs = document.querySelectorAll('input');
  inputs.forEach((input) => {
    const inputElement = input;
    inputElement.style.border = '1px solid lightgray';
  });
}
// Export the LoginPage component
export default LoginPage;
