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
  <h1 class="text-3xl font-bold underline">StageLink IPL</h1>
<h2 class="text-xl font-semibold mt-4">Bienvenue sur StageLink IPL</h2>
<p class="mt-2">L’application qui vous permet de gérer vos recherches de stage</p>

<button id="registerButton" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4">S'inscrire</button>
<button id="connectButton" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded mt-2">Se connecter</button>


  `;

  main.innerHTML = bloc1;


  document.getElementById('registerButton').addEventListener('click', register);
  document.getElementById('connectButton').addEventListener('click', connect);
};

export default HomePage;
