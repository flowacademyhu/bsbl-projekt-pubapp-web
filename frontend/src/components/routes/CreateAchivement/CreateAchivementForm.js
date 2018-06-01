import Button from 'react-bootstrap/lib/Button';
import React from 'react';
import FormFields from '../widgets/forms/FormFields';
import axios from 'axios';
import styles from './new.css';


class CreateAchivementForm extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
    formData:{
      name:{
        element:'input',
        value:'',
        label:true,
        labelText:'name',
        config:{
          name:'name_input',
          text:'text',
          placeholder:'enter a name'

        }
      },

      description:{
        element:'textarea',
        value:'',
        label:true,
        labelText:'',
        config:{
          name:'name_input',
       rows: 15,
       cols: 36

        }
      },

      xpvalue:{
        element:'input',
        value:'',
        label:true,
        labelText:'xp value       ',
        config:{
          name:'name_input',
          text:'text',
          placeholder:'enter xp value'

        }
      },

      expiration :{
        element:'input',
        value:'',
        label:true,
        labelText:'expiration date',
        config:{
          name:'name_input',
          text:'text',
          placeholder:'enter expiration date'

        }
      }
    }
  }
  }
  updateForm = (newState) => {
    this.setState({
        formData:newState
    })
  
}



submitForm = (event) => {
  event.preventDefault();
  let datatosubmit = {name:this.state.formData.name.value, description:this.state.formData.description.value, xpvalue:this.state.formData.xpvalue.value, expiration:this.state.formData.expiration.value}
    let data = JSON.stringify(datatosubmit);
    console.log(data)
    axios.defaults.headers.post['Content-Type'] = 'application/json';
    axios.post('http://127.0.0.1:8080/achivements', data, {
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
    .then(function (response) {
      const dara = response
      console.log(dara);
      const status = JSON.parse(response.status);
      console.log(response.status);

      if (status === +200) {
        window.location.replace('/home');
      }
    })
}

  render () {
    return (
      <div> 
        <form onSubmit={this.submitForm}>
        <FormFields
                        formData={this.state.formData}
                        change={(newState) => this.updateForm(newState)}
                    />

        <button type="submit"> Submit</button>
        </form>
      </div>
    )
  }
}


export default CreateAchivementForm;
