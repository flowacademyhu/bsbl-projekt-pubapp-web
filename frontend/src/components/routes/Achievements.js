import React from 'react';
import axios from 'axios';
import MyHeader from './../header/header';
import AchievementDetails from './AchievementDetails';
import CreateAchievementConditionForm from './Achievements/CreateAchievementCondition/CreateAchievementConditionForm';

export default class Products extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [],
      products: []
    };
  }

  componentDidMount() {
    var config = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin': '*',
      crossdomain: true
    };

    axios
      .get('http://127.0.0.1:8080/achievements', { headers: config })
      .then(({ data }) => {
        console.log(data);
        this.setState(
          { items: data }
        );
      })
      .catch(function (error) {
        if (error.response) {
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }
        console.log(error.config);
        console.log(error);
      });

    axios
      .get('http://127.0.0.1:8080/products', { headers: config })
      .then(({ data }) => {
        console.log(data);
        this.setState(
          { products: data }
        );
      })
      .catch(function (error) {
        if (error.response) {
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }
        console.log(error.config);
        console.log(error);
      });
  }

  goto () {
    window.location.replace('/newAchievement');
  }

  render () {
    console.log(this.state);
    return (
      <div>
        <MyHeader />
        <h3>Achievements<button onClick={this.goto.bind(this)} type='submit'>Add new achievement</button></h3>
        <ul>List of all the achievements: {this.renderAchievements()}</ul>
      </div>
    );
  }

  renderAchievements () {
    console.log(this.state.items);
    var products = this.state.products;
    console.log(products);
    const renderAchievements = this.state.items.map(function (achievement, i) {
      return <li key={achievement.id}> Name: {achievement.name}, Description: {achievement.description}, xpValue: {achievement.xpValue}, expiration: {achievement.expiration}
        <AchievementDetails id={achievement.id} />
        <CreateAchievementConditionForm id={achievement.id} />
      </li>;
    });

    return renderAchievements;
  }
}
