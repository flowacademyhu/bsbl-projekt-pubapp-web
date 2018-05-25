import React from 'react';
import {Link} from 'react-router-dom';
import {Jumbotron} from 'react-bootstrap';

export default class Home extends React.Component {
  render () {
    return (
      <div>
        <Jumbotron>
          <h1>Welcome </h1>
          <h2> Orgiginal PubAPP </h2>

          <ul>
            <li> gooogle play  link <a href='asdasdasd'> click me </a>  </li>
            <li> app store link <a href='asdasdasd'>click me </a> </li>
          </ul>
          <Link to='/login' >Sign in</Link>
          <Link to='/registration' >Registration</Link>
        </Jumbotron>
      </div>
    );
  }
}
