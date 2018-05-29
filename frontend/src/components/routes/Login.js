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

    /*
    { email: email, password: pass }
    */
    
   submitForm = (event) => {
       event.preventDefault();
       let dataToSubmit = {};
       for(let key in this.state.formData){
           dataToSubmit[key] = this.state.formData[key].value;
       }
       console.log(dataToSubmit)
       let data = JSON.stringify(dataToSubmit)
       console.log(data)
       axios.post('localhost:8080/sessions/', dataToSubmit
           )
         .then(function (response) {
           console.log(response);
         })
         .catch(function (error) {
               if (error.response) {
                 // The request was made and the server responded with a status code
                 // that falls out of the range of 2xx
                 console.log(error.response.data);
                 console.log(error.response.status);
                 console.log(error.response.headers);
               } else if (error.request) {
                 // The request was made but no response was received
                 // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                 // http.ClientRequest in node.js
                 console.log(error.request);
               } else {
                 // Something happened in setting up the request that triggered an Error
                 console.log('Error', error.message);
               }
               console.log(error.config);
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
