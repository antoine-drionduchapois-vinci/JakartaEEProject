// Importing authentication utility function to set authenticated user
import { setAuthenticatedUser } from "../../utils/auths";
// Importing navigation function
import Navigate from "../Router/Navigate";

// RegisterPage component definition
const RegisterPage = () => {
  // Selecting main element from the DOM
  const main = document.querySelector('main');

  // Function to handle form submission
  const handleSubmit = async (event) => {
    event.preventDefault();
    // Getting form input values
    const name = document.getElementById('nameInput').value;
    const firstname = document.getElementById('firstnameInput').value;
    const email = document.getElementById('emailInput').value;
    const telephone = document.getElementById('telInput').value;
    const password = document.getElementById('passwordInput').value;
    let role = "student";
    if (email.endsWith('@vinci.be')) {
      role = document.getElementById('roleSelect').value;
    }
    // Validating form inputs
    if (!name || !firstname || !email || !telephone || !password || !role) {
      const errorMessage = document.getElementById('errorMessage');
      errorMessage.textContent = "Veuillez remplir tous les champs obligatoires";
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
      role
    };
  
    // Creating options for fetch request
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    };
    // Sending registration request to server
    const response = await fetch('http://localhost:8080/auth/register', options);
  
    // Selecting success and error message elements
    const successMessage = document.getElementById('success-message');
    const errorMessage = document.getElementById('error-message');
  
    // Handling response from server
    if (response.ok) {
      const authenticatedUser = await response.json();
      // Setting authenticated user in localStorage
      setAuthenticatedUser(authenticatedUser);
      // Navigating to home page
      Navigate('/');
      successMessage.style.display = 'block';
      errorMessage.style.display = 'none';
    } else {
      const errorData = await response.json();
      errorMessage.innerHTML = errorData.message;
      errorMessage.style.display = 'block';
      successMessage.style.display = 'none';
    }
  };

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

        <div class="field">
        <label class="label">Choix de rôle</label>
        <div class="control">
          <div class="buttons">
            <label class="button">
              <input type="radio" name="role" value="Professeur" checked>
              Professeur
            </label>
            <label class="button">
              <input type="radio" name="role" value="Administrateur">
              Administrateur
            </label>
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
      </form>
    </div>
  </div>
  `;

  // Setting the innerHTML of main with the registration form
  main.innerHTML = bloc1;

  document.getElementById('emailInput').addEventListener('input', function() {
    const email = this.value.trim();
    const additionalOptions = document.getElementById('roleOptions');
    if (email.endsWith('@vinci.be')) {
      additionalOptions.style.display = 'block';
    } else {
      additionalOptions.style.display = 'none';
    }
  });

  // Adding event listener for form submission
  document.getElementById('registerButton').addEventListener('click', handleSubmit);
};

// Exporting RegisterPage component
export default RegisterPage;
