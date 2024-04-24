import checkInput from '../../services/checkInput';
import { getAuthenticatedUser } from '../../utils/auths';

const acceptInternship = (data) =>
  fetch('http://localhost:8080/int/accept', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getAuthenticatedUser().token,
    },
    body: JSON.stringify(data),
  })
    .then((res) => (res.status === 200 ? res.json() : null))
    .then((d) => {
      if (d) window.location.href = `http://localhost:3000/contact?id=${d.contact}`;
    })
    .catch((error) => console.error(error));

const modifySubject = (data) =>
  fetch('http://localhost:8080/int/subject', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getAuthenticatedUser().token,
    },
    body: JSON.stringify(data),
  })
    .then((res) => (res.status === 200 ? res.json() : null))
    .then((d) => {
      if (d) window.location.href = `http://localhost:3000/contact?id=${d.contact}`;
    })
    .catch((error) => console.error(error));

const AcceptedRow = async (htmlElement, contactData, internshipData) => {
  const html = htmlElement;
  html.innerHTML = `
    <div class="column is-one-fifth">
        <div class="d-flex gap-2 align-items-center">
            <div class="status-circle">
                <div id="accepted-circle" class="status-circle-item" hidden></div>
            </div>
            Accepté
        </div>
    </div>
    <div class="column">
        <label for="supervisor-name">Nom</label>
        <div class="autocomplete">
            <input autocomplete="off" class="input is-primary" type="text" id="supervisor-name">
        </div>
    </div>
    <div class="column">
        <label for="supervisor-surname">Prénom</label>
        <div class="autocomplete">
          <input autocomplete="off" class="input is-primary" type="text" id="supervisor-surname">
        </div>
    </div>
    <div class="column">
        <label for="supervisor-phone">Téléphone</label>
        <input class="input is-primary" type="text" id="supervisor-phone">
    </div>
    <div class="column">
        <label for="supervisor-email">Email</label>
        <input class="input is-primary" type="text" id="supervisor-email">
    </div>
    <div class="column">
        <label for="subject">Sujet</label>
        <input class="input is-primary" type="text" id="subject">
    </div>
    <div class="column d-flex align-items-end">
        <button class="button is-fullwidth" id="submit-accepted">Créer un stage</button>
    </div>
`;

  let supervisor = null;
  if (contactData) {
    supervisor = await fetch(`http://localhost:8080/res?enterprise=${contactData.enterprise}`)
      .then((res) => (res.status === 200 ? res.json() : null))
      .catch((error) => console.log(error));
  }

  const acceptedCircle = document.querySelector('#accepted-circle');
  const nameInput = { element: document.querySelector('#supervisor-name'), isValid: false };
  const surnameInput = { element: document.querySelector('#supervisor-surname'), isValid: false };
  const phoneInput = { element: document.querySelector('#supervisor-phone'), isValid: false };
  const emailInput = { element: document.querySelector('#supervisor-email'), isValid: false };
  const subjectInput = { element: document.querySelector('#subject'), isValid: false };
  const submit = document.querySelector('#submit-accepted');

  if (contactData) {
    if (contactData.state === 'refused' || contactData.state === 'unfollowed') {
      nameInput.element.setAttribute('disabled', true);
      surnameInput.element.setAttribute('disabled', true);
      phoneInput.element.setAttribute('disabled', true);
      emailInput.element.setAttribute('disabled', true);
      subjectInput.element.setAttribute('disabled', true);
      submit.setAttribute('disabled', true);
    }
    if (internshipData && internshipData.contact === contactData.contactId) {
      acceptedCircle.removeAttribute('hidden');
      nameInput.element.value = internshipData.supervisorDTO.name;
      nameInput.element.setAttribute('disabled', true);
      surnameInput.element.value = internshipData.supervisorDTO.surname;
      surnameInput.element.setAttribute('disabled', true);
      phoneInput.element.value = internshipData.supervisorDTO.phone;
      phoneInput.element.setAttribute('disabled', true);
      emailInput.element.value = internshipData.supervisorDTO.email;
      emailInput.element.setAttribute('disabled', true);
      subjectInput.element.value = internshipData.subject;
      if (internshipData.subject) submit.textContent = 'Modifier le sujet';
      else submit.textContent = 'Ajouter le sujet';
    } else if (supervisor) {
      nameInput.element.value = supervisor.name;
      nameInput.element.setAttribute('disabled', true);
      nameInput.isValid = true;
      surnameInput.element.value = supervisor.surname;
      surnameInput.element.setAttribute('disabled', true);
      surnameInput.isValid = true;
      phoneInput.element.value = supervisor.phone;
      phoneInput.element.setAttribute('disabled', true);
      phoneInput.isValid = true;
      emailInput.element.value = supervisor.email;
      emailInput.element.setAttribute('disabled', true);
      emailInput.isValid = true;
    }
  }

  nameInput.element.addEventListener('input', (e) => {
    nameInput.isValid = checkInput(e.target);
  });

  surnameInput.element.addEventListener('input', (e) => {
    surnameInput.isValid = checkInput(e.target);
  });

  phoneInput.element.addEventListener('input', (e) => {
    phoneInput.isValid = checkInput(e.target);
  });

  subjectInput.element.addEventListener('input', (e) => {
    subjectInput.isValid = checkInput(e.target);
  });

  submit.addEventListener('click', () => {
    if (internshipData && internshipData.contactId === contactData.contactId) {
      modifySubject({ subject: subjectInput.element.value });
      return;
    }
    if (supervisor) {
      acceptInternship({
        contact: contactData.contactId,
        supervisor: supervisor.supervisorId,
        subject: subjectInput.element.value,
      });
      return;
    }

    if (!nameInput.isValid || !surnameInput.isValid || !phoneInput.isValid) {
      checkInput(nameInput.element);
      checkInput(surnameInput.element);
      checkInput(phoneInput.element);
      return;
    }

    acceptInternship({
      contact: contactData.contactId,
      supervisorDTO: {
        name: nameInput.element.value,
        surname: nameInput.element.value,
        email: emailInput.element.value,
        phone: phoneInput.element.value,
      },
      subject: subjectInput.element.value,
    });
  });
};

export default AcceptedRow;
