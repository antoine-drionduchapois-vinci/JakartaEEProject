import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import Dashboard from '../Pages/Dashboard';
import DashboardTeacher from '../Pages/DashboardTeacher';
import Contact from '../Pages/Contact';
import DashboardStudent from '../Pages/DashboardStudent';

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/logout': Logout,
  '/dashboard': Dashboard,
  '/dashboardT': DashboardTeacher,
  '/contact': Contact,
  '/dashboardS': DashboardStudent
};

export default routes;
