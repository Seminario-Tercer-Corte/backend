insert into roles (id, name) values(1,'Administrador');
insert into roles (id, name) values(2,'Usuario');

insert into users (id, name, picture, username, password)
values(1, 'test', 'hola.png', 'test123', '$2a$10$4ue7RWHuCCEetR8u4sHNtOWgGyE/3EnI9YKugtbqHtKaF0jrYtZkO');

insert into users_roles (id_user, id_role) values (1, 2);