INSERT INTO users (id, name, email, password, role, enabled) VALUES (1, 'Admin', 'admin@example.com', '$2a$10$hashedpassword', 'ADMIN', true) ON CONFLICT DO NOTHING;
