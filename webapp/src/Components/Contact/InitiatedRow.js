import autocomplete from '../../services/autocomplete';
import checkInput from '../../services/checkInput';

const initiateContact = (data) =>
  fetch('http://localhost:8080/contact', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  })
    .then(() => window.location.reload())
    .catch((error) => console.error(error));

let foundEnterprise;
const autoFillFields = (
  enterprises,
  enterpriseValue,
  labelElement,
  addressElement,
  phoneElement,
  emailElement,
) => {
  const labelE = labelElement;
  const addressE = addressElement;
  const phoneE = phoneElement;
  const emailE = emailElement;
  const foundEnterprises = enterprises.filter((e) => e.nom === enterpriseValue);
  if (foundEnterprises) {
    autocomplete(
      labelE,
      foundEnterprises.map((e) => e.appellation),
    );
    const labelValue = labelE.value;
    foundEnterprise = foundEnterprises.find((e) => e.appellation === labelValue);
    if (foundEnterprise) {
      addressE.value = foundEnterprise.address;
      addressE.setAttribute('disabled', true);
      phoneE.value = foundEnterprise.phone;
      phoneE.setAttribute('disabled', true);
      emailE.value = foundEnterprise.email;
      emailE.setAttribute('disabled', true);
    } else {
      addressE.removeAttribute('disabled');
      phoneE.removeAttribute('disabled');
      emailE.removeAttribute('disabled');
    }
  }
};

const InitiatedRow = (htmlElement, userData, contactData, enterprisesData) => {
  const html = htmlElement;
  html.innerHTML = `
    <div class="column is-one-fifth">
        <div class="d-flex gap-2 align-items-center">
            <div class="status-circle">
                <div id="initiated-circle" class="status-circle-item" hidden></div>
            </div>
            Initié
        </div>
    </div>
    <div class="column">
        <label for="enterprise">Entreprise</label>
        <div class="autocomplete">
            <input autocomplete="off" class="input is-primary" type="text" id="enterprise">
        </div>
    </div>
    <div class="column">
        <label for="label">Appelation</label>
        <div class="autocomplete">
          <input autocomplete="off" class="input is-primary" type="text" id="label">
        </div>
    </div>
    <div class="column">
        <label for="address">Addresse</label>
        <input class="input is-primary" type="text" id="address">
    </div>
    <div class="column">
        <label for="phone">Téléphone</label>
    <input class="input is-primary" type="text" id="phone">
    </div>
    <div class="column">
        <label for="email">Email</label>
    <input class="input is-primary" type="text" id="email">
    </div>
    <div class="column d-flex align-items-end">
        <button class="button is-fullwidth" id="submit-initiated">Initier</button>
    </div>
`;

  const initiatedCircle = document.querySelector('#initiated-circle');
  const enterpriseInput = { element: document.querySelector('#enterprise'), isValid: false };
  const labelInput = { element: document.querySelector('#label'), isValid: false };
  const addressInput = { element: document.querySelector('#address'), isValid: false };
  const phoneInput = { element: document.querySelector('#phone'), isValid: false };
  const emailInput = { element: document.querySelector('#email'), isValid: false };
  const submit = document.querySelector('#submit-initiated');

  if (contactData) {
    if (contactData.state !== 'refused' || contactData.state !== 'unfollowed')
      initiatedCircle.removeAttribute('hidden');
    enterpriseInput.element.value = contactData.enterprise.name;
    enterpriseInput.element.setAttribute('disabled', true);
    labelInput.element.value = contactData.enterprise.label ? contactData.enterprise.label : '';
    labelInput.element.setAttribute('disabled', true);
    addressInput.element.value = contactData.enterprise.adress;
    addressInput.element.setAttribute('disabled', true);
    phoneInput.element.value = contactData.enterprise.contact ? contactData.enterprise.contact : '';
    phoneInput.element.setAttribute('disabled', true);
    emailInput.element.value = contactData.enterprise.email ? contactData.enterprise.email : '';
    emailInput.element.setAttribute('disabled', true);
    submit.setAttribute('disabled', true);
  }

  if (enterprisesData) {
    autocomplete(enterpriseInput.element, [...new Set(enterprisesData.map((e) => e.nom))]);
  }

  enterpriseInput.element.addEventListener('input', (e) => {
    enterpriseInput.isValid = checkInput(e.target);
    autoFillFields(
      enterprisesData,
      e.target.value,
      labelInput.element,
      addressInput.element,
      phoneInput.element,
      emailInput.element,
    );
  });

  labelInput.element.addEventListener('input', (e) => {
    labelInput.isValid = checkInput(e.target);
    autoFillFields(
      enterprisesData,
      enterpriseInput.element.value,
      labelInput.element,
      addressInput.element,
      phoneInput.element,
      emailInput.element,
    );
  });

  addressInput.element.addEventListener('input', (e) => {
    addressInput.isValid = checkInput(e.target);
  });

  phoneInput.element.addEventListener('input', (e) => {
    phoneInput.isValid = checkInput(e.target);
  });

  emailInput.element.addEventListener('input', (e) => {
    emailInput.isValid = checkInput(e.target);
  });

  submit.addEventListener('click', () => {
    if (foundEnterprise) {
      initiateContact({ userId: userData.id, enterpriseId: foundEnterprise.entreprise_id });
      return;
    }

    if (
      !enterpriseInput.isValid ||
      !labelInput.isValid ||
      !addressInput.isValid ||
      !phoneInput.isValid ||
      !emailInput.isValid
    ) {
      checkInput(enterpriseInput.element);
      checkInput(labelInput.element);
      checkInput(addressInput.element);
      checkInput(phoneInput.element);
      checkInput(emailInput.element);
      return;
    }
    initiateContact({
      userId: userData.id,
      enterpriseName: enterpriseInput.element.value,
      enterpriseLabel: labelInput.element.value,
      enterpriseAddress: addressInput.element.value,
      enterprisePhone: phoneInput.element.value,
      enterpriseEmail: emailInput.element.value,
    });
  });
};

export default InitiatedRow;
