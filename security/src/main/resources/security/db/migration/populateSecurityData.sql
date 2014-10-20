INSERT INTO users (username, password, enabled)
VALUES
('admin', 'admin', TRUE),
('user', 'user', TRUE);

INSERT INTO authorities(authority)
VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO users_auth
VALUES
(1, 1),
(2, 2);