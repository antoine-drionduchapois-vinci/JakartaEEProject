const clearPage = () => {
  const main = document.querySelector('main');
  main.innerHTML = '';
};

const renderPageTitle = (title) => {
  if (!title) return;
  const main = document.querySelector('main');
  const pageTitleContainer = document.createElement('div');
  pageTitleContainer.classList.add('d-flex', 'justify-content-center', 'align-items-center', 'h-100', 'mt-3');
  const pageTitle = document.createElement('h3');
  pageTitle.innerText = title;
  pageTitleContainer.appendChild(pageTitle);
  main.appendChild(pageTitleContainer);
};

export { clearPage, renderPageTitle };
