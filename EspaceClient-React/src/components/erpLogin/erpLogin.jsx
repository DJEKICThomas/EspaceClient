import { useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';

function ErpLogin(){
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    useEffect(() => {
        const token = searchParams.get('token');
        const ncs = searchParams.get('ncs');
        const profile = searchParams.get('profile');
        const customerName = searchParams.get('customerName');
        const username = searchParams.get('username');

        if(token){
            localStorage.setItem('token', token);
            localStorage.setItem('selectedNcs', ncs);
            localStorage.setItem('profile', profile);
            localStorage.setItem('selectedCustomerName', customerName);
            localStorage.setItem('username', username);
            navigate('/home');
        } else {
            navigate('/login');
        }
    }, [searchParams, navigate]);

    return null;
}

export default ErpLogin;