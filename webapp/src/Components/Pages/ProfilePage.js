import { getAuthenticatedUser } from "../../utils/auths";
import { clearPage, renderPageTitle } from "../../utils/render";


const ProfilePage = () => {
    clearPage();

    renderPageTitle('Profil');

    renderProfilePage();

    const passwordForm = document.querySelector('#password-form');
    passwordForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const currentPassword = passwordForm.elements.currentPassword.value;
        const newPassword = passwordForm.elements.newPassword.value;
        const confirmPassword = passwordForm.elements.confirmPassword.value;

        const error = document.getElementById('error1');

        const user = getAuthenticatedUser();
        console.log(user);
        
        if (newPassword !== confirmPassword) {
            error.innerHTML = "Le mot de passe de confirmation n'est pas le même"
            error.style.display = 'block';
            error.style.color = 'red';
            return;
        }
        const data = {
            email : user.email,
            password : currentPassword,
            newPassWord : newPassword
        }
        
        try {
            const response = await fetch('http://localhost:8080/users/changePassword', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': user.token
                },
                body: JSON.stringify(data)
            });
            if (!response.ok) {
                throw new Error('Failed to change password');
            }
            // Afficher un message de succès
        } catch (e) {
            console.error('Error:', e);
            // Afficher un message d'erreur générique
        }
    });
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
                                <input class="input" type="password" name="currentPassword">
                            </div>
                        </div>
                        <div class="field">
                            <label class="label">Nouveau mot de passe</label>
                            <div class="control">
                                <input class="input" type="password" name="newPassword">
                            </div>
                        </div>
                        <div class="field">
                            <label class="label">Confirmer nouveau mot de passe</label>
                            <div class="control">
                                <input class="input" type="password" name="confirmPassword">
                            </div>
                        </div>
                        <div class="field">
                            <div class="control has-text-centered">
                                <button class="button is-dark is-rounded" type="submit">Envoyer</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div id="error1">
                </div>
            </div>
            <div class="column is-half">
                <h4 class="title is-4 has-text-centered">Modifier numéro de téléphone</h4>
                <div class="box">
                    <form>
                        <div class="field">
                            <label class="label">Nouveau numéro de téléphone</label>
                            <div class="control">
                                <input class="input" type="text" name="newPhoneNumber">
                            </div>
                        </div>
                        <div class="field">
                            <label class="label">Mot de passe</label>
                            <div class="control">
                                <input class="input" type="password" name="password">
                            </div>
                        </div>
                        <div class="field">
                            <div class="control has-text-centered">
                                <button class="button is-dark is-rounded" type="submit">Envoyer</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div id="error2">
                </div>
            </div>
        </div>
    </main>
    `;

    main.innerHTML = htmlContent;
}

export default ProfilePage;