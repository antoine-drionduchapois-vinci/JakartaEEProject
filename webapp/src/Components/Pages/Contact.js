// Importing rendering utility functions
import { clearPage, renderPageTitle } from '../../utils/render';

// Contact component definition
const Contact = () => {
  // Selecting main element from the DOM
  const main = document.querySelector('main');
  // Clearing the page
  clearPage();
  // Rendering page title as 'Contact'
  renderPageTitle('Contact');

  const contentElement = document.createElement('div');
  contentElement.id = 'content';
  main.appendChild(contentElement);

  contentElement.innerHTML = `
    <div class="columns p-4">
        <div class="column is-one-fifth bg-primary">
            <div class="d-flex gap-2 align-items-center">
                <div class="border border-black rounded-circle" style="width: 20px; height: 20px;"></div>
                Initié
            </div>
        </div>
        <div class="column bg-secondary">
            <label for="enterprise">Entreprise *</label>
            <input class="input" type="text" id="enterprise">
        </div>
        <div class="column bg-secondary">
        <label for="label">Appelation</label>
            <input class="input" type="text" id="label">
        </div>
        <div class="column bg-secondary">
            <label for="adress">Adresse *</label>
            <input class="input" type="text" id="adress">
        </div>
        <div class="column bg-secondary">
            <label for="contact">Téléphone / Email *</label>
            <input class="input" type="text" id="contact">
        </div>
        <div class="column bg-secondary d-flex align-items-end">
            <button class="button is-fullwidth">Initier</button>
        </div>
    </div>
  `;
};

// Exporting Contact component
export default Contact;
