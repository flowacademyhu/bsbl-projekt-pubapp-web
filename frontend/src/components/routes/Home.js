import React from 'react';
import {Jumbotron} from 'react-bootstrap';
import {Link} from 'react-router-dom';

export default class Home extends React.Component {
  render () {
    return (
      <div>
        <Jumbotron>
          <h1>Welcome </h1>
<<<<<<< HEAD
          <h2> Orgiginal PubAPP </h2>

          <ul>
            <li> gooogle play  link <a href='asdasdasd'> click me </a>  </li>
            <li> app store link <a href='asdasdasd'>click me </a> </li>
          </ul>
          <Link to='/login' >Sign in</Link>
          <Link to='/Registration' >Registration</Link>
=======
          <h2> to the admin home page </h2>
          <Link to='/registration'>click here to creat new admin access</Link>
>>>>>>> eb2d9c5148901f11e2a794cf32b3aa647954a739
        </Jumbotron>
      </div>
    );
  }
}
