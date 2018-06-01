import React from 'react';
import TodoList from './todoList';
import CreateItem from './createItem';

class TodoIndex extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      toDos: [
        { label: 'First task' },
        { label: 'Second task' },
        { label: 'Third task' },
        { label: 'Fourth task' },
        { label: 'Fifth task' }
      ]
    };
  }

  submit (inputValue) {
    let toDosArray = this.state.toDos;
    toDosArray.push({ label: inputValue });
    this.setState({ toDos: toDosArray });
    console.log(inputValue);
  }

  delete (label) {
    setTimeout(() => {
      let toDosArray = this.state.toDos;
      let i = 0;
      while (i < toDosArray.length && label !== toDosArray[i].label) {
        i++;
      }
      if (i < toDosArray.length) {
        toDosArray.splice(i, 1);
        this.setState({ toDos: toDosArray });
      }
    }, 1500);
  }

  render () {
    return (
      <div>
        <TodoList toDos={this.state.toDos} delete={this.delete.bind(this)} />
        <CreateItem submit={this.submit.bind(this)} />
       
      </div>
    );
  }
}

export default TodoIndex;
