import React, { useState } from 'react';
import TaskForm from './TaskForm';
import Task from './Task';

const TodoWrapper = () => {
    const [tasks, setTasks] = useState([]);
    const [completedTasks, setCompletedTasks] = useState([]);
    const [showForm, setShowForm] = useState(false);

    const addTask = (task) => {
        setTasks([...tasks, task]);
        setShowForm(false);
    };

    const completeTask = (index) => {
        const newTasks = [...tasks];
        const [completedTask] = newTasks.splice(index, 1);
        setCompletedTasks([...completedTasks, completedTask]);
        setTasks(newTasks);
    };

    const deleteTask = (index, isCompleted) => {
        if (isCompleted) {
            setCompletedTasks(completedTasks.filter((_, i) => i !== index));
        } else {
            setTasks(tasks.filter((_, i) => i !== index));
        }
    };

    return (
        <div className="todo-wrapper">
            <div className="pending-tasks">
                <h2>Tarefas Pendentes:</h2>
                {tasks.map((task, index) => (
                    <Task key={index} task={task} onComplete={() => completeTask(index)} onDelete={() => deleteTask(index, false)} />
                ))}
                <button onClick={() => setShowForm(true)}>+</button>
            </div>
            <div className="completed-tasks">
                <h2>Tarefas Concluídas:</h2>
                {completedTasks.map((task, index) => (
                    <Task key={index} task={task} onDelete={() => deleteTask(index, true)} completed />
                ))}
            </div>
            {showForm && <TaskForm onAddTask={addTask} />}
        </div>
    );
};

export default TodoWrapper;
