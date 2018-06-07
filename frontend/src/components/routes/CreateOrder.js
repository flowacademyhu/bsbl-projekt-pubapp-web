import React from 'react';
import axios from 'axios';
import MyHeader from './../header/header';
//import NewOrder from './NewOrder';

export default class CreateOrders extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [],
      order: []
    };
  }

  componentDidMount() {
    var config = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin': '*',
      crossdomain: true
    };

    axios
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
        <button type='submit'>Done</button>
      </div>
    );
  }

  /*
    addOrderLine (newState, product) {
      this.state.orderline.productName = product.name,
        this.state.orderline.quantity = newState,
        this.state.orders.push(this.state.orderline);
    };
  */

  renderProducts() {
    const renderProducts = this.state.items.map(function (product, i) {
      return <tr key={i}>
        <td> {product.category} \ {product.name} ({product.price} Ft, {product.xpValue} xp)</td>
        <td> <input type='number' /></td>
      </tr >;
    });

    return renderProducts;
  }
}

//{(e) => this.setState.order({ [product.name]: e.target.value })}