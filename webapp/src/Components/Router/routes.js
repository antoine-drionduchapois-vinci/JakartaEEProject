import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import DashboardTeacher from '../Pages/DashboardTeacher';
import Contact from '../Pages/Contact';
import DashboardStudent from '../Pages/DashboardStudent';
import EnterpriseDetails from '../Pages/EnterpriseDetails';
import StudentDetails from '../Pages/StudentDetails';

import ProfilePage from '../Pages/ProfilePage';

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/logout': Logout,
  '/dashboardT': DashboardTeacher,
  '/contact': Contact,
  '/dashboardS': DashboardStudent,
  '/enterpriseDetails': EnterpriseDetails,
  '/studentDetails': StudentDetails,
  '/profile': ProfilePage
};

export default routes;
