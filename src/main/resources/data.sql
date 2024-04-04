DROP TABLE IF EXISTS task;
CREATE TABLE task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(250) NOT NULL,
    completed BOOLEAN
);
INSERT INTO task (description, completed) VALUES
    ('Primeira tarefa', false),
    ('Segunda tarefa', false),
    ('Terceira tarefa', false);

