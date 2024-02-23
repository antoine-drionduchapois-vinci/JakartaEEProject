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
    <h1>StageLink IPL</h1>
    <h2>Bienvenue sur StageLink IPL</h2>
    <p>L’application qui vous permet de gérer vos recherches de stage</p>

    <button id="registerButton">S'inscrire</button>
    <button id="connectButton">Se connecter</button>
  `;

  main.innerHTML = bloc1;


  document.getElementById('registerButton').addEventListener('click', register);
  document.getElementById('connectButton').addEventListener('click', connect);
};

export default HomePage;
