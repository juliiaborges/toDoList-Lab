import React, { useState } from 'react';

const TaskForm = ({ onAddTask }) => {
    const [description, setDescription] = useState('');
    const [type, setType] = useState('DATA');
    const [priority, setPriority] = useState('ALTA');
    const [dueDate, setDueDate] = useState('');
    const [dueDays, setDueDays] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const newTask = {
            description,
            type,
            priority,
            dueDate: type === 'DATA' ? dueDate : null,
            dueDays: type === 'PRAZO' ? dueDays : null,
            completed: false,
        };
        onAddTask(newTask);
        // Reset form fields
        setDescription('');
        setType('DATA');
        setPriority('ALTA');
        setDueDate('');
        setDueDays('');
    };

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Nome da Tarefa:
                <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} required />
            </label>
            <label>
                Seu tipo:
                <select value={type} onChange={(e) => setType(e.target.value)} required>
                    <option value="DATA">Data</option>
                    <option value="PRAZO">Prazo</option>
                    <option value="LIVRE">Livre</option>
                </select>
            </label>
            {type === 'DATA' && (
                <label>
                    Data:
                    <input type="date" value={dueDate} onChange={(e) => setDueDate(e.target.value)} required />
                </label>
            )}
            {type === 'PRAZO' && (
                <label>
                    Prazo (dias):
                    <input type="number" value={dueDays} onChange={(e) => setDueDays(e.target.value)} required />
                </label>
            )}
            <label>
                Prioridade:
                <select value={priority} onChange={(e) => setPriority(e.target.value)} required>
                    <option value="ALTA">Alta</option>
                    <option value="MEDIA">Média</option>
                    <option value="BAIXA">Baixa</option>
                </select>
            </label>
            <button type="submit">Adicionar Tarefa</button>
        </form>
    );
};

export default TaskForm;
