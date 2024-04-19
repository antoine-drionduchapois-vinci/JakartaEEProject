// Importing navigation function
import { clearPage } from '../../utils/render';
import Navigate from '../Router/Navigate';

// HomePage component definition
const HomePage = () => {
  // Clear the page
  clearPage();

  // Selecting main element from the DOM
  const main = document.querySelector('main');

  // Function to navigate to register page
  function register() {
    console.log('Register Page');
    // Navigating to '/register' route
    Navigate('/register');
  }

  // Function to navigate to login page
  function connect() {
    console.log('Connection Page');
    // Navigating to '/login' route
    Navigate('/login');
  }

  // HTML block for homepage content
  const bloc1 = `
  <section class="hero is-text is-fullheight">
      <div class="mt-6 pt-6">
        <div class="container">
          <h1 class="title has-text-centered">
            <strong>StageLink IPL</strong>
          </h1>
          <h2 class="subtitle has-text-centered">
            L’application qui vous permet de gérer vos recherches de stage
          </h2>
          <div class="columns is-centered mt-6">
            <div class="column is-narrow">
              <button class="button is-large text-white is-rounded" id="registerButton" style="background-color: #004182;">
                <span class="icon">
                  <i class="fas fa-user-plus"></i>
                </span>
                <span>S'inscrire</span>
              </button>
            </div>
            <div class="column is-narrow">
              <button class="button is-large text-white is-rounded" id="connectButton" style="background-color : #b12763;">
                <span class="icon">
                  <i class="fas fa-sign-in-alt"></i>
                </span>
                <span>Se connecter</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
  `;

  // Setting the innerHTML of main with the homepage content
  main.innerHTML = bloc1;

  // Adding event listeners to register and connect buttons
  document.getElementById('registerButton').addEventListener('click', register);
  document.getElementById('connectButton').addEventListener('click', connect);
};

// Exporting HomePage component
export default HomePage;
