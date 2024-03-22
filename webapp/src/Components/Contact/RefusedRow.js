import checkInput from '../../services/checkInput';

const indicateAsRefused = async (data) =>
  fetch('http://localhost:8080/contact/refuse', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  })
    .then((response) => response.status === 200)
    .catch((error) => console.error(error));

const RefusedRow = (htmlElement, contactData) => {
  const html = htmlElement;
  html.innerHTML = `
  <div class="column is-one-fifth bg-primary">
        <div class="d-flex gap-2 align-items-center">
            <div class="status-circle">
                <div id="refused-circle" class="status-circle-item" hidden></div>
            </div>
            Refusé
        </div>
    </div>
    <div class="column bg-secondary">
        <label for="meeting">Raison de refus *</label>
        <input class="input is-primary" type="text" id="reason">
    </div>
    <div class="column bg-secondary d-flex align-items-end">
        <button class="button is-fullwidth" id="submit-refused">Indiquer comme refusé</button>
    </div>
  `;

  const refusedCircle = document.querySelector('#refused-circle');
  const reasonInput = { element: document.querySelector('#reason'), isValid: false };
  const submit = document.querySelector('#submit-refused');

  if (!contactData || (contactData.state !== 'initié' && contactData.state !== 'pris')) {
    reasonInput.element.value = contactData?.reasonRefusal ? contactData.reasonRefusal : '';
    reasonInput.element.setAttribute('disabled', true);
    submit.setAttribute('disabled', true);
  }

  if (contactData && contactData.state === 'refusé') refusedCircle.removeAttribute('hidden');

  reasonInput.element.addEventListener('input', (e) => {
    reasonInput.value = e.target.value;
    reasonInput.isValid = checkInput(e.target);
  });

  submit.addEventListener('click', () => {
    if (!reasonInput.isValid) {
      checkInput(reasonInput.element);
      return;
    }

    indicateAsRefused({ contactId: contactData.contactId, reason: reasonInput.value });
  });
};

export default RefusedRow;
