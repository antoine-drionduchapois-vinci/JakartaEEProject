import Navigate from "../Components/Router/Navigate";

// Function to redirect based on user's role
function redirect(role) {
    switch (role) {
      case 'STUDENT':
        Navigate("/dashboardS"); // Redirect to student dashboard
        break;
      default:
        Navigate("/dashboardT"); // Redirect to TEACHER dashboard or for ADMIN to
        break;
    }
  }
  
  // Export the redirect function
  export default { redirect };