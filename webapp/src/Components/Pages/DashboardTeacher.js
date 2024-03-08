import Chart from 'chart.js/auto';
import { clearPage, renderPageTitle } from '../../utils/render';


// Fonction pour récupérer les données des entreprises
const fetchEnterprises = async () => {
  const token = localStorage.getItem('token');
  const options = {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await fetch('http://localhost:8080/ent/enterprises', options);
    if (!response.ok) {
      throw new Error('Erreur lors de la récupération des données des entreprises');
    }

    // Accéder à la propriété "enterprises" de l'objet retourné
    // Convertir la réponse en JSON
    const jResponse = await response.json();
    return jResponse.enterprises;
  } catch (error) {
    console.error('Erreur lors de la récupération des données des entreprises : ', error);
    return null; // Ajout d'un retour de valeur pour résoudre l'erreur
  }
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
    return data;
  } catch (error) {
    console.error('Erreur lors de la récupération des données : ', error);
    return null;
  }
};

// Fonction pour rendre le graphique
const renderChart = (chartContainer, noStage, total) => {
  const canvas = document.createElement('canvas');
  canvas.width = 400;
  canvas.height = 400;
  chartContainer.appendChild(canvas);

  const chartData = {
    labels: ['Étudiants sans stage', 'Étudiants avec stage'],
    datasets: [
      {
        label: "Nombre d'étudiants",
        data: [noStage, total - noStage],
        backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)'],
        hoverOffset: 4,
      },
    ],
  };

  new Chart(canvas, {
    type: 'pie',
    data: chartData,
    options: {
      responsive: true,
    },
  });
};

// Fonction pour rendre le tableau des entreprises
const renderEnterpriseTable = (tableContainer, enterprises) => {
  const table = document.createElement('table');
  table.className = 'table is-fullwidth';
  tableContainer.appendChild(table);

  // Créer la première ligne pour les en-têtes de colonne
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');
  const headers = ['Nom', 'Appellation', 'Adresse', 'Téléphone', 'Blacklist', 'Avis Professeur']; // Liste des en-têtes

  // Ajouter chaque en-tête à la première ligne
  headers.forEach((headerText) => {
    const header = document.createElement('th');
    header.textContent = headerText;
    headerRow.appendChild(header);
  });

  thead.appendChild(headerRow);
  table.appendChild(thead);

  // Créer le corps du tableau
  const tbody = document.createElement('tbody');
  table.appendChild(tbody);

  enterprises.forEach((enterprise) => {
    const row = document.createElement('tr');
    row.addEventListener('click', () => {
      // Redirection vers la page de détails de l'entreprise avec l'ID comme paramètre de requête
      window.location.href = `details-page.html?id=${enterprise.entreprise_id}`;
    });
    const enterpriseValues = Object.values(enterprise).slice(1); // Exclure le premier élément (ID)
    // Ajouter chaque valeur de l'entreprise dans une cellule de la ligne
    enterpriseValues.forEach((value) => {
      console.log(value);
      const cell = document.createElement('td');
      cell.textContent = value;
      row.appendChild(cell);
    });

    tbody.appendChild(row);
  });
};

// Fonction pour rendre le tableau de bord de l'enseignant
const renderDashboardTeacher = async () => {
  // Nettoyage de la page
  clearPage();
  // Rendu du titre de la page en 'Dashboard Teacher'
  renderPageTitle('Dashboard Teacher');

  // Création d'un conteneur pour le graphique et le tableau
  const container = document.createElement('div');
  container.className = 'dashboard-container';
  document.body.appendChild(container);

  // Création d'un conteneur pour le graphique
  const chartContainer = document.createElement('div');
  chartContainer.className = 'chart-container';
  container.appendChild(chartContainer);

  // Création d'un conteneur pour le tableau
  const tableContainer = document.createElement('div');
  tableContainer.className = 'container';
  container.appendChild(tableContainer);

  // Appel de la fonction pour récupérer les données et rendre le graphique
  const data = await fetchDataAndRenderChart();
  if (data) {
    renderChart(chartContainer, data.noStage, data.total);
  }
  const enterprises = await fetchEnterprises();
  if (enterprises) {
    renderEnterpriseTable(tableContainer, enterprises);
  }
  
};

// Exportation de la fonction renderDashboardTeacher
export default renderDashboardTeacher;
