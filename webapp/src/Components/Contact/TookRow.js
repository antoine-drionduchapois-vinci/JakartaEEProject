import checkInput from '../../services/checkInput';
import { getAuthenticatedUser } from '../../utils/auths';

const meetEnterprise = (data) =>
  fetch('http://localhost:8080/contact/meet', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getAuthenticatedUser().token,
    },
    body: JSON.stringify(data),
  })
    .then(() => window.location.reload())
    .catch((error) => console.error(error));

const TookRow = (htmlElement, userData, contactData) => {
  const html = htmlElement;
  html.innerHTML = `
  <div class="column is-one-fifth">
        <div class="d-flex gap-2 align-items-center">
            <div class="status-circle">
                <div id="took-circle" class="status-circle-item" hidden></div>
            </div>
            Pris
        </div>
    </div>
    <div class="column">
        <label for="meeting">Lieu de rencontre *</label>
        <input class="input is-primary" type="text" id="meeting">
    </div>
    <div class="column d-flex align-items-end">
        <button class="button is-fullwidth" id="submit-took">Prendre contact</button>
    </div>
  `;

  const tookCircle = document.querySelector('#took-circle');
  const meetingInput = { element: document.querySelector('#meeting'), isValid: false };
  const submit = document.querySelector('#submit-took');

  if (!contactData || contactData.state !== 'initiated') {
    meetingInput.element.value = contactData?.meeting_point ? contactData.meeting_point : '';
    meetingInput.element.setAttribute('disabled', true);
    submit.setAttribute('disabled', true);
  }

  if (contactData && contactData.state !== 'initiated' && contactData.meeting_point)
    tookCircle.removeAttribute('hidden');

  meetingInput.element.addEventListener('input', (e) => {
    meetingInput.isValid = checkInput(e.target);
  });

  submit.addEventListener('click', () => {
    if (!meetingInput.isValid) {
      checkInput(meetingInput.element);
      return;
    }

    meetEnterprise({ contactId: contactData.contact_id, meetingPoint: meetingInput.element.value });
  });
};

export default TookRow;
