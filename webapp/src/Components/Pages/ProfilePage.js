import { getAuthenticatedUser } from "../../utils/auths";
import { clearPage, renderPageTitle } from "../../utils/render";
import Navigate from "../Router/Navigate";


const ProfilePage = () => {
    clearPage();

    renderPageTitle('Profil');

    renderProfilePage();

    const passwordForm = document.querySelector('#password-form');
    passwordForm.addEventListener('submit', handlePasswordChange);

    const telForm = document.querySelector('#telForm');
    telForm.addEventListener('submit', handleTelChange);
}

async function handlePasswordChange(event) {
    event.preventDefault();
    resetFormErrors('password-form');

    console.log('passer par handlePassword');

    const main = document.querySelector('main');

    const passwordForm = document.querySelector('#password-form');
    const currentPassword = passwordForm.elements.currentPassword.value;
    const newPassword = passwordForm.elements.newPassword.value;
    const confirmPassword = passwordForm.elements.confirmPassword.value;
    const error = document.getElementById('error1');
    const user = getAuthenticatedUser();

    if (newPassword !== confirmPassword) {
        error.innerHTML = "Le mot de passe de confirmation n'est pas le même";
        error.style.display = 'block';
        error.style.color = 'red';
        return;
    }
    
    if (!currentPassword || !newPassword || !confirmPassword) {
        error.innerHTML = "Veuillez remplir tous les champs";
        error.style.display = 'block';
        error.style.color = 'red';
        if (!currentPassword) addRedBorder('currentPassword');
        if (!newPassword) addRedBorder('newPassword');
        if (!confirmPassword) addRedBorder('confirmPassword');
        return;
    }

    try {
        const data = {
            userId: user.id,
            email: user.email,
            password: currentPassword,
            newPassword1: newPassword,
        };

        const response = await fetch('http://localhost:8080/users/changePassword', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': user.token
            },
        });

        if (!response.ok) {
            if (response.status === 401) {
                error.innerHTML = 'Mauvais mot de passe actuel !';
                error.style.display = 'block';
                error.style.color = 'red';
            } else {
                throw new Error(`Failed to change password`);
            }
        } else {
            const successMessage = document.createElement('div');
            successMessage.innerHTML = 'Mot de passe modifié avec succès !';
            successMessage.style.color = 'green';

            const countdownFrame = document.createElement('div');
            countdownFrame.style.position = 'absolute';
            countdownFrame.style.top = '50%';
            countdownFrame.style.left = '50%';
            countdownFrame.style.transform = 'translate(-50%, -50%)';
            countdownFrame.style.padding = '20px';
            countdownFrame.style.border = '2px solid green';
            countdownFrame.style.backgroundColor = 'white';
            countdownFrame.style.zIndex = '9999';

            const countdownMessage = document.createElement('div');
            countdownMessage.innerHTML = 'Vous allez être redirigé dans <span id="countdown">2</span> secondes';
            countdownFrame.appendChild(successMessage);
            countdownFrame.appendChild(countdownMessage);
            main.appendChild(countdownFrame);

            let secondsLeft = 3;
            const countdownInterval = setInterval(() => {
                secondsLeft -=1;
                document.getElementById('countdown').innerText = secondsLeft;

                if (secondsLeft === 0) {
                    clearInterval(countdownInterval);
                    Navigate('/dashboardS');
                }
            }, 1000);
        }
    } catch (e) {
        console.error('Error:', e);
    }
}

async function handleTelChange(event) {
    event.preventDefault();
    resetFormErrors('telForm');

    const main = document.querySelector('main');

    const telForm = document.querySelector('#telForm');
    const currentPassword = telForm.elements.passwordTel.value;
    const newPhoneNumber = telForm.elements.newPhoneNumber.value;
    const error = document.getElementById('error2');
    const user = getAuthenticatedUser();

    if (!currentPassword || !newPhoneNumber) {
        error.innerHTML = "Veuillez remplir tous les champs";
        error.style.display = 'block';
        error.style.color = 'red';
        if (!currentPassword) addRedBorder('passwordTel');
        if (!newPhoneNumber) addRedBorder('newPhoneNumber');
        return;
    }

    try {
        const data = {
            userId: user.id,
            email: user.email,
            password: currentPassword,
            phone: newPhoneNumber,
        };

        const response = await fetch('http://localhost:8080/users/changePhoneNumber', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': user.token
            },
        });

        if (!response.ok) {
            if (response.status === 401) {
                error.innerHTML = 'Mauvais mot de passe actuel !';
                error.style.display = 'block';
                error.style.color = 'red';
            } else {
                throw new Error(`Failed to change phone`);
            }
        } else {
            const successMessage = document.createElement('div');
            successMessage.innerHTML = 'Numéro de téléphone modifié avec succès !';
            successMessage.style.color = 'green';

            const countdownFrame = document.createElement('div');
            countdownFrame.style.position = 'absolute';
            countdownFrame.style.top = '50%';
            countdownFrame.style.left = '50%';
            countdownFrame.style.transform = 'translate(-50%, -50%)';
            countdownFrame.style.padding = '20px';
            countdownFrame.style.border = '2px solid green';
            countdownFrame.style.backgroundColor = 'white';
            countdownFrame.style.zIndex = '9999';

            const countdownMessage = document.createElement('div');
            countdownMessage.innerHTML = 'Vous allez être redirigé dans <span id="countdown">2</span> secondes';
            countdownFrame.appendChild(successMessage);
            countdownFrame.appendChild(countdownMessage);
            main.appendChild(countdownFrame);

            let secondsLeft = 3;
            const countdownInterval = setInterval(() => {
                secondsLeft -=1;
                document.getElementById('countdown').innerText = secondsLeft;

                if (secondsLeft === 0) {
                    clearInterval(countdownInterval);
                    Navigate('/dashboardS');
                }
            }, 1000);
        }
    } catch (e) {
        console.error('Error:', e);
    }
}

function renderProfilePage() {
    const main = document.querySelector('main');

    const htmlContent = `
    <main class="section">
        <div class="columns">
            <div class="column is-half">
                <h4 class="title is-4 has-text-centered">Modifier mot de passe</h4>
                <div class="box">
                    <form id="password-form">
                        <div class="field">
                            <label class="label">Mot de passe actuel</label>
                            <div class="control">
                                <input class="input" type="password" name="currentPassword" id="currentPassword">
                            </div>
                        </div>
                        <div class="field">
                            <label class="label">Nouveau mot de passe</label>
                            <div class="control">
                                <input class="input" type="password" name="newPassword" id="newPassword">
                            </div>
                        </div>
                        <div class="field">
                            <label class="label">Confirmer nouveau mot de passe</label>
                            <div class="control">
                                <input class="input" type="password" name="confirmPassword" id="confirmPassword">
                            </div>
                        </div>
                        <div id="error1">
                        </div>
                        <div class="field">
                            <div class="control has-text-centered">
                                <button class="button is-dark is-rounded" type="submit" id="password-submit">Envoyer</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="column is-half">
                <h4 class="title is-4 has-text-centered">Modifier numéro de téléphone</h4>
                <div class="box">
                    <form id="telForm">
                        <div class="field">
                            <label class="label">Mot de passe</label>
                            <div class="control">
                                <input class="input" type="password" name="password" id= "passwordTel">
                            </div>
                        </div>
                        <div class="field">
                            <label class="label">Nouveau numéro de téléphone</label>
                            <div class="control">
                                <input class="input" type="text" name="newPhoneNumber" id="newPhoneNumber">
                            </div>
                        </div>
                        <div id="error2">
                        </div>
                        <div class="field">
                            <div class="control has-text-centered">
                                <button class="button is-dark is-rounded" type="submit" id="tel-submit">Envoyer</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>
    `;

    main.innerHTML = htmlContent;
}

function addRedBorder(elementId) {
    const element = document.getElementById(elementId);
    if (element) {
      element.style.border = '1px solid red';
    } 
  }
  
  function resetFormErrors(formId) {
    const errorMessage = document.getElementById(formId === 'password-form' ? 'error1' : 'error2');
    errorMessage.textContent = '';
    errorMessage.style.display = 'none';
  
    const inputs = document.querySelectorAll('input');
    inputs.forEach(input => {
      const inputElement = input;
      inputElement.style.border = '0.5px solid lightgray';
    });
  }

export default ProfilePage;