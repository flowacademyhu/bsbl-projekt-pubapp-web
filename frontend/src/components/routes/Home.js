import React from 'react';
import {Jumbotron} from 'react-bootstrap';

export default class Home extends React.Component {
  render () {
    return (
      <div>
        <Jumbotron>
          <h1>Welcome </h1>
          <h2> to the admin home page </h2>


        </Jumbotron>
      </div>
    );
  }
}
