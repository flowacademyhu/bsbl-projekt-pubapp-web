import React from 'react';
import FormFields from './../../widgets/forms/FormFields';
import axios from 'axios';

class CreateAchievementConditionForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      products: this.props.products,
      formData: {
        quantity: {
          element: 'input',
          value: '',
          label: true,
          labelText: 'quantity',
          config: {
            name: 'name_input',
            text: 'text',
            placeholder: 'enter quantity'
          },
          validation: {
            required: false,
          },
          valid: true,
        },

        productName: {
          element: 'select',
          value: '',
          label: true,
          labelText: 'product',
          config: {
            name: 'name_input',
            options: [
              /* { val: products[1].name, text: products[1].name } */
          ],

          },
          validation: {
            required: false,
          },
          valid: true,
        }
      },
      id: this.props.id
    };
  }

  updateForm(newState) {
    this.setState({
      formData: newState
    });
  }

  submitForm = (event) => {
    event.preventDefault();
    let datatosubmit = { quantity: this.state.formData.quantity.value, productName: this.state.formData.productName.value }
    let data = JSON.stringify(datatosubmit);
    let id = this.state.id;
    axios.defaults.headers.post['Content-Type'] = 'application/json';
    axios.post(`http://127.0.0.1:8080/achievements/${id}/achievement_conditions`, data, {
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Authorization': document.cookie
      }
    })
      .then(function (response) {
        const status = JSON.parse(response.status);
        console.log(response.status);

        if (status === +200) {
          window.location.replace('/achievements');
        }
      })
      .catch(function (error) {
        console.log(error.response);
        console.log(error);
        console.log(error.name);
        console.log(error.value);
        console.log(error.status);
        console.log(error.type);
      });
  }

  render() {
    console.log(this.state.products[1].name);
    return (
      <div>
        <form onSubmit={this.submitForm}>
          <FormFields
            formData={this.state.formData}
            change={(newState) => this.updateForm(newState)}
          />
          <button type="submit">Add condition</button>
        </form>
      </div>
    )
  }
}

export default CreateAchievementConditionForm;