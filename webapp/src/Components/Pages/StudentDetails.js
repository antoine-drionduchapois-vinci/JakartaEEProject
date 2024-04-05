import { clearPage, renderPageTitle } from "../../utils/render";

const StudentDetails = () => {
    clearPage();
    renderPageTitle('Student Details');
    // Selecting main element from the DOM
   

    const queryString = window.location.search; 
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('id');

    const main = document.querySelector('main');
    const bloc1 = `Student details page, student id : ${id}`;
    main.innerHTML = bloc1;


}

export default StudentDetails;