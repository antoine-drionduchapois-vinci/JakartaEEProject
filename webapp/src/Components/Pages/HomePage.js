import Navigate from '../Router/Navigate';

const HomePage = () => {
  const main = document.querySelector('main');

  function register() {
    console.log("Register Page");
    Navigate('/register');

  }

  function connect() {
    console.log("Connection Page");
    Navigate('/login');
  }


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
  <button class="button is-light" id="registerButton">S'inscrire</button>
  <button class="button is-light" id="connectButton">Se connecter</button>
  </div>


  `;

  main.innerHTML = bloc1;


  document.getElementById('registerButton').addEventListener('click', register);
  document.getElementById('connectButton').addEventListener('click', connect);
};

export default HomePage;
