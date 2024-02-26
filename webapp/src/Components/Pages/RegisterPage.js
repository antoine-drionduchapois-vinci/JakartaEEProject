import { setAuthenticatedUser } from "../../utils/auths";
import Navigate from "../Router/Navigate";




const RegisterPage = () => {
  const main = document.querySelector('main');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const name = document.getElementById('nameInput').value;
    const firstname = document.getElementById('firstnameInput').value;
    const email = document.getElementById('emailInput').value;
    const telephone = document.getElementById('telInput').value;
    const password = document.getElementById('passwordInput').value;
  
    if (!name || !firstname || !email || !telephone || !password) {
      alert("Wrong info, please resubmit form");
      return;
    }
  

    const data = {
      name,
      firstname,
      email,
      telephone,
      password
    };
  
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    };
    const reponse= await fetch('http://localhost:8080/auth/register',options);
  
    const successMessage = document.getElementById('success-message');
    const errorMessage = document.getElementById('error-message');
  
    if (reponse.ok) {
      const authenticatedUser = await reponse.json();
      setAuthenticatedUser(authenticatedUser);
    
      Navigate('/');
      successMessage.style.display = 'block';
      errorMessage.style.display = 'none';
    } else {
      const errorData = await reponse.json()
      errorMessage.innerHTML = errorData.message;
      errorMessage.style.display = 'block';
  
      successMessage.style.display = 'none';
    }
  };


  
  
const bloc1 = `

<section class="section">
<div class="container">
  <h1 class="title has-text-centered"><strong>Inscription</strong></h1>
</div>
</section>

<form class="box">
  <div class="field">
  <label class="label">Nom</label>
  <div class="control">
  <input id="nameInput" class="input" type="text">
  </div>

  <div class="field">
  <label class="label">Prénom</label>
  <div class="control">
  <input id="firstnameInput" class="input" type="text">
  </div>

  <div class="field">
    <label class="label">Email</label>
    <div class="control">
      <input id="emailInput" class="input" type="email">
    </div>
  </div>

  <div class="field">
  <label class="label">Téléphone</label>
  <div class="control">
    <input id="telInput" class="input" type="tel">
  </div>
  </div>

  <div class="field">
    <label class="label">Mot de passe</label>
    <div class="control">
      <input id="passwordInput" class="input" type="password">
    </div>
  </div>

  <button class="button is-light" id="registerButton">S'inscrire</button>
</form>

`;


main.innerHTML = bloc1;



document.getElementById('registerButton').addEventListener('click', handleSubmit );
  
};




export default RegisterPage;
