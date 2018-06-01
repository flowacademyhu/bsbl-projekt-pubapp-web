import React from 'react';
import {NavLink} from 'react-router-dom';
export default class MyHeader extends React.Component {
  render () {
    return (
      <div>
        <header>
          <ul>
            <li><NavLink to='/products'> products</NavLink></li>
            <li><NavLink to='/achievements'> achivements</NavLink></li>
            <li><NavLink to='/orders'>orders</NavLink></li>
            <li><NavLink to='/users'>users</NavLink></li>
          </ul>
        </header>      </div>
    );
  }
}
