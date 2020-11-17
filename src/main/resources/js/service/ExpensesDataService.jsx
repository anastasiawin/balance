import axios from 'axios'

const BUDGET_API_URL = 'https://budgetwin.herokuapp.com'
const API_URL = `${BUDGET_API_URL}/api`

class ExpensesDataService {

    retrieveAllExpenses(name) {
        return axios.get(`${API_URL}/expenses`, { crossDomain: true });
    }
    
    deleteExpense(id) {
	    //console.log('executed service')
	    return axios.delete(`${API_URL}/expenses/${id}`, { crossDomain: true });
	}
	
	createExpense(expense) {
      return axios.post(`${API_URL}/expenses/`, expense, { crossDomain: true });
  	}
}

export default new ExpensesDataService()

