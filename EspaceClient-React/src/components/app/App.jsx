import React, { useState } from 'react';
import '../../css/dx.EGlobal.css';
import '../../css/eglobal.css';
import { useTranslation } from "react-i18next";
import './App.css';
import '../../css/dark-mode.css';
import { ThemeProvider, useTheme } from '../../context/ThemeContext.jsx';
import { Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import Login from '../login/login';
import Home from '../home/home';
import Invoices from '../invoices/invoices';
import Register from "../register/register";
import Orders from "../orders/orders";
import Historic from '../historic/historic';
import Subscriptions from "../subscription/subscriptions";
import Offers from '../offers/offers';
import Admin from '../admin/admin';
import ProfilUser from '../profilUser/profilUser';
import PermissionsUser from '../permissionsUser/permissionsUser';
import Infocentre from '../infocentre/infocentre';
import Settings from '../settings/settings';
import ResetPassword from '../../components/forgotPassword/resetPassword';
import ForgotPassword from '../../components/forgotPassword/forgotPassword';
import CustomerSelection from '../../components/customerSelection/customerSelection';
import ErpLogin from '../../components/erpLogin/erpLogin';
import CompanySelection from '../companySelection/companySelection';



const ProtectedRoute = ({ children }) => {
    const token = localStorage.getItem('token');

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    return children;
};

const AdminRoute = ({ children }) => {
    const token = localStorage.getItem('token');
    const isAdmin = localStorage.getItem('profile') === 'ADMINISTRATEUR';

    if(!token){
        return <Navigate to="/login" replace />;
    }

    if(!isAdmin){
        return <Navigate to="/home" replace />;
    }

    return children;
}

function AppContent() {

    const { i18n } = useTranslation();
    const navigate = useNavigate();
    const { resetTheme } = useTheme();
    const [user, setUser] = useState(localStorage.getItem('username') || '');
    const [selectedUser, setSelectedUser] = useState(null);
    const [searchFilter, setSearchFilter] = useState(null);

    const fetchManagedCustomers = async(userId, company) => {
        const token = localStorage.getItem('token');
        try{
            const response = await fetch (
                `${process.env.REACT_APP_API_URL}/wm-customers?userId=${userId}&company=${company}`,
                { headers: { 'Authorization': `Bearer ${token}` } }
            );
            if(response.ok){
                return await response.json();
            }
        } catch (error) {
            console.error('Erreur réseau : ', error);
        }
        return [];
    }

    const handleLogin = async (name) => {

        localStorage.removeItem('selectedCompany');
        localStorage.removeItem('selectedCompanyName');
        localStorage.removeItem('selectedNcs');
        localStorage.removeItem('selectedCustomerName');
        localStorage.removeItem('eGlobalUserId');
        localStorage.removeItem('customers');

        setUser(name);
        localStorage.setItem('username', name);

        const companies = JSON.parse(localStorage.getItem('companies') || '[]');
        if (companies.length > 1) {
            navigate('/company-selection');
            return;
        }
        if (companies.length === 1){
            localStorage.setItem('selectedCompany', companies[0].usercmp);
            localStorage.setItem('selectedCompanyName', companies[0].companyName);
            localStorage.setItem('eGlobalUserId', companies[0].eGlobalUserId);

            // Cas : 1 seule société 1 client
            const customers = await fetchManagedCustomers(name, companies[0].usercmp);
            localStorage.setItem('customers', JSON.stringify(customers)); // Pour avoir le nombre de clients
            if(customers.length === 1){
                localStorage.setItem('selectedNcs', customers[0].ncs);
                localStorage.setItem('selectedCustomerName', customers[0].customerName);
                navigate('/home');
                return;
            }

            // Cas : une seule société pls clients
            navigate('/customer-selection');
            return;
        }

        // Cas : aucune société (fallback)
        navigate('/customer-selection');
    }

    const handleLogout = () => {
        localStorage.removeItem('selectedNcs');
        localStorage.removeItem('selectedCustomerName');
        localStorage.removeItem('selectedCompany');
        localStorage.removeItem('selectedCompanyName');
        localStorage.removeItem('companies');
        localStorage.clear();
        resetTheme();
        i18n.changeLanguage('fr'); // Choisi la langue de base
        navigate('/login');
    }

    return (
        <div className="App dx-viewport">
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />

                <Route path="/login" element={
                    <Login onLoginSuccess={handleLogin}
                           onGoToRegister={() => navigate('/register')}
                           onGoToForgotPassword={() => navigate('/forgot-password')}
                    />}
                />

                <Route path="/erp-login" element={<ErpLogin />} />

                <Route path="/customer-selection" element={
                    <ProtectedRoute>
                        <CustomerSelection
                            onLogout={handleLogout}
                            onGoToHome={() => navigate('/home')}
                            onGoToInvoices={() => navigate('/invoices')}
                            onGoToOrders={() => navigate('/orders')}
                            onGoToOffers={() => navigate('/offers')}
                            onGoToSubscription={() => navigate('/subscriptions')}
                            onGoToHistoric={() => navigate('/historic')}
                            onGoToAdmin={() => navigate('/admin')}
                            onGoToDocument={() => navigate('/document')}
                            onGoToSettings={() => navigate('/settings')}
                            onGoToCompanySelection={() => navigate('/company-selection')}
                        />
                    </ProtectedRoute>
                }/>

                <Route path="/company-selection" element={
                    <ProtectedRoute>
                        <CompanySelection
                            onLogout={handleLogout}
                            onGoToHome={() => navigate('/home')}
                            onGoToCustomerSelection={() => navigate('/customer-selection')}
                            onGoToInvoices={() => navigate('/invoices')}
                            onGoToOrders={() => navigate('/orders')}
                            onGoToOffers={() => navigate('/offers')}
                            onGoToSubscription={() => navigate('/subscriptions')}
                            onGoToHistoric={() => navigate('/historic')}
                            onGoToAdmin={() => navigate('/admin')}
                            onGoToDocument={() => navigate('/document')}
                            onGoToSettings={() => navigate('/settings')}
                        />
                    </ProtectedRoute>
                }/>

                <Route path="/register" element={
                    <Register onBack={() => navigate('/login')}
                    />}
                />

                <Route path="/forgot-password" element={
                    <ForgotPassword onBackToLogin={() => navigate('/login')} />
                } />

                <Route path="/reset-password" element={
                    <ResetPassword onBackToLogin={() => navigate('/login')} />
                } />

                <Route path="/home" element={
                    <ProtectedRoute>
                        <Home username={user} onLogout={handleLogout}
                              onSearchNavigate={(type, field, value) => {
                                    setSearchFilter({ field, value});
                                    navigate(`/${type}`);
                              }}
                              onGoToCustomerSelection={() => navigate('/customer-selection')}
                              onGoToCompanySelection={() => navigate('/company-selection')}
                              onGoToInvoices={() => navigate('/invoices')}
                              onGoToSubscription={() => navigate('/subscriptions')}
                              onGoToHistoric={() => navigate('/historic')}
                              onGoToOrders={() => navigate('/orders')}
                              onGoToOffers={() => navigate('/offers')}
                              onGoToAdmin={() => navigate('/admin')}
                              onGoToDocument={() => navigate('/document')}
                              onGoToSettings={() => navigate('/settings')}
                        />
                    </ProtectedRoute>
                } />

                <Route path="/subscriptions" element={
                    <ProtectedRoute>
                        <Subscriptions username={user}
                                       onLogout={handleLogout}
                                       searchFilter={searchFilter}
                                       clearSearchFilter={() => setSearchFilter(null)}
                                       onGoToCustomerSelection={() => navigate('/customer-selection')}
                                       onGoToCompanySelection={() => navigate('/company-selection')}
                                       onGoToInvoices={() => navigate('/invoices')}
                                       onGoToHistoric={() => navigate('/historic')}
                                       onGoToOrders={() => navigate('/orders')}
                                       onGoToOffers={() => navigate('/offers')}
                                       onGoToAdmin={() => navigate('/admin')}
                                       onGoToDocument={() => navigate('/document')}
                                       onGoToHome={() => navigate('/home')}
                                       onGoToSettings={() => navigate('/settings')}
                        />
                    </ProtectedRoute>
                } />

                <Route path="/invoices" element={
                    <ProtectedRoute>
                        <Invoices onLogout={handleLogout}
                                  searchFilter={searchFilter}
                                  clearSearchFilter={() => setSearchFilter(null)}
                                  onGoToCustomerSelection={() => navigate('/customer-selection')}
                                  onGoToCompanySelection={() => navigate('/company-selection')}
                                  onGoToOrders={() => navigate('/orders')}
                                  onGoToSubscription={() => navigate('/subscriptions')}
                                  onGoToOffers={() => navigate('/offers')}
                                  onGoToHistoric={() => navigate('/historic')}
                                  onGoToHome={() => navigate('/home')}
                                  onGoToAdmin={() => navigate('/admin')}
                                  onGoToDocument={() => navigate('/document')}
                                  onGoToSettings={() => navigate('/settings')}
                        />
                    </ProtectedRoute>
                } />

                <Route path="/orders" element={
                    <ProtectedRoute>
                        <Orders onLogout={handleLogout}
                                searchFilter={searchFilter}
                                clearSearchFilter={() => setSearchFilter(null)}
                                onGoToCustomerSelection={() => navigate('/customer-selection')}
                                onGoToCompanySelection={() => navigate('/company-selection')}
                                onGoToInvoices={() => navigate('/invoices')}
                                onGoToSubscription={() => navigate('/subscriptions')}
                                onGoToOffers={() => navigate('/offers')}
                                onGoToHistoric={() => navigate('/historic')}
                                onGoToHome={() => navigate('/home')}
                                onGoToAdmin={() => navigate('/admin')}
                                onGoToDocument={() => navigate('/document')}
                                onGoToSettings={() => navigate('/settings')}
                        />
                    </ProtectedRoute>
                }/>

                <Route path="/offers" element={
                    <ProtectedRoute>
                        <Offers onLogout={handleLogout}
                                searchFilter={searchFilter}
                                clearSearchFilter={() => setSearchFilter(null)}
                                onGoToCustomerSelection={() => navigate('/customer-selection')}
                                onGoToCompanySelection={() => navigate('/company-selection')}
                                onGoToInvoices={() => navigate('/invoices')}
                                onGoToSubscription={() => navigate('/subscriptions')}
                                onGoToOrders={() => navigate('/orders')}
                                onGoToHistoric={() => navigate('/historic')}
                                onGoToHome={() => navigate('/home')}
                                onGoToAdmin={() => navigate('/admin')}
                                onGoToDocument={() => navigate('/document')}
                                onGoToSettings={() => navigate('/settings')}
                        />
                    </ProtectedRoute>
                }/>

                <Route path="/historic" element={
                    <ProtectedRoute>
                        <Historic onLogout={handleLogout}
                                  onGoToCustomerSelection={() => navigate('/customer-selection')}
                                  onGoToCompanySelection={() => navigate('/company-selection')}
                                  onGoToOrders={() => navigate('/orders')}
                                  onGoToSubscription={() => navigate('/subscriptions')}
                                  onGoToOffers={() => navigate('/offers')}
                                  onGoToInvoices={() => navigate('/invoices')}
                                  onGoToHome={() => navigate('/home')}
                                  onGoToAdmin={() => navigate('/admin')}
                                  onGoToDocument={() => navigate('/document')}
                                  onGoToSettings={() => navigate('/settings')}
                        />
                    </ProtectedRoute>
                }/>

                <Route path="/admin" element={
                    <AdminRoute>
                        <Admin onLogout={handleLogout}
                               onGoToCustomerSelection={() => navigate('/customer-selection')}
                               onGoToCompanySelection={() => navigate('/company-selection')}
                               onGoToOrders={() => navigate('/orders')}
                               onGoToSubscription={() => navigate('/subscriptions')}
                               onGoToOffers={() => navigate('/offers')}
                               onGoToInvoices={() => navigate('/invoices')}
                               onGoToHistoric={() => navigate('/historic')}
                               onGoToHome={() => navigate('/home')}
                               onGoToDocument={() => navigate('/document')}
                               onGoToProfile={(u) => { setSelectedUser(u); navigate('/admin/profil'); }}
                               onGoToPermissions={(u) => { setSelectedUser(u); navigate('/admin/permissions'); }}
                               onGoToSettings={() => navigate('/settings')}
                        />
                    </AdminRoute>
                }/>

                <Route path="/admin/profil" element={
                    <AdminRoute>
                        <ProfilUser userToEdit={selectedUser}
                                    onBack={() => navigate('/admin')}
                                    onGoToCustomerSelection={() => navigate('/customer-selection')}
                                    onGoToCompanySelection={() => navigate('/company-selection')}
                                    onGoToSubscription={() => navigate('/subscriptions')}
                                    onGoToHome={() => navigate('/home')}
                                    onLogout={handleLogout}
                                    onGoToOrders={() => navigate('/orders')}
                                    onGoToOffers={() => navigate('/offers')}
                                    onGoToInvoices={() => navigate('/invoices')}
                                    onGoToHistoric={() => navigate('/historic')}
                                    onGoToPermissions={(u) => { setSelectedUser(u); navigate('/admin/permissions'); }}
                                    onGoToDocument={() => navigate('/document')}
                                    onGoToSettings={() => navigate('/settings')}
                        />
                    </AdminRoute>
                }/>

                <Route path="/admin/permissions" element={
                    <AdminRoute>
                        <PermissionsUser userToEdit={selectedUser}
                                         onBack={() => navigate('/admin')}
                                         onGoToCustomerSelection={() => navigate('/customer-selection')}
                                         onGoToCompanySelection={() => navigate('/company-selection')}
                                         onGoToSubscription={() => navigate('/subscriptions')}
                                         onGoToHome={() => navigate('/home')}
                                         onLogout={handleLogout}
                                         onGoToOrders={() => navigate('/orders')}
                                         onGoToOffers={() => navigate('/offers')}
                                         onGoToInvoices={() => navigate('/invoices')}
                                         onGoToHistoric={() => navigate('/historic')}
                                         onGoToProfile={(u) => { setSelectedUser(u); navigate('/admin/profil'); }}
                                         onGoToDocument={() => navigate('/document')}
                                         onGoToSettings={() => navigate('/settings')}
                        />
                    </AdminRoute>
                }/>

                <Route path="/settings" element={
                    <ProtectedRoute>
                        <Settings onLogout={handleLogout}
                                  onGoToHome={() => navigate('/home')}
                                  onGoToCustomerSelection={() => navigate('/customer-selection')}
                                  onGoToCompanySelection={() => navigate('/company-selection')}
                                  onGoToInvoices={() => navigate('/invoices')}
                                  onGoToOrders={() => navigate('/orders')}
                                  onGoToOffers={() => navigate('/offers')}
                                  onGoToHistoric={() => navigate('/historic')}
                                  onGoToAdmin={() => navigate('/admin')}
                                  onGoToDocument={() => navigate('/document')}
                                  onGoToSubscription={() => navigate('/subscriptions')}
                                  onGoToSettings={() => navigate('/settings')}
                        />
                    </ProtectedRoute>
                }/>

                <Route path="/document" element={
                    <ProtectedRoute>
                        <Infocentre onLogout={handleLogout}
                                    onGoToCustomerSelection={() => navigate('/customer-selection')}
                                    onGoToCompanySelection={() => navigate('/company-selection')}
                                  onGoToOrders={() => navigate('/orders')}
                                  onGoToSubscription={() => navigate('/subscriptions')}
                                  onGoToOffers={() => navigate('/offers')}
                                  onGoToInvoices={() => navigate('/invoices')}
                                  onGoToHistoric={() => navigate('/historic')}
                                  onGoToHome={() => navigate('/home')}
                                  onGoToAdmin={() => navigate('/admin')}
                                  onGoToSettings={() => navigate('/settings')}
                        />
                    </ProtectedRoute>
                }/>


            </Routes>
        </div>
    );
}

function App() {
    return (
        <ThemeProvider>
            <AppContent />
        </ThemeProvider>
    );
}

export default App;
