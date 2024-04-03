import { clearPage } from "../../utils/render";


const ProfilePage = () => {
    clearPage();

    renderProfilePage();
}

function renderProfilePage() {
    const main = document.querySelector('main');

    const htmlContent = `
    <main class="section">
        <h2 class="title is-2 has-text-centered">Profil</h2>
        <div class="columns">
            <div class="column is-half">
                <h4 class="title is-4 has-text-centered">Modifier mot de passe</h4>
                <div class="box">
                    <form>
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
                <div class="error">
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
                <div class="error">
                </div>
            </div>
        </div>
    </main>
    `;

    main.innerHTML = htmlContent;
}

export default ProfilePage;