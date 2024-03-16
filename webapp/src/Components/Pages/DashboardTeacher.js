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
    const response = await fetch('http://localhost:8080/users/stats', options);
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

const fetchUsers = async () => {
  const token = localStorage.getItem('token');
  const options = {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await fetch('http://localhost:8080/users/All', options);
    if (!response.ok) {
      throw new Error('Erreur lors de la récupération des données des utilisateurs');
    }

    const data = await response.json();
    return data; // Retourner directement le tableau d'utilisateurs de la réponse JSON
  } catch (error) {
    console.error('Erreur lors de la récupération des données des utilisateurs : ', error);
    return null;
  }
};

// Fonction pour rendre le graphique
const renderChart = (chartContainer, noStage, total) => {
  const canvas = document.createElement('canvas');

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

// Fonction pour mettre à jour le tableau avec les entreprises fournies
const updateTable = (tableBody, list) => {
  const tbody = tableBody; // Nouvelle variable pour stocker la référence à tableBody
  tbody.innerHTML = ''; // Effacer le contenu actuel du tableau
  list.forEach((e) => {
    const row = document.createElement('tr');
    row.addEventListener('click', () => {
      window.location.href = `details-page.html?id=${e.entreprise_id !== undefined ? e.entreprise_id : e.utilisateur_id}`;
    });
    const values = Object.values(e).slice(1);
    values.forEach((value) => {
      const cell = document.createElement('td');
      cell.textContent = value;
      row.appendChild(cell);
    });
    tableBody.appendChild(row);
  });
};

// Fonction pour rendre le tableau des entreprises avec recherche et tri
const renderEnterpriseTable = (tableContainer, enterprises) => {
  // Créer le tableau
  const table = document.createElement('table');
  table.className = 'table is-fullwidth';
  tableContainer.appendChild(table);
  // Créer le corps du tableau
  const tbody = document.createElement('tbody');
  // Fonction pour trier les colonnes
  const sortColumn = (columnName) => {
    const lowerColumnName = columnName.toLowerCase();
    enterprises.sort((a, b) => {
      // Comparaison des valeurs des colonnes
      const valueA = a[lowerColumnName].toLowerCase();
      const valueB = b[lowerColumnName].toLowerCase();
  
      // Utilisation de localeCompare pour le tri alphabétique
      return valueA.localeCompare(valueB);
    });
    console.log("update T");
    // Mettre à jour le tableau avec les entreprises triées
    updateTable(tbody, enterprises);
  };

  // Créer la première ligne pour les en-têtes de colonne
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');
  const headers = ['Nom', 'Appellation', 'Adresse', 'Téléphone', 'Blacklist', 'Avis Professeur'];

  // Ajouter chaque en-tête à la première ligne avec possibilité de tri
  headers.forEach((headerText) => {
    const header = document.createElement('th');
    header.textContent = headerText;
    header.addEventListener('click', () => {
      sortColumn(headerText); // Fonction pour trier les colonnes
    });
    headerRow.appendChild(header);
  });

  // Ajouter la ligne d'en-tête au tableau
  thead.appendChild(headerRow);
  table.appendChild(thead);
  // ajouter le corp au tableau
  table.appendChild(tbody);

  // Afficher le tableau avec toutes les entreprises au chargement initial
  updateTable(tbody, enterprises);
};

const renderUserTable = (tableUserContainer, users) => {
  const table = document.createElement('table');
  table.className = 'table is-fullwidth';
  tableUserContainer.appendChild(table);

  // Créer la première ligne pour les en-têtes de colonne
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');
  const headers = ['Nom', 'Prénom', 'Email', 'Rôle', 'Année']; // Liste des en-têtes

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

  updateTable(tbody,users);
};

// Fonction pour rendre le tableau de bord de l'enseignant
const renderDashboardTeacher = async () => {
  // Nettoyage de la page
  clearPage();
  // Rendu du titre de la page en 'Dashboard Teacher'
  renderPageTitle('Dashboard Teacher');

  // Création d'un conteneur pour le graphique et le tableau
  const main =document.querySelector('main');
  const container = document.createElement('div');
  container.className = 'dashboard-container';
  main.appendChild(container);

  // Création d'un conteneur pour le graphique
  const chartContainer = document.createElement('div');
  chartContainer.className = 'chart-container';
  container.appendChild(chartContainer);

  // Création d'un conteneur pour le tableau
  const tableContainer = document.createElement('div');
  tableContainer.className = 'table-container';
  container.appendChild(tableContainer);

  // Création d'un conteneur pour le tableau d'users
  const tableUserContainer = document.createElement('div');
  tableContainer.className = 'tableUser-container';
  tableContainer.appendChild(tableUserContainer);

  // Appel de la fonction pour récupérer les données et rendre le graphique
  const data = await fetchDataAndRenderChart();
  const enterprises = await fetchEnterprises();
  const users = await fetchUsers();
  if (data) {
    renderChart(chartContainer, data.noStage, data.all);
  }
  
  if (enterprises) {
    renderEnterpriseTable(tableContainer, enterprises);
  }
  
  if (users) {
    renderUserTable(tableUserContainer, users);
  }
};

// Exportation de la fonction renderDashboardTeacher
export default renderDashboardTeacher;
