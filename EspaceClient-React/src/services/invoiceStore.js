import CustomStore from 'devextreme/data/custom_store';

const API_URL = 'http://localhost:8080/votre-app/api/invoices';

export const invoiceStore = new CustomStore({
    key: 'id',
    load: async (loadOptions) => {
        try{
            //Appel vers le backend java
            const response = await fetch(API_URL, {
                method: 'GET',
                headers:{
                    'Content-Type': 'application/json',
                    //Ajouts du token plus tard
                }
            });
            if (!response.ok){
                throw new Error('Erreur lors de la récupération des données');
            }
            const result = await response.json();

            //Envoi un objet a dev vu qu'il attend ca pour le store
            return{
                data: result.data || result,
                totalCount: result.totalCount || result.length
            };
        } catch (error){
            console.error("Erreur Store : ", error);
            return {
                data: [],
                totalCount: 0
            };
        }
    }
});