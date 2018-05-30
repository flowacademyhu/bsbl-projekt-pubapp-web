import React, { Component } from 'react';
import FormFields from './widgets/forms/FormFields';
import axios from 'axios';
class User extends Component {
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
            type: 'text',
            placeholder: 'Enter your email'
          },
          validation: {
            required: true,
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
          },
          valid: false,
          touched: false,
          validationMessage: ''
        }
      }
    };
  }
  updateForm (newState) {
    this.setState({
      formData: newState  
    });
  }

  submitForm = (event) => {
    event.preventDefault();
    let dataToSubmit = {email: this.state.formData.email.value, password: this.state.formData.password.value};
    console.log(dataToSubmit);
    let data = JSON.stringify(dataToSubmit);
    console.log(data);
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
      })
      .catch(function (error) {
        if (error.response) {
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }
        console.log(error.config);
        console.log(error);
      });

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
      </div>
    )
  }
}
export default User;