import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage } from '../../utils/render';
import stateTranslation from "../../utils/translation";

let enterprise;
let blacklistedState;

const main = document.querySelector('main');

const fetchEnterpriseContacts = async (id) => {
  console.log(id);
  try {
    const response = await fetch(`http://localhost:8080/contact/getEnterpriseContacts/${id}`);
    if (!response.ok) {
      throw new Error('Error retrieving enterprise contacts');
    }

    const contactsArray = await response.json(); // Assume this response is an array

    let contactsHtml = ''; // Initialize an empty string to accumulate HTML content
    for (let index = 0; index < contactsArray.length; index += 1) {
      const {
        userDTO: { name: studentName, surname: studentSurname },
        enterpriseDTO: { name: enterpriseName, blacklisted: isBlacklist },
        meetingPoint,
        state,
        refusalReason,
        year,
      } = contactsArray[index];

      const meeting = meetingPoint || ''; // Handle null values
      const reason = refusalReason || '';

      blacklistedState = isBlacklist;
      enterprise = enterpriseName;

      const translatedState = stateTranslation[state] || state;

          contactsHtml += `
              <tr>
              <td>${enterpriseName}</td>
              <td>${studentName}</td>
              <td>${studentSurname}</td>
              <td>${year}</td>
              <td>${meeting}</td>
              <td>${translatedState}</td>
              <td>${reason}</td>
              </tr>
          `;
    }

    return contactsHtml;
  } catch (error) {
    // console.error('Error retrieving enterprise contacts:', error);
    return "Pas de contact pour l'instant";
  }
};

const renderEnterprisePageDetails = (enterpriseContacts) => {
  let blacklistedMessage = '';
  if (blacklistedState) {
    blacklistedMessage += 'Entrprise blacklistée';
  }
  try {
    const combinedHtml = `
    <h2 class="title is-3 mt-3 mb-3 has-text-centered">Contacts avec l'entreprise ${enterprise}</h2>
    <h3 class="title is-4 mt-3 mb-3 has-text-centered">${blacklistedMessage}</h3>
      <table class="table is-striped ">
        <thead>
          <tr>
            <th>Entreprise</th>
            <th>Nom étudiant</th>
            <th>Prénom étudiant</th>  
            <th>Année</th>
            <th>Lieu RDV</th>
            <th>Etat</th>
            <th>Raison refus</th> 
          </tr>
        </thead>
        <tbody>
          ${enterpriseContacts}
        </tbody>
      </table>
    `;
    main.innerHTML = combinedHtml;
  } catch (error) {
    console.error('Error rendering enterprise page:', error);
    main.innerHTML = `<p>Error rendering data. Please try again later.</p>`;
  }
};

const renderBlacklistForm = () => {
  let htmlContent = ``;
  // HTML content for the profile page
  if (!blacklistedState) {
    htmlContent += `
    <main class="section">
        <div class="columns">
            <div class="column is-half">
              <h2 class="title is-3 mt-3 mb-3 has-text-centered">Black-lister l'entreprise</h2>
                <div class="box">
                    <form id="blacklistForm">
                        <div class="field">
                            <label class="label">Veuillez la raison pour laquelle vous souhaitez black-lister cette entreprise:</label>
                            <div class="control">
                                <input class="input" type="text" id= "blacklistReason">
                            </div>
                        </div>
                        <div id="error2">
                        </div>
                        <div class="field">
                            <div class="control has-text-centered">
                                <button class="button is-dark is-rounded" type="submit" id="blacklist-submit">Black-lister</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>
    `;
  }
  // Set the HTML content to the main element
  main.innerHTML += htmlContent;
};

const EnterpriseDetails = async () => {
  clearPage();

  const id = new URLSearchParams(window.location.search).get('id');

  // Selecting main element from the DOM
  try {
    main.innerHTML = `<p>Loading Data...</p>`;
    const enterpriseContacts = await fetchEnterpriseContacts(id); // Ensure this returns HTML string
    renderEnterprisePageDetails(enterpriseContacts);
  } catch (error) {
    // console.error('Error loading dashboard:', error);
    // Optionally, render an error message in the main container or log it
    main.innerHTML = `<p>Error loading data. Please try again later.</p>`;
  }
  renderBlacklistForm();
  if (!blacklistedState) {
    const blacklistForm = document.querySelector('#blacklistForm'); // Empêcher le comportement par défaut du formulaire lors de la soumission
    blacklistForm.addEventListener('submit', (event) => {
      event.preventDefault(); // Empêcher le comportement par défaut du formulaire
      blacklistEnterprise(id); // Appeler la fonction blacklistEnterprise lors de la soumission
    });
  }
};

async function blacklistEnterprise(id) {
  const blacklistForm = document.querySelector('#blacklistForm');
  const blacklistMessage = blacklistForm.elements.blacklistReason.value;
  try {
    // Prepare data for API request
    const data = {
      enterpriseId: id,
      blacklistedReason: blacklistMessage,
    };

    // Make API request to change phone number
    const response = await fetch('http://localhost:8080/ent/blacklist', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-Type': 'application/json',
        Authorization: getAuthenticatedUser().token,
      },
    });

    // Handle response from the server
    if (!response.ok) {
      throw new Error(`Failed to blacklist the enterprise`);
    }

    // Si la mise sur liste noire réussit, mettre à jour les contacts de l'entreprise
    const updatedEnterpriseContacts = await fetchEnterpriseContacts(id);
    renderEnterprisePageDetails(updatedEnterpriseContacts);
  } catch (e) {
    console.error('Error:', e);
  }
  return EnterpriseDetails;
}

export default EnterpriseDetails;
