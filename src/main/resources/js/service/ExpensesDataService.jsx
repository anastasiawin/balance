import axios from 'axios'

const BUDGET_API_URL = 'http://localhost:8080'
const API_URL = `${BUDGET_API_URL}/api`

class ExpensesDataService {

    retrieveAllExpenses(name) {
        return axios.get(`${API_URL}/expenses`);
    }
    
    deleteExpense(id) {
	    //console.log('executed service')
	    return axios.delete(`${API_URL}/expenses/${id}`);
	}
	
	createExpense(expense) {
      return axios.post(`${API_URL}/expenses/`, expense);
  	}
}

export default new ExpensesDataService()

