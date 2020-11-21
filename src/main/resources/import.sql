INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (1,'Damian','Galvan','damian@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (2,'Jose','Torrez','jose@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (3,'irving','Galvan','damian@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (4,'Carlos','Torrez','jose@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (5,'Jazmin','Galvan','damian@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (6,'Robin','Torrez','jose@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (7,'Mateo','Galvan','damian@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (8,'Talina','Torrez','jose@gmil.com','2020-08-28','');

INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (9,'Damian','Galvan','damian@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (10,'Jose','Torrez','jose@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (11,'irving','Galvan','damian@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (12,'Carlos','Torrez','jose@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (13,'Jazmin','Galvan','damian@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (14,'Robin','Torrez','jose@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (15,'Mateo','Galvan','damian@gmil.com','2020-08-28','');
INSERT INTO clientes (id,nombre,apellido,email,create_at,foto) values (16,'Talina','Torrez','jose@gmil.com','2020-08-28','');

INSERT INTO productos (nombre,precio,create_at) values  ('Celular m20',4999,NOW()); 
INSERT INTO productos (nombre,precio,create_at) values  ('Celular m30',6999,NOW()); 
INSERT INTO productos (nombre,precio,create_at) values  ('Celular m50',7999,NOW()); 
INSERT INTO productos (nombre,precio,create_at) values  ('Sony Camara',2999,NOW()); 
INSERT INTO productos (nombre,precio,create_at) values  ('Pantalla Sony',9999,NOW()); 

INSERT INTO facturas(create_at,descripcion,cliente_id) values (NOW(),"Factura del cliente...",1);
INSERT INTO facturas_items(cantidad,factura_id,producto_id) values (2,1,1);

INSERT INTO users (username,password,enabled) values ("admin","$2a$10$3orGV0sTPlBOxRquI00Z5OGG2e0tLCQQV/Kf4oZYlXG.YC.Xz4Fw6",1);
INSERT INTO users (username,password,enabled) values ("damian","$2a$10$wSEWMz04Qr3uZoZJaNH.qeQABFb2ApI71VgYxXbEeHaGfGrwXM1/q",1);

INSERT INTO authorities (user_id,authority) values (1,'ROLE_ADMIN');
INSERT INTO authorities (user_id,authority) values (1,'ROLE_USER');
INSERT INTO authorities (user_id,authority) values (2,'ROLE_USER');