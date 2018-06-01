import React from 'react';
import {Jumbotron} from 'react-bootstrap';
import {Link} from 'react-router-dom';

export default class Home extends React.Component {
  render () {
    return (
      <div>
        <Jumbotron>
          <h1>Welcome </h1>
          <h2> to the admin home page </h2>
          <Link to='/registration'>click here to creat new admin access</Link>
        </Jumbotron>
      </div>
    );
  }
}
