import React from 'react';
import { render as _render } from 'react-dom';
import ExpensesComponent from './components/ExpensesComponent';
import CategoriesComponent from './components/CategoriesComponent';
import NoMatch from './components/NoMatch';
import '../css/bootstrap-dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { NavigationBar } from './components/NavigationBar';

class App extends React.Component {


	render() {
		return (
			<React.Fragment>
  				<Router>
					<NavigationBar />
					<Switch>
  						<Route exact path="/" component={ExpensesComponent} />
  						<Route path="/categories" component={CategoriesComponent} />
						  <Route component={NoMatch} />  						
					</Switch>

  				</Router>

			</React.Fragment>
		)
	}
}



_render(
	<App />,
	document.getElementById('react')
)