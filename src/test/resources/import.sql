DELETE FROM todos;

INSERT INTO todos (name, description, done, priority, created_at, updated_at) VALUES
('todo 1', 'desc todo 1', false, 1, NOW(), NOW()),
('todo 2', 'desc todo 2', false, 1, NOW(), NOW()),
('todo 3', 'desc todo 3', false, 1, NOW(), NOW()),
('todo 4', 'desc todo 4', false, 1, NOW(), NOW()),
('todo 5', 'desc todo 5', false, 1, NOW(), NOW());

