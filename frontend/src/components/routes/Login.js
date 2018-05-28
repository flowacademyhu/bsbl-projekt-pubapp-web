import React, { Component } from 'react';
import FormFields from './widgets/forms/FormFields';
import axios from 'axios'

class User extends Component {

    state = {
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
                }
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
                }
            },
        }
        
    }

    updateForm = (newState) => {
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
        console.log(dataToSubmit)
       // axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
        axios.post('/sessions', {
            email: dataToSubmit.email.value,
            password: dataToSubmit.password.value
          })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          });
    }

    render(){
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
