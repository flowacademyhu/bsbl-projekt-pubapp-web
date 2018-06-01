import React from 'react';
import MyHeader from './../header/header';
import axios from 'axios';
export default class Products extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      alist: ''
    };
  }
  async getlist () {
    await axios.get('http://127.0.0.1:8080/products')
      .then(response => {
        const list = 'res.data.blabla';
        this.setState({ alist: list });
      });
  }
  //  golist () {  }
  _renderCountries (country, index) {
    return <li key={index}>{country.name} - {country.subregion}</li>;
  }

  render () {
    const { countries } = this.state;
    return (
      <div>
        <div>
          <MyHeader />
        </div>
        <h1>LIST OF COUNTRIES:</h1>
        <ul>
          {
            countries
              ? countries.map(this._renderCountries)
              : 'no data to display'
          }
        </ul>      </div>
    );
  }
}
