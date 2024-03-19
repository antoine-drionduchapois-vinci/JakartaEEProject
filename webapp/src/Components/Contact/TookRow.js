import checkInput from '../../services/checkInput';

const meetEnterprise = async (data) =>
  fetch('http://localhost:8080/contact/meet', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  })
    .then((response) => response.status === 200)
    .catch((error) => console.error(error));

const TookRow = (htmlElement, userData, contactData) => {
  console.log('🚀 ~ TookRow ~ contactData:', contactData);
  const html = htmlElement;
  html.innerHTML = `
  <div class="column is-one-fifth bg-primary">
        <div class="d-flex gap-2 align-items-center">
            <div class="status-circle">
                <div id="took-circle" class="status-circle-item" hidden></div>
            </div>
            Pris
        </div>
    </div>
    <div class="column bg-secondary">
        <label for="meeting">Lieu de rencontre *</label>
        <input class="input is-primary" type="text" id="meeting">
    </div>
    <div class="column bg-secondary d-flex align-items-end">
        <button class="button is-fullwidth" id="submit-took">Prendre contact</button>
    </div>
  `;

  const tookCircle = document.querySelector('#took-circle');
  const meetingInput = { element: document.querySelector('#meeting'), isValid: false };
  const submit = document.querySelector('#submit-took');

  if (!contactData || contactData.state !== 'initié') {
    meetingInput.element.value = contactData?.description ? contactData.description : '';
    meetingInput.element.setAttribute('disabled', true);
    submit.setAttribute('disabled', true);
  }

  if (
    contactData &&
    contactData.state !== 'initié' &&
    contactData.state !== 'refusé' &&
    contactData.state !== 'non_suivis'
  )
    tookCircle.removeAttribute('hidden');

  meetingInput.element.addEventListener('input', (e) => {
    meetingInput.value = e.target.value;
    meetingInput.isValid = checkInput(e.target);
  });

  submit.addEventListener('click', () => {
    if (!meetingInput.isValid) {
      checkInput(meetingInput.element);
      return;
    }

    meetEnterprise({ meetingPoint: meetingInput.value });
  });
};

export default TookRow;
