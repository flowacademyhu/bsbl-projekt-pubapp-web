import ListGroupItem from 'react-bootstrap/lib/ListGroupItem';
import React from 'react';

class TodoItem extends React.Component {
  constructor (props) {
    super(props);
    this.state = {isDone: false};
  }

  delete () {
    this.toggleDisabled();
    this.props.delete(this.props.toDo.label);
  }

  toggleDisabled () {
    this.setState({isDone: !this.state.isDone});
  }

  render () {
    return (
      <ListGroupItem style={{color: (this.state.isDone ? 'grey' : '')}}>
        <input id='box' type='checkbox' disabled={this.state.isDone} onClick={this.delete.bind(this)} />
        <span className='checkmark' />
        {this.props.toDo.label}
      </ListGroupItem>
    );
  }
}
export default TodoItem;
