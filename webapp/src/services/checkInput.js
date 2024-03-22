export default function checkInput(element) {
  if (element.value === '') {
    element.classList.replace('is-primary', 'is-danger');
    return false;
  }
  element.classList.replace('is-danger', 'is-primary');
  return true;
}
