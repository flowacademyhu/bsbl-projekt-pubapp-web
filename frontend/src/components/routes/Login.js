import React, { Component } from 'react';
import FormFields from './widgets/forms/FormFields';
import axios from 'axios';
import {Jumbotron} from 'react-bootstrap';


class Login extends Component {
    constructor(props) {
    super(props);
    this.state = {
      formData: {
        email: {
          element: 'input',
          value: '',
          label: true,
          labelText: 'Email',
          config: {
            name: 'email_input',
            type:'email',
            placeholder: 'Enter your email'
          },
          validation: {
            required: true,
            minLen:5
          },
          valid: false,
          touched: false,
          validationMessage: ''
        },
        password: {
          element: 'input',
          value: '',
          label: true,
          labelText: 'Password',
          config: {
            name: 'password_input',
            type: 'password',
            placeholder: 'Enter your password'
          },
          validation: {
            required: true,
            minLen:5
          },
          valid: false,
          touched: false,
          validationMessage: ''
        }
      }
    };
  }
  updateForm(newState) {
    this.setState({
      formData: newState
    });
  }

  submitForm = (event) => {
    event.preventDefault();
    let dataToSubmit = { email: this.state.formData.email.value, password: this.state.formData.password.value };
    console.log(dataToSubmit);
    let data = JSON.stringify(dataToSubmit);

    axios.defaults.headers.post['Content-Type'] = 'application/json';
    axios.post('http://127.0.0.1:8080/sessions', data, {
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(function (response) {
        
        console.log(response);
        const status = JSON.parse(response.status);
        console.log(response.status);

        if (status === +200) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      window.location.replace('/home');
        }
      }
      
      
    
    )

   

      

  }
  render() {

       return (
      <div className='container'>
        <form onSubmit={this.submitForm}>
          <FormFields
            formData={this.state.formData}
            change={(newState) => this.updateForm(newState)}
          />
          <button type='submit'>Submit</button>
        </form>
        <Jumbotron>
          <h1>Welcome </h1>
          <h2> Orgiginal PubAPP </h2>

          <ul>
            <li> gooogle play  link <a href='asdasdasd'> click me </a>  </li>
            <li> app store link <a href='asdasdasd'>click me </a> </li>
          </ul>
        </Jumbotron>
      </div>
    )
  }
}
export default Login;
