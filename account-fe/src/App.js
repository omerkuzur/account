import './App.css';
import { Component } from 'react';
import logo from './logo.svg';


class App extends Component {

  state = {
    customer: {
      accounts: []
    }
  };

  async componentDidMount() {
    const response = await fetch('/v1/customer/0943d54e-2cff-4857-9cae-e0d1d6c0a97d');
    const body = await response.json();
    this.setState({ customer: body });
  }


  render() {
    const { customer } = this.state;
    return (
      <div className="App">
        <header className="App-Header">
          <img src={logo} className="App-logo" alt="logo" />
          <div className='App-intro'>
            <h2> Customer</h2>
            <div key={customer.id}>
              {customer.name} {customer.surname}

              {
                customer.accounts.map(account =>
                (<p> Accounts:  {account.id},   {account.balance},  {account.creationDate}
                  {
                    account.transations.map(transation =>
                      (<p>Transactions
                        : {transation.id}, {transation.amount},  {transation.transactionDate}, {transation.transationType}</p>)

                    )}
                </p>)
                )
              }
            </div>
          </div>
        </header>
      </div>
    );
  }

}

export default App;
