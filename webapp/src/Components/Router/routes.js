import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import DashboardTeacher from '../Pages/DashboardTeacher';
import Contact from '../Pages/Contact';
import DashboardStudent from '../Pages/DashboardStudent';
import ProfilePage from '../Pages/ProfilePage';

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/logout': Logout,
  '/dashboardT': DashboardTeacher,
  '/contact': Contact,
  '/dashboardS': DashboardStudent,
  '/profile': ProfilePage
};

export default routes;
