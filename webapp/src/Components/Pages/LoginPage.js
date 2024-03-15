// Import des dépendances
import { getRememberMe, setAuthenticatedUser, setRememberMe } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

// Définition du composant LoginPage
const LoginPage = () => {
  // Effacer la page
  clearPage();
  // Rendre le titre de la page "Login"
  renderPageTitle('Login');
  // Rendre le formulaire de connexion
  renderLoginForm();
};

// Fonction pour rendre le formulaire de connexion
function renderLoginForm() {
  // Sélectionner l'élément principal dans le DOM
  const main = document.querySelector('main');

  // Création du conteneur du formulaire centré
  const container = document.createElement('div');
  container.className = ' d-flex justify-content-center align-items-center vh-100';

  // Création du formulaire dans un cadre avec une bordure
  const form = document.createElement('form');
  form.className = ' border rounded bg-light shadow bg-white';
  form.style.padding = '100px';

  // Création du champ d'entrée pour l'e-mail
  const username = createInputElement('email', 'email', 'Email', true, 'form-control mb-3');

  // Création du champ d'entrée pour le mot de passe
  const password = createInputElement('password', 'password', 'Password', true, 'form-control mb-3');

  // Création du bouton de soumission
  const submit = createSubmitButton();

  // Création du wrapper pour la case à cocher "Remember me"
  const formCheckWrapper = createRememberMeCheckbox();

  const registerLink = document.createElement('p');
  registerLink.innerHTML = `Vous n'avez pas de compte ? <a id="registerLink" style="color: blue; cursor: pointer;">S'enregistrer</a>`;
  // Ajout d'un écouteur d'événements pour la redirection vers la page d'inscription
  registerLink.querySelector('#registerLink').addEventListener('click', () => Navigate('/register'));

  // Ajout des éléments au formulaire
  form.append(username.label, username.input, password.label, password.input, formCheckWrapper, submit, registerLink);

  // Ajout du formulaire au conteneur
  container.appendChild(form);

  // Ajout du conteneur à l'élément principal
  main.appendChild(container);

  // Ajout d'un écouteur d'événements pour la soumission du formulaire
  form.addEventListener('submit', onLogin);
}

// Créer un champ d'entrée HTML
function createInputElement(type, id, labelText, required, className) {
  const input = document.createElement('input');
  input.type = type;
  input.id = id;
  input.required = required;
  input.className = className;

  const label = document.createElement('label');
  label.htmlFor = id;
  label.textContent = labelText;
  label.className = 'form-label';

  return { input, label };
}

// Créer le bouton de soumission
function createSubmitButton() {
  const submit = document.createElement('input');
  submit.value = 'Login';
  submit.type = 'submit';
  submit.className = 'btn btn-dark d-block mx-auto mb-4';

  return submit;
}

// Créer la case à cocher "Remember me"
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

// Gérer le clic sur la case à cocher "Remember me"
function onCheckboxClicked(e) {
  setRememberMe(e.target.checked);
}

// Gérer la soumission du formulaire de connexion
async function onLogin(e) {
  e.preventDefault();

  const email = document.querySelector('#email').value;
  const password = document.querySelector('#password').value;

  const options = {
    method: 'POST',
    body: JSON.stringify({ email, password }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  try {
    const response = await fetch(`http://localhost:8080/auths/login`, options);

    if (!response.ok){
      alert('Email or password incorrect');
      return;
    } 
  
    const authenticatedUser = await response.json();
    setAuthenticatedUser(authenticatedUser);
    Navbar();
    Navigate('/dashboard');
  } catch (error){
    console.error('Error during login:', error);
  }
}

// Export du composant LoginPage
export default LoginPage;