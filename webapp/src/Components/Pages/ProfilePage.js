import { getAuthenticatedUser } from "../../utils/auths";
import { clearPage, renderPageTitle } from "../../utils/render";
import Navigate from "../Router/Navigate";


let main = document.querySelector('main');
// ProfilePage component
const ProfilePage = () => {
    // Clear the page
    clearPage();

    // Render the page title
    renderPageTitle('Profil');

    // Render the profile page content
    renderProfilePage();

    // Add event listener for password form submission
    const passwordForm = document.querySelector('#password-form');
    passwordForm.addEventListener('submit', handlePasswordChange);

    // Add event listener for telephone form submission
    const telForm = document.querySelector('#telForm');
    telForm.addEventListener('submit', handleTelChange);
}

// Function to handle password change submission
async function handlePasswordChange(event) {
    // Prevent default form submission behavior
    event.preventDefault();

    // Reset form errors
    resetFormErrors('password-form');

    // Get the main element
    main = document.querySelector('main');

    // Get form elements and user information
    const passwordForm = document.querySelector('#password-form');
    const currentPassword = passwordForm.elements.currentPassword.value;
    const newPassword = passwordForm.elements.newPassword.value;
    const confirmPassword = passwordForm.elements.confirmPassword.value;
    const error = document.getElementById('error1');
    const user = getAuthenticatedUser();

    // Validate new password confirmation
    if (newPassword !== confirmPassword) {
        error.innerHTML = "Le mot de passe de confirmation n'est pas le même";
        error.style.display = 'block';
        error.style.color = 'red';
        return;
    }
    
    // Validate if all fields are filled
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
        // Prepare data for API request
        const data = {
            userId: user.id,
            email: user.email,
            password: currentPassword,
            newPassword1: newPassword,
        };

        // Make API request to change password
        const response = await fetch('http://localhost:8080/users/changePassword', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': user.token
            },
        });

        // Handle response from the server
        if (!response.ok) {
            if (response.status === 401) {
                error.innerHTML = 'Mauvais mot de passe actuel !';
                error.style.display = 'block';
                error.style.color = 'red';
            } else {
                throw new Error(`Failed to change password`);
            }
        } else {
            // Display success message and initiate redirection countdown
            successMessage('Le mot de passe à bien été modifié');
        }
    } catch (e) {
        console.error('Error:', e);
    }
}

// Function to handle telephone change submission
async function handleTelChange(event) {
    // Prevent default form submission behavior
    event.preventDefault();

    // Reset form errors
    resetFormErrors('telForm');

    // Get the main element
    main = document.querySelector('main');

    // Get form elements and user information
    const telForm = document.querySelector('#telForm');
    const currentPassword = telForm.elements.passwordTel.value;
    const newPhoneNumber = telForm.elements.newPhoneNumber.value;
    const error = document.getElementById('error2');
    const user = getAuthenticatedUser();

    // Validate if all fields are filled
    if (!currentPassword || !newPhoneNumber) {
        error.innerHTML = "Veuillez remplir tous les champs";
        error.style.display = 'block';
        error.style.color = 'red';
        if (!currentPassword) addRedBorder('passwordTel');
        if (!newPhoneNumber) addRedBorder('newPhoneNumber');
        return;
    }

    try {
        // Prepare data for API request
        const data = {
            userId: user.id,
            email: user.email,
            password: currentPassword,
            phone: newPhoneNumber,
        };

        // Make API request to change phone number
        const response = await fetch('http://localhost:8080/users/changePhoneNumber', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': user.token
            },
        });

        // Handle response from the server
        if (!response.ok) {
            if (response.status === 401) {
                error.innerHTML = 'Mauvais mot de passe actuel !';
                error.style.display = 'block';
                error.style.color = 'red';
            } else {
                throw new Error(`Failed to change phone`);
            }
        } else {
            // Display success message and initiate redirection countdown
            successMessage('Le numéro de téléphone à bien été modifié');
        }
    } catch (e) {
        console.error('Error:', e);
    }
}

function successMessage (message) {
    const user = getAuthenticatedUser();
    const successMessageDiv = document.createElement('div');
            successMessageDiv.innerHTML = message;
            successMessageDiv.style.color = 'green';

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
            countdownFrame.appendChild(successMessageDiv);
            countdownFrame.appendChild(countdownMessage);
            main.appendChild(countdownFrame);

            let secondsLeft = 3;
            const countdownInterval = setInterval(() => {
                secondsLeft -=1;
                document.getElementById('countdown').innerText = secondsLeft;

                if (secondsLeft === 0) {
                    clearInterval(countdownInterval);
                    if(user.role === 'STUDENT'){
                        Navigate('/dashboardS')
                    }else{
                        Navigate('/dashboardT');
                    }
                }
            }, 1000);
}

// Function to render profile page content
function renderProfilePage() {
    main = document.querySelector('main');

    // HTML content for the profile page
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

    // Set the HTML content to the main element
    main.innerHTML = htmlContent;
}

// Function to add red border to an element
function addRedBorder(elementId) {
    const element = document.getElementById(elementId);
    if (element) {
      element.style.border = '1px solid red';
    } 
}

// Function to reset form errors
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

// Export the ProfilePage component
export default ProfilePage;