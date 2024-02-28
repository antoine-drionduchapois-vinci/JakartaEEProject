// eslint-disable-next-line no-unused-vars
import { Navbar as BootstrapNavbar } from 'bootstrap';
import { getAuthenticatedUser, isAuthenticated } from '../../utils/auths';

const SITE_NAME = 'StageLink IPL';

const Navbar = () => {
  renderNavbar();
};

function renderNavbar() {
  const authenticatedUser = getAuthenticatedUser();

  const anonymousUserNavbar = `
<nav class="navbar navbar-expand-lg navbar-light bg-info">
      <div class="container-fluid d-flex justify-content-center">
          <a class="navbar-brand bg-white border border-dark rounded-pill px-5 fs-4" href="#">${SITE_NAME}</a>
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
            </li>            
          </ul>
        </div>
    </nav>
`;

  const authenticatedUserNavbar = `
  <nav class="navbar navbar-expand-lg navbar-light bg-info ">
  <div class="container-fluid d-flex justify-content-center"> <!-- Utilisation de justify-content-between -->
    <div>
      <a class="navbar-brand mx-auto bg-white border border-dark rounded-pill px-5 fs-4" href="#">${SITE_NAME}</a>
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
      <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">           
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/logout">Logout</a>
          </li>    
          <li class="nav-item">
            <a class="nav-link " href="#">${authenticatedUser?.email}</a>
          </li>           
        </ul>
      </div>
    </div>
</nav>
`;

  const navbar = document.querySelector('#navbarWrapper');

  navbar.innerHTML = isAuthenticated() ? authenticatedUserNavbar : anonymousUserNavbar;
}

export default Navbar;
