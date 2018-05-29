import React from 'react';
import AchivementsLine from './Achivements/AchivementsLine';

class Achivements extends React.Component {
  submit (inputValue) {
    console.log(inputValue);
    this.props.submit(inputValue);
  }

  render () {
    return (
      <div className='createItemWrapper'>
        <AchivementsLine submit={this.submit.bind(this)} />
      </div>
    );
  }
}
export default Achivements;
