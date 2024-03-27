/* eslint-disable camelcase */
import { getAuthenticatedUser } from '../../utils/auths';
import fetchUserOnRefresh from '../../utils/refresh';

import { clearPage, renderPageTitle } from '../../utils/render';
import Navigate from '../Router/Navigate';

fetchUserOnRefresh();

const fetchUser = async () => {
  console.log(getAuthenticatedUser().token);
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ token: getAuthenticatedUser().token }), // Object shorthand used here
  };

  try {
    const response = await fetch('http://localhost:8080/users/getUserInfoById', options);
    if (!response.ok) {
      throw new Error('Erreur lors de la récupération des données du user');
    }

    const userData = await response.json();
    console.log(userData);

    const blocUser = `
      <h2 class="title is-3">Profil</h2>
      <table class="table is-striped is-fullwidth">
        <tbody>
          <tr>
            <th>Nom</th>
            <td>${userData.name}</td>
          </tr>
          <tr>
            <th>Prénom</th>
            <td>${userData.surName}</td>
          </tr>
          <tr>
            <th>Année</th>
            <td>${userData.year}</td>
          </tr>
          <tr>
            <th>Email</th>
            <td>${userData.email}</td>
          </tr>
          <tr>
            <th>Téléphone</th>
            <td>${userData.phone}</td>
          </tr>
        </tbody>
      </table>
      `;

    return blocUser;
  } catch (error) {
    console.error('Erreur lors de la récupération des données du user : ', error);
    const blocError = `<p>${error}</p>`;
    return blocError;
  }
};

const fetchUserContacts = async () => {
  console.log(getAuthenticatedUser());
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ token: getAuthenticatedUser().token }),
  };

  try {
    const response = await fetch('http://localhost:8080/contact/getUserContacts', options);
    if (!response.ok) {
      throw new Error('Error retrieving user contacts');
    }

    const contactsInfo = await response.json();
    const contactsArray = contactsInfo.contact;
    let contactsHtml = ''; // Initialize an empty string to accumulate HTML content
    for (let index = 0; index < contactsArray.length; index += 1) {
      const { contact_id, enterprise_name, state } = contactsArray[index];
      contactsHtml += `
            <tr>
            <td><a class="enterprise_link" data-contact-id="${contact_id}">${enterprise_name}</a></td>
            <td> ${state}</td>
            </tr>
        `;
    }

    return contactsHtml;
  } catch (error) {
    console.error('Error retrieving user contacts:', error);
    const blocError = `<p>${error}</p>`;
    return blocError;
  }
};

const fetchUserInternship = async () => {
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ token: getAuthenticatedUser().token }),
  };

  try {
    const response = await fetch('http://localhost:8080/int', options);

    if (!response.ok) {
      throw new Error('Error retrieving user internships');
    }
    const internshipData = await response.json();
    const blocInternship = `
      <h2 class="title is-3">Stage</h2>
      <table class="table is-striped is-fullwidth">
        <tbody>
          <tr>
            <th>Entreprise</th>
            <td>${internshipData.enterprise}</td>
          </tr>
          <tr>
            <th>Année</th>
            <td>${internshipData.year}</td>
          </tr>
          <tr>
            <th>Responsable</th>
            <td>${internshipData.responsbile}</td> 
          <tr>
            <th>Téléphone</th>
            <td>${internshipData.phone}</td>
          </tr>
          <tr>
            <th>Contact Entreprise</th>
            <td>${internshipData.contact}</td>
          </tr>
        </tbody>
      </table>
      `;
    return blocInternship;
  } catch (error) {
    console.error('Error retrieving user internship:', error);
    // If there's an error, still return the default stage title with error message
    return `
        <h2 class="title is-3">Stage</h2>
        <p>Pas de stage pour l'instant</p>
      `;
  }
};

const DashboardStudent = async () => {
  const main = document.querySelector('main');

  // Nettoyage de la page
  clearPage();
  // Rendu du titre de la page en 'Dashboard Teacher'
  renderPageTitle('Dashboard Student');

  try {
    main.innerHTML = `<p>Loading Data...</p>`;
    // Await all fetch functions
    const userHtml = await fetchUser(); // Ensure this returns HTML string
    // Ensure this builds and returns an HTML string
    const internshipHtml = await fetchUserInternship(); // Ensure this returns HTML string
    const contactsHtml = await fetchUserContacts();
    // Combine all HTML segments
    const combinedHtml = `
  <div class="tables-container" style="display: flex; justify-content: space-around;">
    <div>${userHtml}</div>
    <div>${internshipHtml}</div>
    <div>
      <h2 class="title is-3">Contact</h2>
      <table class="table is-striped is-fullwidth">
        <thead>
          <tr>
            <th>Entreprise</th>
            <th>Etat</th>
            <th><a id="addContact"> + </a></th>
          </tr>
        </thead>
        <tbody>
          ${contactsHtml}
        </tbody>
      </table>
    </div>
  </div>
`;

    main.innerHTML = combinedHtml;
    const addContact = document.getElementById('addContact');
    addContact.addEventListener('click', () => {
      Navigate('/contact');
    });

    document.querySelectorAll('.enterprise_link').forEach((link) => {
      link.addEventListener('click', (e) => {
        e.preventDefault();
        const { contactId } = e.target.dataset;
        Navigate(`/contact?id=${contactId}`);
      });
    });
  } catch (error) {
    console.error('Error loading dashboard:', error);
    // Optionally, render an error message in the main container or log it
    main.innerHTML = `<p>Error loading data. Please try again later.</p>`;
  }
};

export default DashboardStudent;
