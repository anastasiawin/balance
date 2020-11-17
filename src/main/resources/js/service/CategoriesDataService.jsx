import axios from 'axios'

const BUDGET_API_URL = 'http://localhost:8080'
const API_URL = `${BUDGET_API_URL}/api`

class CategoriesDataService {

    retrieveAllCategories() {
        return axios.get(`${API_URL}/categories`);
    }
    
    deleteCategory(id) {
	    //console.log('executed service')
	    return axios.delete(`${API_URL}/categories/${id}`);
	}
	
	createCategory(category) {
      return axios.post(`${API_URL}/categories/`, category);
  	}
}

export default new CategoriesDataService()

