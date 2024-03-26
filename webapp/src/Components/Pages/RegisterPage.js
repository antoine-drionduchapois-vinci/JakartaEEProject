// Importing authentication utility function to set authenticated user
import { setAuthenticatedUser } from '../../utils/auths';
import Navbar from '../Navbar/Navbar';
// Importing navigation function
import Navigate from '../Router/Navigate';
import Redirect from '../../utils/redirect';
import { clearPage } from '../../utils/render';

// RegisterPage component definition
const RegisterPage = () => {
  clearPage();

  // Selecting main element from the DOM
  const main = document.querySelector('main');

  // HTML block for registration form
  const bloc1 = `
  <section class="section">
  <div class="container">
    <h1 class="title has-text-centered"><strong>Inscription</strong></h1>
  </div>
  </section>

  <div class="columns is-centered">
    <div class="column is-half">
      <form class="box">
        <div class="field">
          <label class="label">Nom</label>
          <div class="control">
            <input id="nameInput" class="input" type="text" required>
          </div>
        </div>

        <div class="field">
          <label class="label">Prénom</label>
          <div class="control">
            <input id="firstnameInput" class="input" type="text" required>
          </div>
        </div>

        <div class="field">
          <label class="label">Email</label>
          <div class="control">
            <input id="emailInput" class="input" type="email" required>
          </div>
        </div>

        <div class="field" id="roleOptions" style="display: none;">
          <label class="label">Choix de rôle</label>
          <div class="control">
            <div class="select">
              <select id="roleSelect" required>
                <option value="TEACHER">Professeur</option>
                <option value="ADMIN">Administrateur</option>
              </select>
            </div>
          </div>
        </div>

        <div class="field">
          <label class="label">Téléphone</label>
          <div class="control">
            <input id="telInput" class="input" type="tel" required>
          </div>
        </div>

        <div class="field">
          <label class="label">Mot de passe</label>
          <div class="control">
            <input id="passwordInput" class="input" type="password" required>
          </div>
        </div>

        <div class="field">
          <p id="errorMessage" class="help is-danger" style="display: none;"></p>
        </div>

        <div class="field is-flex justify-content-center">
        <div class="control">
          <button class="button is-dark is-rounded" id="registerButton">S'inscrire</button>
        </div>  
      </div>
      <div class="field is-flex justify-content-center">
        <p>Vous avez déjà un compte ? <a id="loginLink" style="color: blue; cursor: pointer;">Se connecter !</a></p>
      </div>
      </form>
    </div>
  </div>
  `;

  // Setting the innerHTML of main with the registration form
  main.innerHTML = bloc1;

  // To handle the select choice input
  document.getElementById('emailInput').addEventListener('input', function() {
    const email = this.value.trim();
    const additionalOptions = document.getElementById('roleOptions');
    const roleSelect = document.getElementById('roleSelect');

    if (email.endsWith('@vinci.be')) {
      additionalOptions.style.display = 'block';
      roleSelect.setAttribute('required', '');
    } else {
      additionalOptions.style.display = 'none';
      roleSelect.removeAttribute('required');
    }
  });
  document.getElementById('loginLink').addEventListener('click', () => Navigate('/login'));

  // Adding event listener for form submission
  document.getElementById('registerButton').addEventListener('click', handleSubmit);
};

// Function to handle the submit of the form register
async function handleSubmit(e) {
  e.preventDefault();
  // Getting form input values
  const name = document.getElementById('nameInput').value;
  const firstname = document.getElementById('firstnameInput').value;
  const email = document.getElementById('emailInput').value;
  const telephone = document.getElementById('telInput').value;
  const password = document.getElementById('passwordInput').value;
  let role = 'STUDENT';
  if (email.endsWith('@vinci.be')) {
    role = document.getElementById('roleSelect').value;
  }
  // Validating form inputs
  if (!name || !firstname || !email || !telephone || !password || !role) {
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = 'Veuillez remplir tous les champs obligatoires';
    errorMessage.style.display = 'block';
    errorMessage.style.fontSize = '16px';
    return;
  }

  // Creating data object
  const data = {
    name,
    firstname,
    email,
    telephone,
    password,
    role,
  };

  // Creating options for fetch request
  const options = {
    method: 'POST',
    body: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json',
    },
  };
  // Sending registration request to server
  try {
    fetch('http://localhost:8080/auths/register', options)
      .then((response) => {
        if (!response.ok) {
          return response.json().then((errorData) => {
            const errorMessage = document.getElementById('error-message');
            errorMessage.innerHTML = errorData.message;
            errorMessage.style.display = 'block';
            throw new Error(errorData.message);
          });
        }
        return response.json();
      })
      .then((authenticatedUser) => {
        // Setting authenticated user in localStorage
        setAuthenticatedUser(authenticatedUser);
        // Render navbar components
        Navbar();
        // Navigating to home page
        Redirect.redirect(authenticatedUser.role);
      })
      .catch((error) => {
        console.error('Error during register:', error);
      });
  } catch (error) {
    console.error('Error during register:', error);
  }
}

// Exporting RegisterPage component
export default RegisterPage;
