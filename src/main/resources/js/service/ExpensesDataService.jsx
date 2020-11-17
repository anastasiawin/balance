import axios from 'axios'
import { API_ROOT } from '../api-config';

class ExpensesDataService {

    retrieveAllExpenses(name) {
        return axios.get(`${API_ROOT}/expenses`, { crossDomain: true });
    }
    
    deleteExpense(id) {
	    //console.log('executed service')
	    return axios.delete(`${API_ROOT}/expenses/${id}`, { crossDomain: true });
	}
	
	createExpense(expense) {
      return axios.post(`${API_ROOT}/expenses/`, expense, { crossDomain: true });
  	}
}

export default new ExpensesDataService()

