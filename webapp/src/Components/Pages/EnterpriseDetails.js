import { clearPage, renderPageTitle } from "../../utils/render";






const fetchEnterpriseContacts = async (id) => {
    console.log(id);
    try {
      const response = await fetch(`http://localhost:8080/contact/getEnterpriseContacts/${id}`);
      if (!response.ok) {
        throw new Error('Error retrieving user contacts');
      }
  
      const contactsArray = await response.json();
      // Object { enterprise_name: "LetsBuild", student_name: "skile", student_surname: "Carole", state: "accepted", year: "2023-2024", refusal_reason: null, meeting_point: "Dans l'entreprise" }
      
      let contactsHtml = ''; // Initialize an empty string to accumulate HTML content
      for (let index = 0; index < contactsArray.length; index += 1) {
        contactsHtml += `
              <tr>
              <td>${contactsArray[index].enterprise_name}</td>
              <td>${contactsArray[index].student_name}</td>
              <td>${contactsArray[index].student_surname}</td>
              <td>${contactsArray[index].year}</td>
              <td>${contactsArray[index].meeting_point}</td>
              <td>${contactsArray[index].state}</td>
              <td>${contactsArray[index].refusal_reason}</td>
              </tr>
          `;
      }
      
      return contactsHtml;
    } catch (error) {
      console.error('Error retrieving user contacts:', error);
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