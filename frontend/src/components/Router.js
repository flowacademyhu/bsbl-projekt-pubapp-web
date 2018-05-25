import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import {ModalFooter} from 'react-bootstrap';

// Components
import Login from './routes/Login';
import Home from './routes/Home';
import Achivement from './routes/Achivements';
import Orders from './routes/Orders';
import Products from './routes/Products';
import Users from './routes/Users';
import Registration from './routes/Registration';
import NotFound from './routes/NotFound';
 
const Router = () => (
  <BrowserRouter>
    <div>
      <Switch>
        <Route path='/registration' component={Registration} />
        <Route path='/login' component={Login} />
        <Route path='/achivement' component={Achivement} />
        <Route path='/order' component={Orders} />
        <Route path='/product' component={Products} />
        <Route path='/user' component={Users} />
        <Route path='/' exact component={Home} />
        <Route component={NotFound} />
      </Switch>
      <ModalFooter>
        <p> yoloright 2018 BSBL-hadada</p>
      </ModalFooter>
    </div>
  </BrowserRouter>
);
export default Router;
