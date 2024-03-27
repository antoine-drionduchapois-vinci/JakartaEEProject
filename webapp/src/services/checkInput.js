export default function checkInput(element1, element2) {
  if (element2) {
    if (element1.value === '' && element2.value === '') {
      element1.classList.replace('is-primary', 'is-danger');
      element2.classList.replace('is-primary', 'is-danger');
      return false;
    }
    element1.classList.replace('is-danger', 'is-primary');
    element2.classList.replace('is-danger', 'is-primary');
  } else {
    if (element1.value === '') {
      element1.classList.replace('is-primary', 'is-danger');
      return false;
    }
    element1.classList.replace('is-danger', 'is-primary');
  }
  return true;
}
