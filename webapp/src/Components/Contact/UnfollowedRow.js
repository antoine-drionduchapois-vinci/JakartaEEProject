const unfollow = async (data) =>
  fetch('http://localhost:8080/contact/unfollow', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  })
    .then((response) => response.status === 200)
    .catch((error) => console.error(error));

const UnfollowedRow = (htmlElement, contactData) => {
  const html = htmlElement;
  html.innerHTML = `
  <div class="column is-one-fifth bg-primary">
        <div class="d-flex gap-2 align-items-center">
            <div class="status-circle">
                <div id="unfollowed-circle" class="status-circle-item" hidden></div>
            </div>
            Non Suivis
        </div>
    </div>
    <div class="column bg-secondary d-flex align-items-end">
        <button class="button is-fullwidth" id="submit-unfollowed">Ne plus suivre</button>
    </div>
  `;

  const unefollowedCircle = document.querySelector('#unfollowed-circle');
  const submit = document.querySelector('#submit-unfollowed');

  if (!contactData || (contactData.state !== 'initié' && contactData.state !== 'pris')) {
    submit.setAttribute('disabled', true);
  }

  if (contactData && contactData.state === 'non_suivis')
    unefollowedCircle.removeAttribute('hidden');

  submit.addEventListener('click', () => {
    unfollow({ contactId: contactData.contactId });
  });
};

export default UnfollowedRow;