import React from 'react';
import axios from 'axios';
import MyHeader from './../header/header';
//import NewOrder from './NewOrder';

export default class CreateOrders extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [],
      orders: {
      }
    };
  }

  async componentWillMount () {
    var config = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': document.cookie,
      crossdomain: true
    };

    await axios
      .get('http://127.0.0.1:8080/products', { headers: config })
      .then(({ data }) => {
        console.log(data);
        this.setState(
          { items: data }
        );
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

    this.prepareOrders();
  }

  goto() {
    window.location.replace('/Orders');
  }

  render() {
    console.log(this.state);
    return (
      <div>
        <MyHeader />
        <table>
          <thead>

            <td>Select product: </td>
            <td>Quantity</td>

          </thead>
          <tbody>{this.renderProducts()}</tbody>
        </table>
        <button type='submit' >Done</button>
      </div>
    );
  }

  submitForm = (event) => {
    event.preventDefault();
    let dataToSubmit = this.state.orders;
    console.log(dataToSubmit);
    let data = JSON.stringify(dataToSubmit);
      
        axios.defaults.headers.post['Content-Type'] = 'application/json';
        axios.post('http://127.0.01:8080/', data, {
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization': document.cookie
          }
        })

        .then(function (response) {
          console.log(response);
          const status = JSON.parse(response.status);
          console.log(response.status);

        if (status === +200) {
          window.location.replace('/orders');

        }
        
        
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

  prepareOrders () {
    let ordersCopy = this.state.orders;
    let itemsCopy = this.state.items;
    for (let i = 0; i < itemsCopy.length; i++) {
      ordersCopy[itemsCopy[i].name] = 0;
    }

    this.setState({ orders: ordersCopy });
    console.log(this.state.orders);
  }

  getProductChange (productName) {
    return element => {
      let orders = this.state.orders;
      orders[productName] = parseInt(element.target.value);
      this.setState({ orders });
    };
  }

  renderProducts () {
    const renderProducts = this.state.items.map((product, i) => {
      return <tr key={i}>
        <td> {product.category} \ {product.name} ({product.price} Ft, {product.xpValue} xp)</td>
        <td> <input type='number' onChange={this.getProductChange(product.name)} /></td>
      </tr >;
    });

    return renderProducts;
  }
}

//{(e) => this.setState.order({ [product.name]: e.target.value })}