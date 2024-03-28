import checkInput from '../../services/checkInput';
import { getAuthenticatedUser } from '../../utils/auths';

const indicateAsRefused = (data) =>
  fetch('http://localhost:8080/contact/refuse', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getAuthenticatedUser().token,
    },
    body: JSON.stringify(data),
  })
    .then(() => window.location.reload())
    .catch((error) => console.error(error));

const RefusedRow = (htmlElement, contactData) => {
  const html = htmlElement;
  html.innerHTML = `
  <div class="column is-one-fifth">
        <div class="d-flex gap-2 align-items-center">
            <div class="status-circle">
                <div id="refused-circle" class="status-circle-item" hidden></div>
            </div>
            Refusé
        </div>
    </div>
    <div class="column">
        <label for="meeting">Raison de refus *</label>
        <input class="input is-primary" type="text" id="reason">
    </div>
    <div class="column d-flex align-items-end">
        <button class="button is-fullwidth" id="submit-refused">Indiquer comme refusé</button>
    </div>
  `;

  const refusedCircle = document.querySelector('#refused-circle');
  const reasonInput = { element: document.querySelector('#reason'), isValid: false };
  const submit = document.querySelector('#submit-refused');

  if (!contactData || (contactData.state !== 'initiated' && contactData.state !== 'meet')) {
    reasonInput.element.value = contactData?.refusal_reason ? contactData.refusal_reason : '';
    reasonInput.element.setAttribute('disabled', true);
    submit.setAttribute('disabled', true);
  }

  if (contactData && contactData.state === 'refused') refusedCircle.removeAttribute('hidden');

  reasonInput.element.addEventListener('input', (e) => {
    reasonInput.isValid = checkInput(e.target);
  });

  submit.addEventListener('click', () => {
    if (!reasonInput.isValid) {
      checkInput(reasonInput.element);
      return;
    }

    indicateAsRefused({
      contactId: contactData.contact_id,
      refusalReason: reasonInput.element.value,
    });
  });
};

export default RefusedRow;
