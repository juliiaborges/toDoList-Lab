import React from 'react';

const Task = ({ task, onComplete, onDelete, completed }) => {
    return (
        <div className={`task ${completed ? 'completed' : ''}`}>
            <span>{task.description}</span>
            <span>{task.dueDate || `${task.dueDays} dias`}</span>
            <div className="task-buttons">
                {!completed && <button className="complete" onClick={onComplete}>Concluir</button>}
                <button className="delete" onClick={onDelete}>Excluir</button>
            </div>
        </div>
    );
};

export default Task;
