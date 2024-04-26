import Chart from 'chart.js/auto';
import { clearPage, renderPageTitle } from '../../utils/render';
import { getAuthenticatedUser } from '../../utils/auths';
import autocomplete from '../../services/autocomplete';
import translation from '../../utils/translation';
import Navigate from '../Router/Navigate';

// Fonction pour récupérer les données des entreprises
const fetchEnterprises = async () => {
  const user = getAuthenticatedUser();
  const options = {
    method: 'GET',
    headers: {
      Authorization: user.token,
    },
  };

  try {
    const response = await fetch('http://localhost:8080/ent/enterprises', options);
    if (!response.ok) {
      throw new Error('Erreur lors de la récupération des données des entreprises');
    }

    let data = await response.json();

    // Modifier chaque objet entreprise pour ne garder que les propriétés désirées
    data = data.map((enterprise) => ({
      id: enterprise.enterpriseId,
      nom: enterprise.name,
      appellation: enterprise.label || '', // Utiliser une chaîne vide si la valeur est null
      adresse: enterprise.address,
      téléphone: enterprise.phone,
      blacklist: enterprise.blacklisted,
      avisprofesseur: enterprise.blacklistedReason || '', // Utiliser une chaîne vide si la valeur est null
    }));

    return data;
  } catch (error) {
    console.error('Erreur lors de la récupération des données des entreprises : ', error);
    return null; // Ajout d'un retour de valeur pour résoudre l'erreur
  }
};

// Fonction pour récupérer les données et rendre le graphique
const fetchDataAndRenderChart = async () => {
  const user = getAuthenticatedUser();
  const options = {
    method: 'GET',
    headers: {
      Authorization: user.token,
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
  const user = getAuthenticatedUser();
  const options = {
    method: 'GET',
    headers: {
      Authorization: user.token,
    },
  };

  try {
    const response = await fetch('http://localhost:8080/users/All', options);
    if (!response.ok) {
      throw new Error('Erreur lors de la récupération des données des utilisateurs');
    }
    let translatedRole = '';
    let data = await response.json();
    data = data.map((u) => {
      translatedRole = translation[u.role] || u.role;
      return {
        id: u.userId,
        nom: u.name,
        prenom: u.surname,
        email: u.email,
        role: translatedRole,
        année: u.year,
      };
    });

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
        backgroundColor: ['rgb(221,255,0)', 'rgb(69, 176, 103)'],
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
      if (e.blacklist === undefined) {
        if (e.role === 'Etudiant') {
          
          Navigate(`/dashboardS?id=${e.id}`);
        }
      } else {
        Navigate(`/enterpriseDetails?id=${e.id}`);
      }
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

const renderForm = (formContainer, users, tableUserContainer) => {
  // Créer le formulaire
  const form = document.createElement('form');
  form.className = 'form';
  form.style.display = 'flex'; // Utiliser Flexbox
  form.style.flexWrap = 'wrap'; // Permettre le retour à la ligne si nécessaire
  form.style.paddingBottom = '10px'; // Ajouter le padding-bottom
  form.style.gap = '20px'; // Ajouter un espace entre les éléments

  // Créer le champ input avec Bulma
  const inputFieldDiv = document.createElement('div');
  inputFieldDiv.className = 'field';
  const inputLabel = document.createElement('label');
  inputLabel.className = 'label';
  inputLabel.textContent = 'Nom';
  const inputControlDiv = document.createElement('div');
  inputControlDiv.className = 'control';
  const inputField = document.createElement('input');
  inputField.className = 'input';
  inputField.type = 'text';
  inputField.placeholder = 'Entrez le nom';
  
  autocomplete(
    inputField,
    users.map((u) => u.nom),
  );
  inputControlDiv.appendChild(inputField);
  inputFieldDiv.appendChild(inputLabel);
  inputFieldDiv.appendChild(inputControlDiv);

  // Créer la checkbox avec Bulma
  const checkboxDiv = document.createElement('div');
  checkboxDiv.className = 'field';
  const checkboxControlDiv = document.createElement('div');
  checkboxControlDiv.className = 'control';
  const checkboxLabel = document.createElement('label');
  checkboxLabel.className = 'checkbox';
  const checkboxField = document.createElement('input');
  checkboxField.type = 'checkbox';
  checkboxField.id = 'isStudent'; // Modifier l'ID de la case à cocher
  checkboxLabel.appendChild(checkboxField);
  checkboxLabel.append(' Is Student'); // Modifier le texte de l'étiquette
  checkboxControlDiv.appendChild(checkboxLabel);
  checkboxDiv.appendChild(checkboxControlDiv);

  // Créer le menu déroulant (select) avec Bulma
  const selectDiv = document.createElement('div');
  selectDiv.className = 'field';
  const selectLabel = document.createElement('label');
  selectLabel.className = 'label';
  selectLabel.textContent = 'Option';
  const selectControlDiv = document.createElement('div');
  selectControlDiv.className = 'control';
  const selectField = document.createElement('select');
  selectField.className = 'input';
  // Récupérer l'année actuelle
  const currentYear = new Date().getFullYear() - 1;

  // Générer les options pour les années de l'année actuelle à 2000
  for (let year = currentYear; year >= 2015; year -= 1) {
    const option = document.createElement('option');
    const nextYear = year + 1;
    option.textContent = `${year}-${nextYear}`; // Format "2000-2001"
    option.value = `${year}-${nextYear}`;
    selectField.appendChild(option);
  }
  const clearOption = document.createElement('option');
  clearOption.textContent = 'Sélectionnez une année';
  clearOption.value = '';
  selectField.appendChild(clearOption);
  selectControlDiv.appendChild(selectField);
  selectDiv.appendChild(selectLabel);
  selectDiv.appendChild(selectControlDiv);

  // Ajouter les éléments au formulaire
  form.appendChild(inputFieldDiv);
  form.appendChild(checkboxDiv);
  form.appendChild(selectDiv);

  // Gestionnaire d'événement pour les champs d'entrée du formulaire
  [inputField, checkboxField, selectField].forEach((field) => {
    field.addEventListener('input', () => {
      // Récupérer les valeurs du formulaire
      const name = inputField.value.trim();
      const isStudent = checkboxField.checked;
      const selectedYear = selectField.value;
      
      // Filtrer les utilisateurs en fonction des critères
      const filteredUsers = users.filter((user) => {
        const userName = user.nom || '';
        const matchesName = !name || userName.toLowerCase().includes(name.toLowerCase());
        const matchesIsStudent = !isStudent || user.role === 'Etudiant';

        // Vérifier si selectedYear est null ou vide
        if (!selectedYear) {
          return matchesName && matchesIsStudent;
        }

        const userYearParts = user.année.split('-');
        const selectedYearParts = selectedYear.split('-');

        // Vérifier si les parties des années correspondent
        const matchesYear =
          !selectedYear ||
          (userYearParts[0] === selectedYearParts[0] && userYearParts[1] === selectedYearParts[1]);
        return matchesName && matchesIsStudent && matchesYear;
      });

      const tbody = tableUserContainer.querySelector('.table-scroll-container table tbody');

      updateTable(tbody, filteredUsers);
    });
  });

  // Ajouter le formulaire au conteneur fourni
  formContainer.appendChild(form);
};

// Fonction pour rendre le tableau des entreprises avec recherche et tri
const renderEnterpriseTable = (tableContainer, enterprises) => {
  // Conteneur pour le tableau avec défilement
  const scrollContainer = document.createElement('div');
  scrollContainer.className = 'table-scroll-container';
  // Créer le tableau
  const table = document.createElement('table');
  table.className = 'table is-fullwidth';
  table.style.maxHeight = '250px'; // Définir la hauteur maximale
  table.style.overflowY = 'auto';

  // Créer le corps du tableau
  const tbody = document.createElement('tbody');
  // Fonction pour trier les colonnes
  const sortColumn = (columnName) => {
    const lowerColumnName = columnName
      .trim()
      .toLowerCase()
      .replace(/\s/g, '');

    enterprises.sort((a, b) => {
      const valueA = a[lowerColumnName];
      const valueB = b[lowerColumnName];

      // Si les valeurs sont des booléens, trier true avant false
      if (typeof valueA === 'boolean' && typeof valueB === 'boolean') {
        if (valueA === valueB) {
          return 0;
        }
        return valueA ? -1 : 1;
      }

      // Comparaison des valeurs des colonnes
      const stringValueA = typeof valueA === 'string' ? valueA.toLowerCase() : valueA;
      const stringValueB = typeof valueB === 'string' ? valueB.toLowerCase() : valueB;

      // Utilisation de localeCompare pour le tri alphabétique
      return stringValueA.localeCompare(stringValueB);
    });

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
  // Ajouter le corps du tableau au tableau
  table.appendChild(tbody);
  scrollContainer.appendChild(table);
  tableContainer.appendChild(scrollContainer);

  // Afficher le tableau avec toutes les entreprises au chargement initial
  updateTable(tbody, enterprises);
};

const renderUserTable = (tableUserContainer, users) => {
  // Conteneur pour le formulaire
  
  const formContainer = document.createElement('div');
  tableUserContainer.appendChild(formContainer);

  // Appel de la fonction pour créer le formulaire
  renderForm(formContainer, users, tableUserContainer);

  // Conteneur pour le tableau avec défilement
  const scrollContainer = document.createElement('div');
  scrollContainer.className = 'table-scroll-container';

  // Créer le tableau des utilisateurs
  const table = document.createElement('table');
  table.className = 'table is-fullwidth';

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

  // Ajouter le tableau au conteneur de défilement
  scrollContainer.appendChild(table);

  // Ajouter le conteneur de défilement au conteneur principal
  tableUserContainer.appendChild(scrollContainer);
  const filteredUsers = users.filter((user) => {
    const userYearParts = user.année.split('-');

    // Vérifier si les parties des années correspondent
    const matchesYear = userYearParts[0] === '2023' && userYearParts[1] === '2024';
    return matchesYear;
  });
  // Afficher les utilisateurs dans le tableau
  updateTable(tbody, filteredUsers);
};

// Fonction pour rendre le tableau de bord de l'enseignant
const renderDashboardTeacher = async () => {
  // Nettoyage de la page
  clearPage();
  // Rendu du titre de la page en 'Dashboard Teacher'
  renderPageTitle('Tableau de bord');

  // Création d'un conteneur pour le graphique et le tableau
  const main = document.querySelector('main');
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
