// Import des rendus utilitaires
import Chart from 'chart.js/auto';
import { clearPage, renderPageTitle } from '../../utils/render';

// Fonction pour rendre le graphique
const renderChart = (div, studentsWithoutInternship, totalStudents) => {
  const canvas = document.createElement('canvas');
  canvas.width = 400;
  canvas.height = 400;
  div.appendChild(canvas);
  

  // Rendu du graphique camembert
  const chartData = {
    labels: ['Étudiants sans stage', 'Étudiants avec stage'],
    datasets: [
      {
        label: "Nombre d'étudiants",
        data: [studentsWithoutInternship, totalStudents - studentsWithoutInternship],
        backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)'],
        hoverOffset: 4,
      },
    ],
  };
 

  // Création d'une instance de Chart en utilisant le constructeur
  new Chart(canvas, {
    type: 'pie',
    data: chartData,
    options: {
      responsive: true,
    },
  });
  
};

// Fonction pour récupérer les données et rendre le graphique
const fetchDataAndRenderChart = async () => {
  const token = localStorage.getItem('token');
  const options = {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
  try {
    const response = await fetch('http://localhost:8080/auths/stats', options);
    if (!response.ok) {
      throw new Error('Erreur lors de la récupération des données');
    }
    const data = await response.json();

    // Appel à renderChart pour afficher le graphique
    const div2 = document.createElement('div');
    document.body.appendChild(div2);
    renderChart(div2, data.noStage, data.total);
  } catch (error) {
    console.error('Erreur lors de la récupération des données : ', error);
  }
};

// Fonction pour rendre le tableau de bord de l'enseignant
const DashboardTeacher = async () => {
  // Nettoyage de la page
  clearPage();
  // Rendu du titre de la page en 'Dashboard Teacher'
  renderPageTitle('Dashboard Teacher');

  // Appel de la fonction pour récupérer les données et rendre le graphique
  await fetchDataAndRenderChart();
};

// Exportation de la fonction DashboardTeacher
export default DashboardTeacher;
