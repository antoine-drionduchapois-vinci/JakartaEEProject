import Navigate from "../Components/Router/Navigate";
import { getAuthenticatedUser } from './auths';


async function fetchUserOnRefresh(){


    if (window.location.pathname === "/login" || window.location.pathname === "/register"){
        return;
    }

    const user = getAuthenticatedUser();

    // Check if the user object or token is undefined
    if (!user || !user.token) {
      console.error('No authenticated user found.');
      Navigate('/'); // Redirect to homepage or login page
      return; // Exit the function to prevent further execution
    }
  
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
     
    } catch (error) {
      console.error('Erreur lors de la récupération des données du user : ', error);
      Navigate('/');
    }
  };

  export default fetchUserOnRefresh;
  
