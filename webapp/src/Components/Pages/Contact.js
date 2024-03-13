// Importing rendering utility functions
import { clearPage, renderPageTitle } from '../../utils/render';
import { getAuthenticatedUser } from '../../utils/auths';

// Fonction pour récupérer les données des entreprises
const fetchEnterprises = async () =>
  fetch('http://localhost:8080/ent/enterprises')
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => console.error(error));

const renderEnterprisesList = async () => {
  const enterprises = await fetchEnterprises();
  console.log('🚀 ~ renderEnterprisesList ~ enterprises:', enterprises);
};

const initiateContact = async (data) =>
  fetch('http://localhost:8080/contact', { method: 'POST', body: data })
    .then((response) => response.status === 200)
    .catch((error) => console.error(error));

const checkInput = (element) => {
  if (element.value === '') {
    element.classList.replace('is-primary', 'is-danger');
    return false;
  }
  element.classList.replace('is-danger', 'is-primary');
  return true;
};

// Contact component definition
const Contact = () => {
  // Selecting main element from the DOM
  const main = document.querySelector('main');
  // Clearing the page
  clearPage();
  // Rendering page title as 'Contact'
  renderPageTitle('Contact');

  const contentElement = document.createElement('div');
  contentElement.id = 'content';
  main.appendChild(contentElement);

  contentElement.innerHTML = `
    <div class="columns p-4">
        <div class="column is-one-fifth bg-primary">
            <div class="d-flex gap-2 align-items-center">
                <div class="border border-black rounded-circle" style="width: 20px; height: 20px;"></div>
                Initié
            </div>
        </div>
        <div class="column bg-secondary">
            <label for="enterprise">Entreprise *</label>
            <input class="input is-primary" type="text" id="enterprise">
        </div>
        <div class="column bg-secondary">
        <label for="label">Appelation</label>
            <input class="input is-primary" type="text" id="label">
        </div>
        <div class="column bg-secondary">
            <label for="address">Addresse *</label>
            <input class="input is-primary" type="text" id="address">
        </div>
        <div class="column bg-secondary">
            <label for="contact">Téléphone / Email *</label>
            <input class="input is-primary" type="text" id="contact">
        </div>
        <div class="column bg-secondary d-flex align-items-end">
            <button class="button is-fullwidth" id="submit">Initier</button>
        </div>
    </div>
  `;

  const enterprise = { element: document.querySelector('#enterprise'), isValid: false };
  enterprise.element.addEventListener('input', (e) => {
    enterprise.value = e.target.value;
    enterprise.isValid = checkInput(e.target);
  });

  const label = { element: document.querySelector('#label'), isValid: false };
  label.element.addEventListener('input', (e) => {
    label.value = e.target.value;
    checkInput(e.target);
  });

  const address = { element: document.querySelector('#address'), isValid: false };
  address.element.addEventListener('input', (e) => {
    address.value = e.target.value;
    checkInput(e.target);
  });

  const contact = { element: document.querySelector('#contact'), isValid: false };
  contact.element.addEventListener('input', (e) => {
    contact.value = e.target.value;
    checkInput(e.target);
  });

  const user = getAuthenticatedUser();
  console.log('🚀 ~ Contact ~ user:', user);

  renderEnterprisesList();

  const submit = document.querySelector('#submit');
  submit.addEventListener('click', () => {
    if (!enterprise.isValid || !label.isValid || !address.isValid || !contact.isValid) {
      checkInput(enterprise.element);
      checkInput(label.element);
      checkInput(address.element);
      checkInput(contact.element);
      return;
    }
    initiateContact({
      enterprise: enterprise.value,
      label: label.value,
      address: address.value,
      contact: contact.value,
    });
  });
};

// Exporting Contact component
export default Contact;
