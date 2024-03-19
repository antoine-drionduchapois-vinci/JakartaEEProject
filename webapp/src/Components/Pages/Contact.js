// Importing rendering utility functions
import { clearPage, renderPageTitle } from '../../utils/render';
import InitiatedRow from '../Contact/InitiatedRow';

import { getAuthenticatedUser } from '../../utils/auths';

const contactId = 1; // TODO: will then be acquired by route param

// Contact component definition
const Contact = async () => {
  // Selecting main element from the DOM
  const main = document.querySelector('main');
  // Clearing the page
  clearPage();
  // Rendering page title as 'Contact'
  renderPageTitle('Contact');

  const user = getAuthenticatedUser();

  const contact = await fetch(`http://localhost:8080/contact?contactId=${contactId}`)
    .then((data) => data.json())
    .catch((error) => console.error(error));

  const enterprises = await fetch('http://localhost:8080/ent/enterprises')
    .then((response) => response.json())
    .then((data) => data.enterprises)
    .catch((error) => console.error(error));

  const contentElement = document.createElement('div');
  contentElement.id = 'content';
  main.appendChild(contentElement);
  contentElement.innerHTML = `
    <div id="initiated-row" class="columns p-4"></div>
  `;

  InitiatedRow(document.querySelector('#initiated-row'), user, contact, enterprises);
};

// Exporting Contact component
export default Contact;
