import axios from 'axios'

const BUDGET_API_URL = 'https://budgetwin.herokuapp.com'
const API_URL = `${BUDGET_API_URL}/api`

class CategoriesDataService {

    retrieveAllCategories() {
        return axios.get(`${API_URL}/categories`, { crossDomain: true });
    }
    
    deleteCategory(id) {
	    //console.log('executed service')
	    return axios.delete(`${API_URL}/categories/${id}`, { crossDomain: true });
	}
	
	createCategory(category) {
      return axios.post(`${API_URL}/categories/`, category, { crossDomain: true });
  	}
}

export default new CategoriesDataService()

