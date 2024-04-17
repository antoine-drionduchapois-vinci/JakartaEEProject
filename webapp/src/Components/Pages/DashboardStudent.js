/* eslint-disable camelcase */
import { getAuthenticatedUser } from '../../utils/auths';

import { clearPage} from '../../utils/render';
import Navigate from '../Router/Navigate';

let urlId;

const fetchUser = async () => {
  const options = {
    method: 'GET',
    headers: {
      Authorization: getAuthenticatedUser().token,
    },
    // Object shorthand used here
  };

  try {

    const url = `http://localhost:8080/users/getUserInfoById?id=${urlId}`;
    const response = await fetch(url, options);
    if (!response.ok) {
      throw new Error('Erreur lors de la récupération des données du user');
    }

    const userData = await response.json();

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
            <td>${userData.surname}</td>
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
  console.log(getAuthenticatedUser().token);

  const options = {
    method: 'GET',
    headers: {
      Authorization: getAuthenticatedUser().token,
    },
  };

  try {
    const url = `http://localhost:8080/contact/getUserContacts?id=${urlId}`;
    const response = await fetch(url, options);
    if (!response.ok) {
      throw new Error('Error retrieving user contacts');
    }

    const contactsInfo = await response.json();
 
    const contactsArray = contactsInfo;  // Assuming the array is now directly the response JSON
    console.log(contactsArray);
    let contactsHtml = ''; // Initialize an empty string to accumulate HTML content
    for (let index = 0; index < contactsArray.length; index += 1) {
      const {
        contactId,
        enterpriseDTO: { name: enterprise_name },
        state,
        meetingPoint,
        refusalReason,
        year,
      } = contactsArray[index];
      const reason = refusalReason === null ? '' : refusalReason;
      const meeting = meetingPoint === null ? '' : meetingPoint;
      contactsHtml += `
            <tr>
            <td><a class="enterprise_link" data-contact-id="${contactId}">${enterprise_name}</a></td>
            <td> ${state}</td>
            <td> ${meeting}</td>
            <td> ${reason}</td>
            <td> ${year}</td>
            </tr>
        `;
    }
    return contactsHtml;
    } catch (error) {
      console.error('Failed to load contacts:', error);
    }
    return "Pas de contact pour l'instant";
  };


const fetchUserInternship = async () => {
  const options = {
    method: 'GET',
    headers: {
      Authorization: getAuthenticatedUser().token,
    },
  };

  try {
    const url = `http://localhost:8080/int?id=${urlId}`
    const response = await fetch(url, options);

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
  const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('id');
    
    if (id) {
      urlId = id;
    } else {
      urlId = getAuthenticatedUser().id;
    }

  try {
    main.innerHTML = `<p>Loading Data...</p>`;
    // Await all fetch functions
    const userHtml = await fetchUser(); // Ensure this returns HTML string
    // Ensure this builds and returns an HTML string
    const internshipHtml = await fetchUserInternship(); // Ensure this returns HTML string
    const contactsHtml = await fetchUserContacts();
    // Combine all HTML segments
    const combinedHtml = `
    
    <h2 class="title is-3 mt-3 mb-5 has-text-centered">Tableau de bord étudiant</h2>
    <section class="hero is-text is-fullheight">
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
                <th>RDV</th>
                <th>Raison refus</th>
                <th>Année</th>
                <th><a id="addContact"> + </a></th>
              </tr>
            </thead>
            <tbody>
              ${contactsHtml}
            </tbody>
          </table>
        </div>
      </div>
    </section>
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
