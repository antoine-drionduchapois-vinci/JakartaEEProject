// Importing navigation function
import Navigate from '../Router/Navigate';

// HomePage component definition
const HomePage = () => {
  // Selecting main element from the DOM
  const main = document.querySelector('main');

  // Function to navigate to register page
  function register() {
    console.log("Register Page");
    // Navigating to '/register' route
    Navigate('/register');
  }

  // Function to navigate to login page
  function connect() {
    console.log("Connection Page");
    // Navigating to '/login' route
    Navigate('/login');
  }

  // HTML block for homepage content
  const bloc1 = `
    <section class="section">
      <div class="container">
        <h1 class="title has-text-centered"><strong>StageLink IPL</strong></h1>
        <h2 class="subtitle has-text-centered">
          L’application qui vous permet de gérer vos recherches de stage
        </h2>
      </div>
    </section>

    <div class="has-text-centered">
      <button class="button " id="registerButton">S'inscrire</button>
      <button class="button " id="connectButton">Se connecter</button>
    </div>
  `;

  // Setting the innerHTML of main with the homepage content
  main.innerHTML = bloc1;

  // Adding event listeners to register and connect buttons
  document.getElementById('registerButton').addEventListener('click', register);
  document.getElementById('connectButton').addEventListener('click', connect);
};

// Exporting HomePage component
export default HomePage;
