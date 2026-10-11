import React, { useState } from 'react';
import '../../css/dx.EGlobal.css';
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


const ProtectedRoute = ({ children }) => {
    const token = localStorage.getItem('token');

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    return children;
};

function AppContent() {
    const navigate = useNavigate();
    const { resetTheme } = useTheme();
    const [user, setUser] = useState(localStorage.getItem('username') || '');
    const [selectedUser, setSelectedUser] = useState(null);
    const [searchFilter, setSearchFilter] = useState(null);

    const handleLogin = (name) => {
        setUser(name);
        localStorage.setItem('username', name);
        navigate('/home');
    }

    const handleLogout = () => {
        localStorage.clear();
        resetTheme();
        navigate('/login');
    }

    return (
        <div className="App dx-viewport">
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />

                <Route path="/login" element={
                    <Login onLoginSuccess={handleLogin}
                           onGoToRegister={() => navigate('/register')}
                    />}
                />
                <Route path="/register" element={
                    <Register onBack={() => navigate('/login')}
                    />}
                />

                <Route path="/home" element={
                    <ProtectedRoute>
                        <Home username={user} onLogout={handleLogout}
                              onSearchNavigate={(type, field, value) => {
                                    setSearchFilter({ field, value});
                                    navigate(`/${type}`);
                              }}
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
                    <ProtectedRoute>
                        <Admin onLogout={handleLogout}
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
                    </ProtectedRoute>
                }/>

                <Route path="/admin/profil" element={
                    <ProtectedRoute>
                        <ProfilUser userToEdit={selectedUser}
                                    onBack={() => navigate('/admin')}
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
                    </ProtectedRoute>
                }/>

                <Route path="/admin/permissions" element={
                    <ProtectedRoute>
                        <PermissionsUser userToEdit={selectedUser}
                                         onBack={() => navigate('/admin')}
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
                    </ProtectedRoute>
                }/>

                <Route path="/settings" element={
                    <ProtectedRoute>
                        <Settings onLogout={handleLogout}
                                  onGoToHome={() => navigate('/home')}
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
