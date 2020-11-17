import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import CategoriesDataService from '../service/CategoriesDataService';


class CategoriesComponent extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			categories: [],
			message: null,
			categoryId: '',
			name: '',
			description: ''
		};
		this.refreshExpensesCategories = this.refreshCategories.bind(this)
		this.deleteCategoryClicked = this.deleteCategoryClicked.bind(this)
		this.onSubmit = this.onSubmit.bind(this)
		this.validate = this.validate.bind(this)
	}

	componentDidMount() {

		this.refreshCategories();
	}

	render() {
		let { categoryId, name, description, total, date } = this.state
		return (
			<div className="container">
			<h1>Категории</h1>
			<div className="container">
                <Formik initialValues={{ categoryId, name, description }} onSubmit={this.onSubmit}
	                validateOnChange={false}
	      			validateOnBlur={false}
	      			validate={this.validate}
	      			enableReinitialize={true}
                >
                    {
                        (props) => (
                            <Form>
                            	<ErrorMessage name="description" component="div" className="alert alert-warning" />
                                <fieldset className="form-group">
                                    <label>Категория</label>
                                    <Field className="form-control" type="text" name="name" />
                                </fieldset>
                                <fieldset className="form-group">
                                    <label>Описание</label>
                                    <Field className="form-control" type="text" name="description" />
                                </fieldset>
        
                                <button className="btn btn-success" type="submit">Add</button>
                            </Form>
                        )
                    }
                </Formik>

            </div>
			
			<div className="container">
				{this.state.message && <div className="alert alert-success">{this.state.message}</div>}
				<table  className="table">
					<thead>
						<tr>
							<th>Категория</th>
							<th>Описание</th>
						</tr>
					</thead>
					<tbody>
					 {
                        this.state.categories.map(
                            category =>
					 			<tr key={category.id}>
									<td>{category.name}</td>
									<td>{category.description}</td>
									<td><button className="btn btn-warning" onClick={() => this.deleteCategoryClicked(category.id)}>Delete</button></td>
								</tr>
                            )
                        }
					</tbody>
				</table>
			</div>
			</div>
		)
	}
	
	refreshCategories() {
        CategoriesDataService.retrieveAllCategories()
            .then(
                response => {
                    console.log(response);
                    this.setState({ categories: response.data })
                }
               )
      }
      
    validate(values) {
	    let errors = {}
	    if (!values.name) {
	        errors.description = 'Enter a Name'
	    }

    return errors
}

    deleteCategoryClicked(id) {
    CategoriesDataService.deleteCategory(id)
        .then(
            response => {
            	console.log("message")
                this.setState({ message: `Delete of category is successful` })
                this.refreshCategories()
            }
        )

	}
	
	onSubmit(values) {
		let category = {
            name: values.name,
            description: values.description
        }
        CategoriesDataService.createCategory(category)
                .then(() => this.refreshCategories())
    	console.log(values);
	}
}



export default CategoriesComponent;