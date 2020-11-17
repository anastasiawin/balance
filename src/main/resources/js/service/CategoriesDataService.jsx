import axios from 'axios'
import { API_ROOT } from '../api-config';

const BUDGET_API_URL = 'https://budgetwin.herokuapp.com'
const API_URL = `${BUDGET_API_URL}/api`

class CategoriesDataService {

    retrieveAllCategories() {
        return axios.get(`${API_ROOT}/categories`, { crossDomain: true });
    }
    
    deleteCategory(id) {
	    //console.log('executed service')
	    return axios.delete(`${API_ROOT}/categories/${id}`, { crossDomain: true });
	}
	
	createCategory(category) {
      return axios.post(`${API_ROOT}/categories/`, category, { crossDomain: true });
  	}
}

export default new CategoriesDataService()

