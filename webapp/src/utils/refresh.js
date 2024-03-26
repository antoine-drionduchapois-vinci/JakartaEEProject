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
       // Exit the function to prevent further execution
    }
}

  export default fetchUserOnRefresh;
  

