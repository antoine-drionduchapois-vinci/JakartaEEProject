import { clearPage, renderPageTitle } from "../../utils/render";

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
        const { enterprise, enterprise_name, state } = contactsArray[index];
        contactsHtml += `
              <tr>
              <td>${enterprise_name}</a></td>
              <td> ${state}</td>
              <td>${enterprise_name}</a></td>
              <td> ${state}</td>
              </tr>
          `;
      }
  
      return contactsHtml;
    } catch (error) {
      console.error('Error retrieving user contacts:', error);
      return "Pas de contact pour l'instant";
    }
  };
  


const EnterpriseDetails = () => {
    clearPage();
    renderPageTitle('Enterprise Details');

  
    // Selecting main element from the DOM


    const queryString = window.location.search; 
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('id');

    const main = document.querySelector('main');
    const bloc1 = `Entreprise Details id : ${id}`;
    main.innerHTML = bloc1;


}

export default EnterpriseDetails;