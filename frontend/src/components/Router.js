import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import {ModalFooter} from 'react-bootstrap';

// Components
import Login from './routes/Login';
import Home from './routes/Home.js';
import Achivement from './routes/Achivements';
import Orders from './routes/Orders';
import Products from './routes/Products';
import Users from './routes/Users';
import Registration from './routes/Registration';
import NotFound from './routes/NotFound';
<<<<<<< HEAD
import MyForm from './routes/Myform';
import AchivementsLine from './routes/Achivements/AchivementsLine';
import TodoIndex from './routes/todoIndex';
import NewAchivement from './routes/CreateAchivement/CreateAchivementForm';

=======
>>>>>>> eb2d9c5148901f11e2a794cf32b3aa647954a739
 
const Router = () => (
  <BrowserRouter>
    <div>
      <Switch>
<<<<<<< HEAD
        <Route path='/newAchivement' component={NewAchivement} />
        <Route path='/myfrom' component={MyForm} />
=======
>>>>>>> eb2d9c5148901f11e2a794cf32b3aa647954a739
        <Route path='/registration' component={Registration} />
        <Route exact path='/' component={Login} />
        <Route path='/achivement' component={Achivement} />
        <Route path='/order' component={Orders} />
        <Route path='/product' component={Products} />
        <Route path='/user' component={Users} />
<<<<<<< HEAD
        <Route path='/todos' component={TodoIndex} />
        <Route path='/' exact component={Home} />
=======
        <Route path='/home' component={Home} />
>>>>>>> eb2d9c5148901f11e2a794cf32b3aa647954a739
        <Route component={NotFound} />
      </Switch>
      <ModalFooter>
        <p> yoloright 2018 BSBL-hadada</p>
      </ModalFooter>
    </div>
  </BrowserRouter>
);
export default Router;
