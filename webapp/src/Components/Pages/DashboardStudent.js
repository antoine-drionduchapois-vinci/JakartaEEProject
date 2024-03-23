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
  
      const jResponse = await response.json();
      console.log(jResponse);
      return jResponse;
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
      console.log(contactsInfo);
      return contactsInfo;
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
  
      const internshipInfo = await response.json();
      console.log("internshipInfo");
      console.log(internshipInfo);
      return internshipInfo;
      
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

    
  
    const bloc1 = `
    <h1 class="title">Dashboard Student</h1>
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
          <td id="nom"></td>
          <td id="prenom"></td>
          <td id="annee"></td>
          <td id="email"></td>
          <td id="telephone"></td>
        </tr>
      </tbody>
    </table>
    
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
          <td id="entreprise"></td>
          <td id="annee_stage"></td>
          <td id="responsable_nom"></td>
          <td id="responsable_telephone"></td>
          <td id="entreprise_contact"></td>
        </tr>
      </tbody>
    </table>
    
    <h2 class="title is-3">Contacts</h2>
    <div id="contactsContainer"></div>
    
    `;
    
    main.innerHTML = bloc1;
    
    // const contactsInfo = await fetchUserContacts();
    const userData = await fetchUser();
    const contactData = await fetchUserContacts();
    const internshipData = await fetchUserInternship();
    // const internshipData = await fetchUserInternship();
    console.log(userData.name);
    console.log(userData.surname);
    console.log(contactData);
    console.log(internshipData);
    
    // User Data
    /*
    document.getElementById('nom').textContent = userData.nom;
    document.getElementById('prenom').textContent = userData.prenom;
    document.getElementById('annee').textContent = userData.annee;
    document.getElementById('email').textContent = userData.email;
    document.getElementById('telephone').textContent = userData.telephone;
    /*
    // Internship Data
    document.getElementById('entreprise').textContent = internshipData.entreprise;
    document.getElementById('annee_stage').textContent = internshipData.annee_stage;
    document.getElementById('responsable_nom').textContent = internshipData.responsable_nom;
    document.getElementById('responsable_telephone').textContent = internshipData.responsable_telephone;
    document.getElementById('entreprise_contact').textContent = internshipData.entreprise_contact;
   

    // Display contacts
   
    const contactsContainer = document.getElementById('contactsContainer');
    contactsInfo.forEach(contact => {
      contactsContainer.innerHTML += `
        <div>
          <p>Entreprise: ${contact.entreprise.nom}</p>
          <p>Statut: ${contact.utilisateur.role}</p>
        </div>
      `;
    });
    */
  };
 
  export default DashboardStudent;
  