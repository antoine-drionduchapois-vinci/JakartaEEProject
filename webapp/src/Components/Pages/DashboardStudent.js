import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';


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
      <thead>
        <tr>
          <th>Nom</th>
          <th>Prénom</th>
          <th>Année</th>
          <th>Email</th>
          <th>Téléphone</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td id="nom">${userData.name}</td>
          <td id="prenom">${userData.surName}</td>
          <td id="annee">${userData.year}</td>
          <td id="email">${userData.email}</td>
          <td id="telephone">${userData.phone}</td>
        </tr>
      </tbody>
    </table>`;

      return blocUser;
    } catch (error) {
      console.error('Erreur lors de la récupération des données du user : ', error);
      return null;
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
      console.log(contactsInfo.contact[0]);
      let contactsHtml = ''; // Initialize an empty string to accumulate HTML content
      for (let index = 0; index < contactsArray.length; index+=1) {
        contactsHtml += `
            <tr>
            <td> ${contactsArray[index].enterprise_name}</td>
            <td> ${contactsArray[index].state}</td>
            </tr>
            
         
        `;
        
      }
      return contactsHtml;
    } catch (error) {
      console.error('Error retrieving user contacts:', error);
      return null;
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
      console.log("internshipInfo");
      console.log(internshipData);
      const blocInternship = `
      <h2 class="title is-3">Stage</h2>
      <table class="table is-striped is-fullwidth">
        <thead>
          <tr>
            <th>Entreprise</th>
            <th>Année</th>
            <th>Responsable</th>
            <th>Téléphone</th>
            <th>Contact Entreprise</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td id="entreprise">${internshipData.enterprise}</td>
            <td id="annee_stage">${internshipData.year}</td>
            <td id="responsable_nom">${internshipData.responsbile}</td>
            <td id="responsable_telephone">${internshipData.phone}</td>
            <td id="entreprise_contact">${internshipData.contact}</td>
          </tr>
        </tbody>
      </table>
      
      `;
      return blocInternship;
      
    } catch (error) {
      console.error('Error retrieving user internship:', error);
      return null;
    }
  };
  
  const DashboardStudent = async () => {
    const main = document.querySelector('main');
    
    // Nettoyage de la page
  clearPage();
  // Rendu du titre de la page en 'Dashboard Teacher'
  renderPageTitle('Dashboard Student');

      
  try {
    // Await all fetch functions
    const userHtml = await fetchUser(); // Ensure this returns HTML string
    const contactsHtml = await fetchUserContacts(); // Ensure this builds and returns an HTML string
    const internshipHtml = await fetchUserInternship(); // Ensure this returns HTML string
    
    // Combine all HTML segments
    const combinedHtml = `
      ${userHtml}
      ${internshipHtml}
      <h2 class="title is-3">Contact</h2>
      <table class="table is-striped is-fullwidth">
        <thead>
          <tr>
          <th>Entreprise</th>
          <th>Etat</th>
          </tr>
        </thead>
        <tbody>
      
          ${contactsHtml}
      
        </tbody>
      </table>
      
      
    `;
    
    main.innerHTML = combinedHtml;
  } catch (error) {
    console.error('Error loading dashboard:', error);
    // Optionally, render an error message in the main container or log it
    main.innerHTML = `<p>Error loading data. Please try again later.</p>`;
  }
};
    
   

  
  export default DashboardStudent;
  