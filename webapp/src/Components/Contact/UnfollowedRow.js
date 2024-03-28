import { getAuthenticatedUser } from '../../utils/auths';

const unfollow = async (data) =>
  fetch('http://localhost:8080/contact/unfollow', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getAuthenticatedUser().token,
    },
    body: JSON.stringify(data),
  })
    .then(() => window.location.reload())
    .catch((error) => console.error(error));

const UnfollowedRow = (htmlElement, contactData) => {
  const html = htmlElement;
  html.innerHTML = `
  <div class="column is-one-fifth">
        <div class="d-flex gap-2 align-items-center">
            <div class="status-circle">
                <div id="unfollowed-circle" class="status-circle-item" hidden></div>
            </div>
            Non Suivis
        </div>
    </div>
    <div class="column d-flex align-items-end">
        <button class="button is-fullwidth" id="submit-unfollowed">Ne plus suivre</button>
    </div>
  `;

  const unefollowedCircle = document.querySelector('#unfollowed-circle');
  const submit = document.querySelector('#submit-unfollowed');

  if (!contactData || (contactData.state !== 'initiated' && contactData.state !== 'meet')) {
    submit.setAttribute('disabled', true);
  }

  if (contactData && contactData.state === 'unfollowed')
    unefollowedCircle.removeAttribute('hidden');

  submit.addEventListener('click', () => {
    unfollow({ contactId: contactData.contact_id });
  });
};

export default UnfollowedRow;
