import React from 'react';
import { render as _render } from 'react-dom';
import ExpensesComponent from './components/ExpensesComponent';

class App extends React.Component {


	render() {
		return (
			<ExpensesComponent/>
		)
	}
}



_render(
	<App />,
	document.getElementById('react')
)