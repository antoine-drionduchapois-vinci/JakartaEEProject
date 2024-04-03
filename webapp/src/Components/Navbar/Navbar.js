// eslint-disable-next-line no-unused-vars
// Importing necessary modules and functions
import { getAuthenticatedUser, isAuthenticated } from '../../utils/auths';
import Navigate from '../Router/Navigate';

// Constant for site name
const SITE_NAME = 'StageLink IPL';

// Navbar component definition
const Navbar = () => {
  // Rendering the navbar
  renderNavbar();
};

// Function to render the navbar
function renderNavbar() {
  // Getting authenticated user data
  const authenticatedUser = getAuthenticatedUser();

  // HTML for navbar when user is anonymous
  const anonymousUserNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-info">
      <div class="container-fluid d-flex justify-content-center">
          <a class="navbar-brand bg-white border border-dark rounded-pill px-5 fs-4" id="home" href="#">${SITE_NAME}</a>
      </div>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <!-- Nav items for anonymous users -->
        </ul>
      </div>
    </nav>
  `;

  // HTML for navbar when user is authenticated
  const authenticatedUserNavbar = `
    <nav class="navbar navbar-expand-lg navbar-light bg-info ">
      <div class="container-fluid d-flex justify-content-end">
        <div>
          <a class="navbar-brand bg-white border border-dark rounded-pill px-5 fs-4" id="home1" href="#">${SITE_NAME}</a>
        </div>
      </div>
      <div>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-left" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <!-- Nav items for authenticated users -->
            <li class="nav-item">
              <a class="nav-link" href="#" data-uri="/logout">Logout</a>
            </li>    
            <li class="nav-item">
              <a class="nav-link " id="profileRedirect" href="#">${authenticatedUser?.email}</a>
            </li>           
          </ul>
        </div>
      </div>
    </nav>
  `;

  // Selecting navbar wrapper element
  const navbar = document.querySelector('#navbarWrapper');

  // Setting innerHTML based on authentication status
  navbar.innerHTML = isAuthenticated() ? authenticatedUserNavbar : anonymousUserNavbar;

  
  if(isAuthenticated() === false){
    const renderHome = document.getElementById('home');
    renderHome.addEventListener('click', () => Navigate('/'));
  }

  if(isAuthenticated() === true){
    const profile = document.getElementById('profileRedirect');
    profile.addEventListener('click', () => Navigate('/profile'));
    const renderHome1 = document.getElementById('home1')
    if(authenticatedUser?.role === 'STUDENT'){
      renderHome1.addEventListener('click', () => Navigate('/dashboardS'));
    } else {
      renderHome1.addEventListener('click', () => Navigate('/dashboardT'));
    }
  }
  
}

// Exporting Navbar component
export default Navbar;
