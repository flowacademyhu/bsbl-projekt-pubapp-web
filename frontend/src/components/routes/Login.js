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
    /*
    for (let key in this.state.formData) {
      dataToSubmit[key] = this.state.formData[key].value;
    }
    */
    console.log(dataToSubmit);
    let data = JSON.stringify(dataToSubmit);
    console.log(data);
    //console.log(data);
    // {"email":"value", "password":"value"}
    axios.defaults.headers.post['Content-Type'] = 'application/json';
    axios.post('localhost:8080/sessions', dataToSubmit, {
      headers: {
        'Content-Type': 'application/json',
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
/*
import React, { Component } from "react";
import axios from 'axios'
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
export default class Login extends Component {

  constructor(props) {

    super(props);

    this.state = {

      email: "",

      password: ""

    };

  }

  validateForm() {

    return this.state.email.length > 0 && this.state.password.length > 0;

  }

  handleChange = event => {

    this.setState({

      [event.target.id]: event.target.value

    });

  }

  handleSubmit = event => {
    console.log("lófasz")
    event.preventDefault();
    console.log(this.state);
    console.log(this.state.email);
    console.log(this.state.password);

    //let date = { "email}
    let data = JSON.stringify({
    email: this.state.email,
    password: this.state.password});
    axios.post('localhost:8080/sessions',
    JSON.stringify({
            email: 'pista@gmail.com',
            password: '123456',
    }), {
      headers: { 
        "Content-Type": "application/x-www-form-urlencoded"
      }
    }).then(function(response) {

        console.log(response);

    });

  }

  render() {

    return (

      <div className="Login">

        <form onSubmit={this.handleSubmit}>

          <FormGroup controlId="email" bsSize="large">

            <ControlLabel>Email</ControlLabel>

            <FormControl

              autoFocus

              type="email"

              value={this.state.email}

              onChange={this.handleChange}

            />

          </FormGroup>

          <FormGroup controlId="password" bsSize="large">

            <ControlLabel>Password</ControlLabel>

            <FormControl

              value={this.state.password}

              onChange={this.handleChange}

              type="password"

            />

          </FormGroup>

          <Button

            block

            bsSize="large"

            disabled={!this.validateForm()}

            type="submit"

          >

            Login

          </Button>

        </form>

      </div>

    );

  }

}
*/