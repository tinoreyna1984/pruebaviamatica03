INSERT INTO usuarios (username, password, email, rol, fecha_creacion, fecha_aprobacion, creado_por, estado_aprobacion) values ('Administrat0r', '$2a$10$yo7Kk1q39/h8uU.zS/xZQOm6M.0l2qndrdn0p4CRh8vr9KAQ31v3a', 'asistema@mail.com', 'ADMINISTRATOR', '2023-04-25', '2023-04-25', 'Administrat0r', 'APPROVED');
INSERT INTO usuarios (username, password, email, rol, fecha_creacion, fecha_aprobacion, creado_por, estado_aprobacion) values ('Manager00', '$2a$10$yo7Kk1q39/h8uU.zS/xZQOm6M.0l2qndrdn0p4CRh8vr9KAQ31v3a', 'gestorsistema@mail.com', 'MANAGER', '2023-04-25', '2023-04-25', 'Administrat0r', 'APPROVED');
INSERT INTO usuarios (username, password, email, rol, fecha_creacion, fecha_aprobacion, creado_por, estado_aprobacion) values ('Tinoreyna1984', '$2a$10$zFxt31.CfbInZKgPpMSxUeJA2ydl14P72dy6.PeMuOD/BI3MZbpTm', 'treyna@mail.com', 'USER', '2023-04-25', '2023-04-25', 'Administrat0r', 'APPROVED');
INSERT INTO usuarios (username, password, email, rol, fecha_creacion, fecha_aprobacion, creado_por, estado_aprobacion) values ('Usuario01', '$2a$10$zFxt31.CfbInZKgPpMSxUeJA2ydl14P72dy6.PeMuOD/BI3MZbpTm', 'usuario01@mail.com', 'USER', '2023-04-25', '2023-04-25', 'Administrat0r', 'APPROVED');
INSERT INTO usuarios (username, password, email, rol, fecha_creacion, fecha_aprobacion, creado_por, estado_aprobacion) values ('Usuario02', '$2a$10$zFxt31.CfbInZKgPpMSxUeJA2ydl14P72dy6.PeMuOD/BI3MZbpTm', 'usuario02@mail.com', 'USER', '2023-04-25', '2023-04-25', 'Administrat0r', 'APPROVED');
INSERT INTO usuarios (username, password, email, rol, fecha_creacion, fecha_aprobacion, creado_por, estado_aprobacion) values ('Usuario03', '$2a$10$zFxt31.CfbInZKgPpMSxUeJA2ydl14P72dy6.PeMuOD/BI3MZbpTm', 'usuario03@mail.com', 'USER', '2023-04-25', '2023-04-25', 'Administrat0r', 'APPROVED');
INSERT INTO usuarios (username, password, email, rol, fecha_creacion, fecha_aprobacion, creado_por, estado_aprobacion) values ('Usuario04', '$2a$10$zFxt31.CfbInZKgPpMSxUeJA2ydl14P72dy6.PeMuOD/BI3MZbpTm', 'usuario04@mail.com', 'USER', '2023-04-25', '2023-04-25', 'Administrat0r', 'APPROVED');
INSERT INTO usuarios (username, password, email, rol, fecha_creacion, fecha_aprobacion, creado_por, estado_aprobacion) values ('Usuario05', '$2a$10$zFxt31.CfbInZKgPpMSxUeJA2ydl14P72dy6.PeMuOD/BI3MZbpTm', 'usuario05@mail.com', 'USER', '2023-04-25', '2023-04-25', 'Administrat0r', 'APPROVED');
INSERT INTO usuarios (username, password, email, rol, fecha_creacion, fecha_aprobacion, creado_por, estado_aprobacion) values ('Usuario06', '$2a$10$zFxt31.CfbInZKgPpMSxUeJA2ydl14P72dy6.PeMuOD/BI3MZbpTm', 'usuario06@mail.com', 'USER', '2023-04-25', '2023-04-25', 'Administrat0r', 'APPROVED');

INSERT INTO clientes(nombre, apellido, doc_id, email, fono, direccion, referencia_dir) VALUES ('Pepe', 'Castro', '0945267862', 'pcastro@mail.com', '0963587450', 'Mi casa', 'Por ahi');
INSERT INTO clientes(nombre, apellido, doc_id, email, fono, direccion, referencia_dir) VALUES ('Julio', 'Aldana', '0956321489', 'jaldana@mail.com', '0976253985', 'Mi casa', 'Por ahi');
INSERT INTO clientes(nombre, apellido, doc_id, email, fono, direccion, referencia_dir) VALUES ('Jorge', 'Arana', '0982014452', 'jarana@mail.com', '0930258749', 'Mi casa', 'Por ahi');
INSERT INTO clientes(nombre, apellido, doc_id, email, fono, direccion, referencia_dir) VALUES ('Pedro', 'Rado', '0963228947', 'prado@mail.com', '0903589625', 'Mi casa', 'Por ahi');
INSERT INTO clientes(nombre, apellido, doc_id, email, fono, direccion, referencia_dir) VALUES ('John', 'Garrison', '0911125896', 'jgarrison@mail.com', '0914859630', 'Mi casa', 'Por ahi');
INSERT INTO clientes(nombre, apellido, doc_id, email, fono, direccion, referencia_dir) VALUES ('Antonio', 'Pinto', '0978410226', 'apinto@mail.com', '0932144785', 'Mi casa', 'Por ahi');

INSERT INTO cajas(descripcion, active) VALUES('CAJA0001', true);
INSERT INTO cajas(descripcion, active) VALUES('CAJA0002', true);
INSERT INTO cajas(descripcion, active) VALUES('CAJA0003', true);

INSERT INTO dispositivos(nombre) VALUES ('TP-LINK 450Kb');
INSERT INTO dispositivos(nombre) VALUES ('Cisco 440Kb');

INSERT INTO servicios (descripcion, precio, dispositivo_id) VALUES ('Paquete Internet Premium', 30.00,1);
INSERT INTO servicios (descripcion, precio, dispositivo_id) VALUES ('Paquete Internet Normal', 25.00,2);

INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',1);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',1);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',2);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',2);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',3);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',3);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',4);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',4);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',5);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',5);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',6);
INSERT INTO pagos(fecha_pago, cliente_id) VALUES ('2024-06-12',6);

INSERT INTO usuario_caja(usuario_id, caja_id) VALUES (4,1);
INSERT INTO usuario_caja(usuario_id, caja_id) VALUES (5,1);
INSERT INTO usuario_caja(usuario_id, caja_id) VALUES (6,2);
INSERT INTO usuario_caja(usuario_id, caja_id) VALUES (7,2);
INSERT INTO usuario_caja(usuario_id, caja_id) VALUES (8,3);
INSERT INTO usuario_caja(usuario_id, caja_id) VALUES (9,3);

INSERT INTO atenciones(tipo_atencion, descripcion, estado_atencion, caja_id, cliente_id) VALUES ('AC0001', 'AC0001 - Contrato de servicio', 'FINALIZADO',1,1);
INSERT INTO atenciones(tipo_atencion, descripcion, estado_atencion, caja_id, cliente_id) VALUES ('PS0001', 'PS0001 - Pago de servicios', 'FINALIZADO',1,2);
INSERT INTO atenciones(tipo_atencion, descripcion, estado_atencion, caja_id, cliente_id) VALUES ('AC0001', 'AC0001 - Contrato de servicio', 'FINALIZADO',2,3);
INSERT INTO atenciones(tipo_atencion, descripcion, estado_atencion, caja_id, cliente_id) VALUES ('PS0001', 'PS0001 - Pago de servicios', 'FINALIZADO',2,4);
INSERT INTO atenciones(tipo_atencion, descripcion, estado_atencion, caja_id, cliente_id) VALUES ('AC0001', 'AC0001 - Contrato de servicio', 'FINALIZADO',3,5);
INSERT INTO atenciones(tipo_atencion, descripcion, estado_atencion, caja_id, cliente_id) VALUES ('PS0001', 'PS0001 - Pago de servicios', 'FINALIZADO',3,6);



INSERT INTO contratos(fecha_inicio_contrato, fecha_fin_contrato, estado_contrato, forma_pago, servicio_id, cliente_id) VALUES ('2023-05-12', '2024-05-12', 'VIG', 'TARJETA_DE_PAGO',1,1);
INSERT INTO contratos(fecha_inicio_contrato, fecha_fin_contrato, estado_contrato, forma_pago, servicio_id, cliente_id) VALUES ('2023-05-12', '2024-05-12', 'VIG', 'TARJETA_DE_PAGO',1,2);
INSERT INTO contratos(fecha_inicio_contrato, fecha_fin_contrato, estado_contrato, forma_pago, servicio_id, cliente_id) VALUES ('2023-05-12', '2024-05-12', 'VIG', 'TARJETA_DE_PAGO',1,3);
INSERT INTO contratos(fecha_inicio_contrato, fecha_fin_contrato, estado_contrato, forma_pago, servicio_id, cliente_id) VALUES ('2023-05-12', '2024-05-12', 'VIG', 'TARJETA_DE_PAGO',2,4);
INSERT INTO contratos(fecha_inicio_contrato, fecha_fin_contrato, estado_contrato, forma_pago, servicio_id, cliente_id) VALUES ('2023-05-12', '2024-05-12', 'VIG', 'TARJETA_DE_PAGO',2,5);
INSERT INTO contratos(fecha_inicio_contrato, fecha_fin_contrato, estado_contrato, forma_pago, servicio_id, cliente_id) VALUES ('2023-05-12', '2024-05-12', 'VIG', 'TARJETA_DE_PAGO',2,6);

