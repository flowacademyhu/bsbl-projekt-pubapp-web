import React from 'react';
import {NavLink} from 'react-router-dom';
export default class MyHeader extends React.Component {
  render () {
    return (
      <div>
        <header>
          <NavLink to='/products'> Products</NavLink>
          <NavLink to='/achievements'> Achivements</NavLink>
          <NavLink to='/orders'>Orders</NavLink>
          <NavLink to='/users'>Users</NavLink>
        </header>      </div>
    );
  }
}
