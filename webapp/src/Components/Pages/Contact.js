// Importing rendering utility functions
import { clearPage, renderPageTitle } from '../../utils/render';
import InitiatedRow from '../Contact/InitiatedRow';

import { getAuthenticatedUser } from '../../utils/auths';
import TookRow from '../Contact/TookRow';
import RefusedRow from '../Contact/RefusedRow';
import UnfollowedRow from '../Contact/UnfollowedRow';

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
    .then((res) => (res.status === 200 ? res.json() : null))
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
    <div id="took-row" class="columns p-4"></div>
    <div id="refused-row" class="columns p-4"></div>
    <div id="unfollowed-row" class="columns p-4"></div>
  `;

  InitiatedRow(document.querySelector('#initiated-row'), user, contact, enterprises);
  TookRow(document.querySelector('#took-row'), user, contact);
  RefusedRow(document.querySelector('#refused-row'), contact);
  UnfollowedRow(document.querySelector('#unfollowed-row'), contact);
};

// Exporting Contact component
export default Contact;
