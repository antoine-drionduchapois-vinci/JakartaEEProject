import autocomplete from '../../services/autocomplete';
import checkInput from '../../services/checkInput';

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

let foundEnterprise;
const autoFillFields = (
  enterprises,
  enterpriseValue,
  labelElement,
  addressElement,
  contactElement,
) => {
  const labelE = labelElement;
  const addressE = addressElement;
  const contactE = contactElement;
  const foundEnterprises = enterprises.filter((e) => e.nom === enterpriseValue);
  if (foundEnterprises) {
    autocomplete(
      labelE,
      foundEnterprises.map((e) => e.appellation),
    );
    const labelValue = labelE.value;
    foundEnterprise = foundEnterprises.find((e) => e.appellation === labelValue);
    if (foundEnterprise) {
      addressE.value = foundEnterprise.adresse;
      addressE.setAttribute('disabled', true);
      contactE.value = foundEnterprise.telephone;
      contactE.setAttribute('disabled', true);
    } else {
      addressE.removeAttribute('disabled');
      contactE.removeAttribute('disabled');
    }
  }
};

const InitiatedRow = (htmlElement, userData, contactData, enterprisesData) => {
  const html = htmlElement;
  html.innerHTML = `
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
        <button class="button is-fullwidth" id="submit-initiated">Initier</button>
    </div>
`;

  const initiatedCircle = document.querySelector('#initiated-circle');
  const enterpriseInput = { element: document.querySelector('#enterprise'), isValid: false };
  const labelInput = { element: document.querySelector('#label'), isValid: false };
  const addressInput = { element: document.querySelector('#address'), isValid: false };
  const contactInput = { element: document.querySelector('#contact'), isValid: false };
  const submit = document.querySelector('#submit-initiated');

  if (contactData) {
    if (contactData.state !== 'refusé' || contactData.state !== 'non_suivis')
      initiatedCircle.removeAttribute('hidden');
    enterpriseInput.element.value = contactData.enterprise.name;
    enterpriseInput.element.setAttribute('disabled', true);
    labelInput.element.value = contactData.enterprise.label;
    labelInput.element.setAttribute('disabled', true);
    addressInput.element.value = contactData.enterprise.adress;
    addressInput.element.setAttribute('disabled', true);
    contactInput.element.value = contactData.enterprise.contact;
    contactInput.element.setAttribute('disabled', true);
    submit.setAttribute('disabled', true);
  }

  autocomplete(enterpriseInput.element, [...new Set(enterprisesData.map((e) => e.nom))]);

  enterpriseInput.element.addEventListener('input', (e) => {
    enterpriseInput.value = e.target.value;
    enterpriseInput.isValid = checkInput(e.target);
    autoFillFields(
      enterprisesData,
      e.target.value,
      labelInput.element,
      addressInput.element,
      contactInput.element,
    );
  });

  labelInput.element.addEventListener('input', (e) => {
    labelInput.value = e.target.value;
    labelInput.isValid = checkInput(e.target);
    autoFillFields(
      enterprisesData,
      enterpriseInput.element.value,
      labelInput.element,
      addressInput.element,
      contactInput.element,
    );
  });

  addressInput.element.addEventListener('input', (e) => {
    addressInput.value = e.target.value;
    addressInput.isValid = checkInput(e.target);
  });

  contactInput.element.addEventListener('input', (e) => {
    contactInput.value = e.target.value;
    contactInput.isValid = checkInput(e.target);
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
      !contactInput.isValid
    ) {
      checkInput(enterpriseInput.element);
      checkInput(labelInput.element);
      checkInput(addressInput.element);
      checkInput(contactInput.element);
      return;
    }
    initiateContact({
      userId: userData.id,
      enterpriseName: enterpriseInput.value,
      enterpriseLabel: labelInput.value,
      enterpriseAddress: addressInput.value,
      enterpriseContact: contactInput.value,
    });
  });
};

export default InitiatedRow;
