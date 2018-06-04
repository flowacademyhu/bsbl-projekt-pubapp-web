import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import {ModalFooter} from 'react-bootstrap';

// Components
import Login from './routes/Login';
import Home from './routes/Home.js';
import Achievement from './routes/Achievements';
import Orders from './routes/Orders';
import Product from './routes/Products';
import NewProduct from './routes/Products/ProductItem';
import Users from './routes/Users';
import Registration from './routes/Registration';
import NotFound from './routes/NotFound';
//import AchievementsLine from './routes/Achievements/AchievementsLine';
import NewAchievement from './routes/CreateAchievement/CreateAchievementForm';

const Router = () => (
  <BrowserRouter>
    <div>
      <Switch>
        <Route path='/newAchievement' component={NewAchievement} />
        <Route path='/registration' component={Registration} />
        <Route exact path='/' component={Login} />
        <Route path='/achievements' component={Achievement} />
        <Route path='/orders' component={Orders} />
        <Route path='/newProduct' component={NewProduct} />
        <Route path='/products' component={Product} />
        <Route path='/users' component={Users} />
        <Route path='/home' component={Home} />
        <Route component={NotFound} />
      </Switch>
      <ModalFooter>
        <p> yoloright 2018 BSBL-hadada</p>
      </ModalFooter>
    </div>
  </BrowserRouter>
);
export default Router;
