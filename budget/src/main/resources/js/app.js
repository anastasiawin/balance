import React from 'react';
import { render as _render } from 'react-dom';
import rest from 'rest';
import mime from 'rest/interceptor/mime';


class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {expenses: []};
	}

	componentDidMount() {
		const client = rest.wrap(mime);
		client({method: 'GET', path: '/api/expenses'}).done(response => {

			this.setState({expenses: response.entity});

		});
	}

	render() {
		return (
			<ExpensesList expenses={this.state.expenses}/>
		)
	}
}

class ExpensesList extends React.Component{
	render() {
		    
		console.log(this.props.expenses);
			console.log(this.props.expenses.length);
			console.log(Array.isArray(this.props.expenses.entity));
		const expenses = this.props.expenses.map(expense =>
			<Expense key={expense.id} expense={expense}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Name</th>
						<th>Description</th>
						<th>Total</th>
						<th>Category</th>
						<th>Date</th>
					</tr>
					{expenses}
				</tbody>
			</table>
		)
	}
}

class Expense extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.expense.name}</td>
				<td>{this.props.expense.description}</td>
				<td>{this.props.expense.total}</td>
				<td>{this.props.expense.categoryName}</td>
				<td>{this.props.expense.date}</td>
			</tr>
		)
	}
}

_render(
	<App />,
	document.getElementById('react')
)