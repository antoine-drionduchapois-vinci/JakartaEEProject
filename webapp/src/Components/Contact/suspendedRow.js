import { getAuthenticatedUser } from '../../utils/auths';

const indicateAsSuspended = (data) =>
fetch('http://localhost:8080/contact/suspend', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getAuthenticatedUser().token,
    },
    body: JSON.stringify(data),
  })
    .then(() => window.location.reload())
    .catch((error) => console.error(error));




const SuspendedRow = (htmlElement, contactData) => {
    const html = htmlElement;
    html.innerHTML = `
    <div class="column is-one-fifth">
        <div class="d-flex gap-2 align-items-center">
            <div class="status-circle">
                <div id="suspended-circle" class="status-circle-item" hidden></div>
            </div>
            Suspendu
        </div>
    </div>
`;

    const suspendedCircle = document.querySelector('#suspended-circle'); 
    const submit = document.querySelector('#submit-suspended');


  
    if (!contactData || (contactData.state !== 'initiated' && contactData.state !== 'meet')) {
      submit.setAttribute('disabled', true);
    }
  
    if (contactData && contactData.state === 'suspended')
      suspendedCircle.removeAttribute('hidden');
  
    submit.addEventListener('click', () => {
        indicateAsSuspended({ contactId: contactData.contact_id });
    });
};

export default SuspendedRow;