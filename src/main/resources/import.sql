INSERT INTO customers (id,name,last_name,email,create_at,photo) values (1,'Damian','Galvan','damian@gmil.com','2020-08-28','');
INSERT INTO customers (id,name,last_name,email,create_at,photo) values (2,'Jose','Torrez','jose@gmil.com','2020-08-28','');
INSERT INTO customers (id,name,last_name,email,create_at,photo) values (3,'irving','Galvan','irving@gmil.com','2020-08-28','');
INSERT INTO customers (id,name,last_name,email,create_at,photo) values (4,'Carlos','Torrez','carlos@gmil.com','2020-08-28','');
INSERT INTO customers (id,name,last_name,email,create_at,photo) values (5,'Jazmin','Galvan','jazmin@gmil.com','2020-08-28','');
INSERT INTO customers (id,name,last_name,email,create_at,photo) values (6,'Robin','Torrez','robin@gmil.com','2020-08-28','');
INSERT INTO customers (id,name,last_name,email,create_at,photo) values (7,'Mateo','Galvan','mateo@gmil.com','2020-08-28','');
INSERT INTO customers (id,name,last_name,email,create_at,photo) values (8,'Talina','Torrez','talina@gmil.com','2020-08-28','');

INSERT INTO products (product_name,price,create_at) values  ('Celular m20',4999,NOW());
INSERT INTO products (product_name,price,create_at) values  ('Celular m30',6999,NOW());
INSERT INTO products (product_name,price,create_at) values  ('Celular m50',7999,NOW());
INSERT INTO products (product_name,price,create_at) values  ('Sony Camara',2999,NOW());
INSERT INTO products (product_name,price,create_at) values  ('Pantalla Sony',9999,NOW());

INSERT INTO invoices(create_at,description,customer_id) values (NOW(),"Factura del customer...",1);
INSERT INTO invoice_items(quantity,invoice_id,producto_id) values (2,1,1);

INSERT INTO users (username,password,enabled) values ("admin","$2a$10$3orGV0sTPlBOxRquI00Z5OGG2e0tLCQQV/Kf4oZYlXG.YC.Xz4Fw6",1);
INSERT INTO users (username,password,enabled) values ("damian","$2a$10$wSEWMz04Qr3uZoZJaNH.qeQABFb2ApI71VgYxXbEeHaGfGrwXM1/q",1);

INSERT INTO authorities (user_id,authority) values (1,'ROLE_ADMIN');
INSERT INTO authorities (user_id,authority) values (1,'ROLE_USER');
INSERT INTO authorities (user_id,authority) values (2,'ROLE_USER');