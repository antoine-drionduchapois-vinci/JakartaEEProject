import { clearPage} from "../../utils/render";
import stateTranslation from "../../utils/translation";

let enterprise;
const main = document.querySelector('main');

const fetchEnterpriseContacts = async (id) => {
  try {
      const response = await fetch(`http://localhost:8080/contact/getEnterpriseContacts/${id}`);
      if (!response.ok) {
          throw new Error('Error retrieving enterprise contacts');
      }

      const contactsArray = await response.json();  // Assume this response is an array
      let contactsHtml = ''; // Initialize an empty string to accumulate HTML content
      for (let index = 0; index < contactsArray.length; index += 1) {
          const {
              userDTO: { name: studentName, surname: studentSurname },
              enterpriseDTO: { name: enterpriseName },
              meetingPoint,
              state,
              refusalReason,
              year,
          } = contactsArray[index];

          const meeting = meetingPoint || '';  // Handle null values
          const reason = refusalReason || '';

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
      console.error('Error retrieving enterprise contacts:', error);
      return "Pas de contact pour l'instant";
  }
};

const renderEnterprisePageDetails = (enterpriseContacts) => {
  try {
    const combinedHtml = `
    <h2 class="title is-3 mt-3 mb-3 has-text-centered">Contact avec l'entreprise ${enterprise}</h2>
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

const EnterpriseDetails = async () => {
  
  clearPage();

  const id = new URLSearchParams(window.location.search).get('id');

  // Selecting main element from the DOM
  try {
    main.innerHTML = `<p>Loading Data...</p>`;
    const enterpriseContacts = await fetchEnterpriseContacts(id); // Ensure this returns HTML string
    renderEnterprisePageDetails(enterpriseContacts);
    
  } catch (error) {
    console.error('Error loading dashboard:', error);
    // Optionally, render an error message in the main container or log it
    main.innerHTML = `<p>Error loading data. Please try again later.</p>`;
  }
};

export default EnterpriseDetails;