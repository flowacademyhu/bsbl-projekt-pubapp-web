import React from 'react';
import { FormGroup, Form, Col, FormControl, Button, ControlLabel } from 'react-bootstrap';

export default class Login extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      email: '',
      password: ''
    };
  }

  /*
  submitForm = (event) => {
    event.preventDefault();
    let dataToSubmit = {};
    for (let key in this.state) {
      dataToSubmit[key] = this.state[key].value;
    }
    console.log(dataToSubmit)
    axios.
}
*/

  onChangeEmail (element) {
    this.setState({ email: element.target.value });
  }
  onChangePassword (element) {
    this.setState({ password: element.target.value });
  }
  onSubmit (event) {
    event.preventDefault();
    let dataToSubmit = {};
    dataToSubmit = {
      'email': ,
      'password': this.state.password
    };

    console.log(dataToSubmit);
  }

  render () {
    return (
      <div> <Form horizontal onSubmit={this.onSubmit}>
        <FormGroup controlId='formHorizontalEmail'>
          <Col componentClass={ControlLabel} sm={2}>
            Email
          </Col>
          <Col sm={10}>
            <FormControl type='email' placeholder='Email' value={this.state.email}
              onChange={this.onChangeEmail.bind(this)}
              formemail={this.state.email} />
          </Col>
        </FormGroup>
        <FormGroup controlId='formHorizontalPassword'>
          <Col componentClass={ControlLabel} sm={2}>
            Password
          </Col>
          <Col sm={10}>
            <FormControl type='password' placeholder='Password' value={this.state.password}
              onChange={this.onChangePassword.bind(this)}
              formpass={this.state.password} />
          </Col>
        </FormGroup>

        <FormGroup>
          <Col smOffset={2} sm={10}>
            <Button type='submit'>Sign in</Button>
          </Col>
        </FormGroup>
      </Form>;
      </div>
    );
  }
}
