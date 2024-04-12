import { clearPage, renderPageTitle } from "../../utils/render";






const fetchEnterpriseContacts = async (id) => {
  console.log(id);
  try {
      const response = await fetch(`http://localhost:8080/contact/getEnterpriseContacts/${id}`);
      if (!response.ok) {
          throw new Error('Error retrieving enterprise contacts');
      }

      const contactsArray = await response.json();  // Assume this response is an array
      console.log(contactsArray);
      let contactsHtml = ''; // Initialize an empty string to accumulate HTML content
      for (let index = 0; index < contactsArray.length; index += 1) {
          const {
              contactId, 
              userDTO: { name: student_name, surname: student_surname },
              enterpriseDTO: { name: enterprise_name },
              meetingPoint,
              state,
              refusalReason,
              year,
          } = contactsArray[index];

          const meeting = meetingPoint || '';  // Handle null values
          const reason = refusalReason || '';

          contactsHtml += `
              <tr>
              <td>${enterprise_name}</td>
              <td>${student_name}</td>
              <td>${student_surname}</td>
              <td>${year}</td>
              <td>${meeting}</td>
              <td>${state}</td>
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



const EnterpriseDetails = async () => {
    const main = document.querySelector('main');
    clearPage();
    renderPageTitle('Enterprise Details');
    const id = new URLSearchParams(window.location.search).get('id');
  
    // Selecting main element from the DOM
    try {
        main.innerHTML = `<p>Loading Data...</p>`;
        const enterpriseContacts = await fetchEnterpriseContacts(id); // Ensure this returns HTML string
    
        const combinedHtml = `
        <h2 class="title is-3">Contacts de l'entreprise</h2>
        <table class="table is-striped is-fullwidth">
          <thead>
            <tr>
              <th>Entreprise</th>
              <th>Nom</th>
              <th>Prénom</th>  
              <th>Année</th>
              <th>RDV</th>
              <th>Etat</th>
              <th>Raison refus</th>
             
             
            
            </tr>
          </thead>
          <tbody>
            ${enterpriseContacts}
          </tbody>
        </table>
      </div>

      
     
    `;

        main.innerHTML = combinedHtml;
    } catch (error) {
        console.error('Error loading dashboard:', error);
        // Optionally, render an error message in the main container or log it
        main.innerHTML = `<p>Error loading data. Please try again later.</p>`;
    }

     


}

export default EnterpriseDetails;