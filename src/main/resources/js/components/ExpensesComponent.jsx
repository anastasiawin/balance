import React from 'react';
import rest from 'rest';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import ExpensesDataService from '../service/ExpensesDataService';


class ExpensesComponent extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			expenses: [],
			message: null,
			categoryId: '',
			name: '',
			description: '',
			total: '',
			date: ''
		};
		this.refreshExpenses = this.refreshExpenses.bind(this)
		this.deleteExpenseClicked = this.deleteExpenseClicked.bind(this)
		this.onSubmit = this.onSubmit.bind(this)
		this.validate = this.validate.bind(this)
	}

	componentDidMount() {

		this.refreshExpenses();
	}

	render() {
		let { categoryId, name, description, total, date } = this.state
		return (
			<div className="container">
			<h1>Расходы</h1>
			<div className="container">
                <Formik initialValues={{ categoryId, name, description, total, date }} onSubmit={this.onSubmit}
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
                                    <label>Category</label>
                                    <Field className="form-control" type="text" name="categoryId" />
                                </fieldset>
                                <fieldset className="form-group">
                                    <label>Name</label>
                                    <Field className="form-control" type="text" name="name" />
                                </fieldset>
                                <fieldset className="form-group">
                                    <label>Description</label>
                                    <Field className="form-control" type="text" name="description" />
                                </fieldset>
                                <fieldset className="form-group">
                                    <label>Total</label>
                                    <Field className="form-control" type="text" name="total" />
                                </fieldset>
                    			<fieldset className="form-group">
                                    <label>Date</label>
                                    <Field className="form-control" type="text" name="date" />
                                </fieldset>
                                <button className="btn btn-success" type="submit">Add</button>
                            </Form>
                        )
                    }
                </Formik>

            </div>
			
			<div className="container">
				{this.state.message && <div class="alert alert-success">{this.state.message}</div>}
				<table  className="table">
					<thead>
						<tr>
							<th>Category</th>
							<th>Name</th>
							<th>Description</th>
							<th>Total</th>
							<th>Date</th>
						</tr>
					</thead>
					<tbody>
					 {
                        this.state.expenses.map(
                            expense =>
					 			<tr key={expense.id}>
									<td>{expense.categoryName}</td>
									<td>{expense.name}</td>
									<td>{expense.description}</td>
									<td>{expense.total}</td>
									<td>{expense.date}</td>
									<td><button className="btn btn-warning" onClick={() => this.deleteExpenseClicked(expense.id)}>Delete</button></td>
								</tr>
                            )
                        }
					</tbody>
				</table>
			</div>
			</div>
		)
	}
	
		refreshExpenses() {
        ExpensesDataService.retrieveAllExpenses()
            .then(
                response => {
                    console.log(response);
                    this.setState({ expenses: response.data })
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

    deleteExpenseClicked(id) {
    ExpensesDataService.deleteExpense(id)
        .then(
            response => {
            	console.log("message")
                this.setState({ message: `Delete of expense Successful` })
                this.refreshExpenses()
            }
        )

	}
	
	onSubmit(values) {
		let expense = {
            name: values.name,
            description: values.description,
            categoryId: values.categoryId,
            date: values.date,
            total: values.total
        }
        ExpensesDataService.createExpense(expense)
                .then(() => this.refreshExpenses())
    	console.log(values);
	}
}



export default ExpensesComponent;