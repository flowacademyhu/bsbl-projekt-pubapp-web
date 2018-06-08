import React from 'react';
import {NavLink} from 'react-router-dom';
export default class MyHeader extends React.Component {
  render () {
    return (
      <div>
        <header>
          <ul>
            <NavLink to='/products'>Products</NavLink>
            <NavLink to='/achievements'>Achivements</NavLink>
            <NavLink to='/orders'>Orders</NavLink>
            <NavLink to='/users'>Users</NavLink>
            <NavLink to='/logout'>Logout</NavLink>
          </ul>
        </header>      </div>
    );
  }
}
