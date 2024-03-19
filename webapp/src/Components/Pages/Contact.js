// Importing rendering utility functions
import { clearPage, renderPageTitle } from '../../utils/render';

import { getAuthenticatedUser } from '../../utils/auths';
import autocomplete from '../../services/autocomplete';

const contactId = 1; // TODO: will then be acquired by route param

const initiateContact = async (data) =>
  fetch('http://localhost:8080/contact', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  })
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
                <div class="status-circle">
                  <div id="initiated-circle" class="status-circle-item" hidden></div>  
                </div>
                Initié
            </div>
        </div>
        <div class="column bg-secondary">
            <label for="enterprise">Entreprise *</label>
            <div class="autocomplete">
              <input autocomplete="off" class="input is-primary" type="text" id="enterprise">
            </div>
        </div>
        <div class="column bg-secondary">
            <label for="label">Appelation</label>
            <div class="autocomplete">
              <input autocomplete="off" class="input is-primary" type="text" id="label">
            </div>
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

  const initiatedCircle = document.querySelector('#initiated-circle');
  const enterprise = { element: document.querySelector('#enterprise'), isValid: false };
  const label = { element: document.querySelector('#label'), isValid: false };
  const address = { element: document.querySelector('#address'), isValid: false };
  const contact = { element: document.querySelector('#contact'), isValid: false };

  let enterprises;
  const fetchEnterprises = async () => {
    enterprises = await fetch('http://localhost:8080/ent/enterprises')
      .then((response) => response.json())
      .then((data) => data.enterprises)
      .catch((error) => console.error(error));
    autocomplete(enterprise.element, [...new Set(enterprises.map((e) => e.nom))]);
  };
  fetchEnterprises();

  let contactObj;
  const fetchContact = async () => {
    contactObj = await fetch(`http://localhost:8080/contact?contactId=${contactId}`)
      .then((data) => data.json())
      .catch((error) => console.error(error));

    if (contactObj.state === 'Confirmed') initiatedCircle.removeAttribute('hidden');
  };
  fetchContact();

  let foundEnterprise;
  const autoFillFields = () => {
    const enterpriseText = enterprise.element.value;
    const foundEnterprises = enterprises.filter((e) => e.nom === enterpriseText);
    if (foundEnterprises) {
      autocomplete(
        label.element,
        foundEnterprises.map((e) => e.appellation),
      );
      const labelText = label.element.value;
      foundEnterprise = foundEnterprises.find((e) => e.appellation === labelText);
      if (foundEnterprise) {
        address.element.value = foundEnterprise.adresse;
        address.element.setAttribute('disabled', true);
        contact.element.value = foundEnterprise.telephone;
        contact.element.setAttribute('disabled', true);
      } else {
        foundEnterprise = null;
        address.element.removeAttribute('disabled');
        contact.element.removeAttribute('disabled');
      }
    }
  };

  enterprise.element.addEventListener('input', (e) => {
    enterprise.value = e.target.value;
    enterprise.isValid = checkInput(e.target);
    autoFillFields(e.target.value);
  });

  label.element.addEventListener('input', (e) => {
    label.value = e.target.value;
    label.isValid = checkInput(e.target);
    autoFillFields(e.target.value);
  });

  address.element.addEventListener('input', (e) => {
    address.value = e.target.value;
    address.isValid = checkInput(e.target);
  });

  contact.element.addEventListener('input', (e) => {
    contact.value = e.target.value;
    contact.isValid = checkInput(e.target);
  });

  const user = getAuthenticatedUser();
  console.log('🚀 ~ Contact ~ user:', user);

  const submit = document.querySelector('#submit');
  submit.addEventListener('click', () => {
    if (foundEnterprise) {
      initiateContact({ userId: user.id, enterpriseId: foundEnterprise.entreprise_id });
      return;
    }

    if (!enterprise.isValid || !label.isValid || !address.isValid || !contact.isValid) {
      checkInput(enterprise.element);
      checkInput(label.element);
      checkInput(address.element);
      checkInput(contact.element);
      return;
    }
    initiateContact({
      userId: user.id,
      enterpriseName: enterprise.value,
      enterpriseLabel: label.value,
      enterpriseAddress: address.value,
      enterpriseContact: contact.value,
    });
  });
};

// Exporting Contact component
export default Contact;
