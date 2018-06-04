import React from 'react';
import FormFields from '../widgets/forms/FormFields';
import axios from 'axios';
export default class ProductItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            formData: {
                name: {
                    element: 'input',
                    value: '',
                    label: true,
                    labelText: 'Product Name',
                    config: {
                        name: 'name_input',
                        type: 'text',
                        placeholder: 'Enter the product name'
                    },
                    validation: {
                        required: false
                    },
                    valid: true
                },
                price: {
                    element: 'input',
                    value: '',
                    label: true,
                    labelText: 'Price',
                    config: {
                        name: 'price_input',
                        type: 'text',
                        placeholder: 'Enter the product price'
                    },
                    validation: {
                        required: false
                    },
                    valid: true
                },
                xpValue: {
                    element: 'input',
                    value: '',
                    label: true,
                    labelText: 'Xp Value',
                    config: {
                        name: 'price_input',
                        type: 'text',
                        placeholder: 'Enter the product xp value'
                    },
                    validation: {
                        required: false
                    },
                    valid: true
                },
                category: {
                    element: 'select',
                    value: '',
                    label: true,
                    labelText: 'Categorys',
                    config: {
                        name: 'categorys_input',
                        options: [
                            { val: '0', text: 'Select a category' },
                            { val: 'SOFT', text: 'soft' },
                            { val: 'BEER', text: 'beer' },
                            { val: 'WINE', text: 'wine' },
                            { val: 'WHISKEY', text: 'whiskey' },
                            { val: 'VODKA', text: 'vodka' },
                            { val: 'TEQUILA', text: 'tequila' },
                            { val: 'MISC', text: 'misc' }
                        ]
                    },
                    validation: {
                        required: false
                    },
                    valid: true
                }
            }
        };
    }
    updateForm = (newState) => {
        this.setState({
            formData: newState
        })
    }

    submitForm = (event) => {
        event.preventDefault();
        let datatosubmit = { name: this.state.formData.name.value, price: this.state.formData.price.value, xpValue: this.state.formData.xpValue.value, category: this.state.formData.category.value }
        let data = JSON.stringify(datatosubmit);
        console.log(data)
        axios.defaults.headers.post['Content-Type'] = 'application/json';
        axios.post('http://127.0.0.1:8080/products', data, {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        })
            .then(function (response) {
                const status = JSON.parse(response.status);
                console.log(response.status);

                if (status === +200) {
                    window.location.replace('/product');
                }
            })
    }

    render() {
        return (
            <div> <form onSubmit={this.submitForm}>
                <FormFields
                    formData={this.state.formData}
                    change={(newState) => this.updateForm(newState)}
                />        <button type='submit'>Submit</button>
            </form></div>
        );
    }
}