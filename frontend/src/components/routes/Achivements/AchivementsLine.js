
import React from 'react';
import Button from 'react-bootstrap/lib/Button';

class AchivementsLine extends React.Component {
  constructor (props) {
    super(props);
    this.state = {inputValue: ''};
  }

  submit () {
    console.log('Submit successful!', this.state.inputValue);
    this.props.submit(this.state.inputValue);
    this.setState({inputValue: ''});
  }

  changeInputValue (element) {
    this.setState({inputValue: element.target.value});
  }

  render () {
    return (
      <form>
        <input className='my-input-field' type='text' placeholder='Add to your list' value={this.state.inputValue} onChange={this.changeInputValue.bind(this)} />
        <Button bsStyle='warning' bsSize='large' type='button' onClick={this.submit.bind(this)}>Add</Button>
        <br />
      </form>
    );
  }
}

export default AchivementsLine;
