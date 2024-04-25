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

  if (contactData && contactData.state === 'suspended') suspendedCircle.removeAttribute('hidden');
};

export default SuspendedRow;
