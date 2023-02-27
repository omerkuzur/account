import './App.css';
import { Component } from 'react';
import logo from './logo.svg';
import { Link, withRouter } from 'react-router-dom';

class Account extends Component{
    state = {
        account: {
          transactions: []
        }
      };


      async handleSubmit(event){
        event.prevenDefault();
        const {item} = this.state;

        await fetch('/v1/account' , {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/clients');
      }
}