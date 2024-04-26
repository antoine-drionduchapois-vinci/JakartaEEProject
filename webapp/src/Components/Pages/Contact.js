// Importing rendering utility functions
import { clearPage, renderPageTitle } from '../../utils/render';
import { getAuthenticatedUser } from '../../utils/auths';
import Navigate from '../Router/Navigate';
import InitiatedRow from '../Contact/InitiatedRow';
import TookRow from '../Contact/TookRow';
import RefusedRow from '../Contact/RefusedRow';
import UnfollowedRow from '../Contact/UnfollowedRow';
import SuspendedRow from '../Contact/SuspendedRow';
import AcceptedRow from '../Contact/AcceptedRow';

// Contact component definition
const Contact = async () => {
  // Selecting main element from the DOM
  const main = document.querySelector('main');
  // Clearing the page
  clearPage();
  // Rendering page title as 'Contact'
  renderPageTitle('Contact');

  const user = getAuthenticatedUser();

  const contactId = new URLSearchParams(window.location.search).get('id');

  let contact = null;
  if (contactId) {
    contact = await fetch(`http://localhost:8080/contact?contactId=${contactId}`, {
      headers: {
        Authorization: user.token,
      },
    })
      .then((res) => {
        if (res.ok) return res.json();
        return 404;
      })
      .catch((error) => console.error(error));
  }

  let enterprises = null;
  enterprises = await fetch('http://localhost:8080/ent/enterprises', {
    headers: {
      Authorization: user.token,
    },
  })
    .then((res) => (res.ok ? res.json() : null))
    .catch((error) => console.error(error));

  if (!enterprises) return;

  let internship = null;
  internship = await fetch('http://localhost:8080/int', {
    method: 'GET',
    headers: { Authorization: user.token },
  })
    .then((res) => (res.ok ? res.json() : null))
    .catch((error) => console.error(error));

  const contentElement = document.createElement('div');
  contentElement.id = 'content';
  main.appendChild(contentElement);

  contentElement.innerHTML = `
    <div>
      <button class="ml-4 button" id="back">Retour</button>
    </div>
    <div id="error-404" class="d-none">
      <p>
        <strong>
          404
        </strong>
        Not Found
      </p>
    </div>
    <div id="initiated-row" class="columns p-4"></div>
    <div id="took-row" class="columns p-4"></div>
    <div id="accepted-row" class="columns p-4"></div>
    <div id="refused-row" class="columns p-4"></div>
    <div id="unfollowed-row" class="columns p-4"></div>
    <div id="suspended-row" class="columns p-4"></div>
  `;

  const backButton = document.querySelector('#back');
  backButton.addEventListener('click', () => {
    Navigate('/dashboardS');
  });

  if (contact === 404) {
    document.querySelector('#error-404').setAttribute('class', 'd-flex justify-content-center');
    return;
  }

  InitiatedRow(document.querySelector('#initiated-row'), user, contact, enterprises);
  TookRow(document.querySelector('#took-row'), contact);
  AcceptedRow(document.querySelector('#accepted-row'), contact, internship);
  RefusedRow(document.querySelector('#refused-row'), contact);
  UnfollowedRow(document.querySelector('#unfollowed-row'), contact);
  SuspendedRow(document.querySelector('#suspended-row'), contact);
};

// Exporting Contact component
export default Contact;
