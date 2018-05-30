import React, { Component } from 'react';
import FormFields from './widgets/forms/FormFields';
import axios from 'axios';
class User extends Component {
   constructor (props) {
       super(props);
       this.state = {
       formData:{
           email:{
               element:'input',
               value:'',
               label:true,
               labelText:'Email',
               config:{
                   name:'email_input',
                   type:'text',
                   placeholder:'Enter your email'
               },
               validation:{
                   required:true,
               },
               valid:false,
               touched:false,
               validationMessage:''
           },
           password:{
               element:'input',
               value:'',
               label:true,
               labelText:'Password',
               config:{
                   name:'password_input',
                   type:'password',
                   placeholder:'Enter your password'
               },
               validation:{
                   required:true,
               },
               valid:false,
               touched:false,
               validationMessage:''
           }
           }
       }
   }    updateForm = (newState) => {
       this.setState({
           formData:newState
       })
   }
   submitForm = (event) => {
       event.preventDefault();
       let dataToSubmit = {};
       for(let key in this.state.formData){
           dataToSubmit[key] = this.state.formData[key].value;
       }
       console.log(dataToSubmit)        let data = JSON.stringify(dataToSubmit)
       console.log(data)
       // {"email":"value", "password":"value"}
       axios.post('localhost:8080/sessions', data,{
           headers: {
               'Content-Type': 'application/json',
           }})
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
   }    render(){
       return(
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
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import axios from 'axios'

export default class Login extends Component {

  constructor(props) {

    super(props);

    this.state = {

      email: "",

      password: ""

    };

  }

  handleClick(event){
   
    var self = this;
   
    var payload={
   
    "email":this.state.email,
   
    "password":this.state.password
   
    }
   
    axios.post('localhost:8080/sessions/', payload)
   
    .then(function (response) {
   
    console.log(response);
   
    if(response.data.code == 200){
   
    console.log("Login successfull");
   
    }
   
    else if(response.data.code == 204){
   
    console.log("Username password do not match");
   
    alert("username password do not match")
   
    }
   
    else{
   
    console.log("Username does not exists");
   
    alert("Username does not exist");
   
    }
   
    })
   
    .catch(function (error) {
   
    console.log(error);
   
    });
   
    }

  handleSubmit = event => {

    event.preventDefault();

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