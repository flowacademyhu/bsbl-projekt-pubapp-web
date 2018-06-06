import React from 'react';
import axios from 'axios';
import MyHeader from './../header/header';

export default class Orders extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      items: []
    };
  }

  componentDidMount () {
    var config = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin': '*',
      crossdomain: true
    };

    axios
      .get('http://127.0.0.1:8080/ordering', { headers: config })
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

  goto () {
    axios
      .post('http://127.0.0.1:8080/ordering', {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Authorization': document.cookie

        }
      })
      .then(function (response) {
        const status = JSON.parse(response.status);
        const orderID = JSON.parse(status.data.id);
        
        window.location.replace(`/orders/${status.data.id}/newOrder`);
      });
  }

  render () {
    console.log(this.state);
    return (
      <div>
        <MyHeader />
        <h3>Orders<button onClick={this.goto.bind(this)} type='submit'>New order</button></h3>
        <ul>List of all the orders: {this.renderOrders()}</ul>
      </div>
    );
  }

  viewQrCode (filename) {
    let link = '/myQRcodes/' + filename;
    window.open(link, '_blank');
  }

  renderOrders () {
    console.log(this.state.items);
    const renderOrders = this.state.items.map((order, i) => {
      return (
        <form >
          <li key={order.id}>Created at: {order.created} <button onClick={this.viewQrCode.bind(this, order.qrCodePath)} type='submit'>view QR code</button>
          </li>
        </form>
      );
    });
    return renderOrders;
  }
}
