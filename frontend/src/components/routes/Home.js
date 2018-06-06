import React from 'react';
import {Jumbotron} from 'react-bootstrap';
import {Link} from 'react-router-dom';
import MyHeader from './../header/header';

export default class Home extends React.Component {
  render () {
    return (
      <div>
        <MyHeader />
        <Jumbotron>
          <h1>Welcome </h1>
          <h2> to the admin home page </h2>
          <Link to='/registration'>click here to create new admin access</Link>
        </Jumbotron>
      </div>
    );
  }
}
