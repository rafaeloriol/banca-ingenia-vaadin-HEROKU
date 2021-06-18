-- CATEGORIAS ---
INSERT INTO categorias (tipo_categoria) VALUES
	 ('Restaurantes'),
	 ('Combustible'),
	 ('Viajes'),
	 ('Hoteles'),
	 ('Compras'),
	 ('Regalos'),
	 ('Varios'),
	 ('Domiciliacion'),
	 ('Electrónica'),
	 ('Préstamo');


-- CUENTAS --
INSERT INTO cuentas (entidad,numero_cuenta,saldo,tipo_cuenta) VALUES
	 ('BBVA','23984238947',2150.0,'Corriente'),
	 ('BBVA','65165778947',1000.0,'Corriente'),
	 ('CAIXA','16116515947',2000.0,'Ahorro'),
	 ('BANCO SANTANDER','3047103742',10000.0,'Ahorro'),
	 ('UNICAJA','4901749274',12000.0,'Corriente');


--    USUARIO       PASSWORD
--      andres          a
--      oscar           o
--      umberto         u
INSERT INTO usuarios (activation_code,active,direccion,email,password,password_hash,password_salt,`role`,telefono,username, full_name) VALUES
	 ('BEZ7104HEyeUElmmcA7haryEy1PrNG7w',0,NULL,NULL,NULL,'14afb45e0a83eb04426edbdf7fd9d083709e133b','𥕌𢠶ﷁ𖭨𣥷𪼪🠂𭋾ஔ䪪𭌇잡𪀚𑘈㻹撅搀䑵𨰟众맠',0,NULL,'umberto', 'Umberto García'),
	 ('Z7ubteBnAmcEBHWrDFpF58YOAd8yv8VE',0,NULL,NULL,NULL,'0cdba6078e2f91627da574fd069ba98bb11b0c30','𪱛뗏𥢱𘄦𑌪𐐆𭗻䤽𩱋鏯멫剱𫉾滟𘂶⣿뉝𭳄𡡸﹩䓙',1,NULL,'andres', 'Andres Segovia');

INSERT INTO usuarios ( activation_code, active, direccion, email, password, password_hash, password_salt, `role`, telefono, username, full_name) VALUES
    ('ei7IO1mY8cAWpktJl0Po55xqW2Gm02aY', 0, NULL, NULL, NULL, '40525521ccf7b57f64856e277ef5ccd5a8224bc3', '蘒𩻵𠓏𑀝å𢍍𰌳葻𗳠坻𰵊𭸸𬌐ꀃ𡨇𗪚ꢖ𨼣𝀑', 0, NULL, 'oscar', 'Oscar Pérez');


-- CUENTAS-USUARIOS --
INSERT INTO cuentas_users (cuenta_id,user_id) VALUES
	 (1,1),
	 (1,2),
	 (4,2),
	 (2,3),
	 (3,3);

-- TARJETAS --
INSERT INTO tarjetas (cvv,fecha_caducidad,limite_maximo,numero_tarjeta,tipo_tarjeta,id_cuenta) VALUES
	 (356,'2022-02-16',500.0,'15165165','Débito',1),
	 (586,'2022-02-13',300.0,'56161665','Débito',1),
	 (834,'2022-04-03',500.0,'23432433','Crédito',2),
	 (443,'2022-02-28',500.0,'89789873','Débito',3),
	 (220,'2022-04-10',600.0,'43027100','Crédito',5),
	 (117,'2022-11-24',1000.0,'21210741','Débito',3),
	 (205,'2022-11-04',600.0,'67302742','Crédito',2),
	 (992,'2022-10-02',500.0,'74210723','Débito',4),
	 (992,'2022-10-02',500.0,'94270041','Débito',4);

-- MOVIMIENTOS --
	 INSERT INTO training2.movimientos (cantidad,concepto,fecha_operacion,fecha_valor,num_tarjeta,saldo_actual,id_categoria,id_cuenta) VALUES
     	 (50.0,'ingreso','2021-02-01','2021-02-01','',50.0,1,1),
     	 (50.0,'ingreso','2021-02-05','2021-02-05','',100.0,1,1),
     	 (1500.0,'nomina','2021-02-16','2021-02-16','',1600.0,7,1),
     	 (-100.0,'Mercadona','2021-02-20','2021-02-20','56161665',1500.0,5,1),
     	 (-50.0,'Electricidad','2021-02-20','2021-02-20','56161665',1450.0,8,1),
     	 (-50.0,'Emasa Agua','2021-02-21','2021-02-21','15165165',1400.0,8,1),
     	 (-100.0,'Hotel Florida','2021-02-22','2021-02-22','15165165',1350.0,4,1),
     	 (-100.0,'El corte ingles','2021-02-23','2021-02-23','15165165',1250.0,6,1),
     	 (-100.0,'Hotel Marina','2021-02-22','2021-02-22','56161665',1350.0,4,1),
     	 (-50.0,'Repsol','2021-02-23','2021-02-23','56161665',1300.0,2,1);
     INSERT INTO training2.movimientos (cantidad,concepto,fecha_operacion,fecha_valor,num_tarjeta,saldo_actual,id_categoria,id_cuenta) VALUES
     	 (-200.0,'Mediamarkt','2021-02-24','2021-02-24','15165165',1050.0,6,1),
     	 (-50.0,'Movistar','2021-02-25','2021-02-25','56161665',1350.0,8,1),
     	 (50.0,'ingreso','2021-03-01','2021-03-01','',1400.0,1,1),
     	 (50.0,'ingreso','2021-03-05','2021-03-05','',1450.0,1,1),
     	 (1500.0,'nomina','2021-03-16','2021-03-16','',2950.0,7,1),
     	 (-100.0,'Mercadona','2021-03-20','2021-03-20','56161665',2850.0,5,1),
     	 (-50.0,'Electricidad','2021-03-20','2021-03-20','56161665',2800.0,8,1),
     	 (-50.0,'Emasa Agua','2021-03-21','2021-03-21','15165165',2750.0,8,1),
     	 (-100.0,'Hotel Florida','2021-03-22','2021-03-22','15165165',2650.0,4,1),
     	 (-100.0,'El corte ingles','2021-03-23','2021-03-23','56161665',2550.0,6,1);
     INSERT INTO training2.movimientos (cantidad,concepto,fecha_operacion,fecha_valor,num_tarjeta,saldo_actual,id_categoria,id_cuenta) VALUES
     	 (-100.0,'Hotel Marina','2021-03-22','2021-03-22','15165165',2450.0,4,1),
     	 (-50.0,'Repsol','2021-03-23','2021-03-23','15165165',2400.0,2,1),
     	 (-200.0,'Mediamarkt','2021-03-24','2021-03-24','56161665',2200.0,6,1),
     	 (-50.0,'Movistar','2021-03-25','2021-03-25','56161665',2150.0,8,1),
     	 (-50.0,'Repsol','2021-04-23','2021-04-23','23432433',2400.0,2,2),
     	 (-200.0,'Mediamarkt','2021-04-24','2021-04-24','67302742',2200.0,6,2),
     	 (-50.0,'Movistar','2021-04-25','2021-04-25','30432433',2150.0,8,2),
     	 (-50.0,'Carrefour','2021-04-26','2021-04-26','67302742',2100.0,9,2),
     	 (-50.0,'Movistar','2021-04-27','2021-04-27','67302742',2050.0,7,2),
     	 (-50.0,'Mercadona','2021-04-28','2021-04-28','30432433',2000.0,5,2);
     INSERT INTO training2.movimientos (cantidad,concepto,fecha_operacion,fecha_valor,num_tarjeta,saldo_actual,id_categoria,id_cuenta) VALUES
     	 (-50.0,'El Corte Ingles','2021-04-29','2021-04-29','67302742',1950.0,3,2),
     	 (1050.0,'Nómina','2021-04-30','2021-04-30','',3000.0,7,2),
     	 (-50.0,'Hotel Florida','2021-05-01','2021-05-01','67302742',2950.0,4,2),
     	 (-50.0,'Repsol','2021-05-02','2021-05-02','67302742',2900.0,2,2),
     	 (-50.0,'Mediamarkt','2021-05-03','2021-05-03','30432433',2850.0,9,2),
     	 (-50.0,'Emasa Agua','2021-05-04','2021-05-04','67302742',2800.0,8,2),
     	 (-50.0,'Restaurante Paca','2021-05-05','2021-05-05','30432433',2750.0,1,2),
     	 (-50.0,'Dentista','2021-05-06','2021-05-06','30432433',2700.0,7,2),
     	 (-50.0,'ITV','2021-05-07','2021-05-07','67302742',2650.0,7,2),
     	 (-50.0,'Repsol','2021-04-23','2021-04-23','89789873',2600.0,2,3);
     INSERT INTO training2.movimientos (cantidad,concepto,fecha_operacion,fecha_valor,num_tarjeta,saldo_actual,id_categoria,id_cuenta) VALUES
     	 (-200.0,'Mediamarkt','2021-04-24','2021-04-24','21210741',2400.0,6,3),
     	 (-50.0,'Movistar','2021-04-25','2021-04-25','89789873',2350.0,8,3),
     	 (-50.0,'Carrefour','2021-04-26','2021-04-26','89789873',2300.0,9,3),
     	 (-50.0,'Movistar','2021-04-27','2021-04-27','21210741',2250.0,7,3),
     	 (-50.0,'Mercadona','2021-04-28','2021-04-28','89789873',2200.0,5,3),
     	 (-50.0,'El Corte Ingles','2021-04-29','2021-04-29','21210741',2150.0,3,3),
     	 (1050.0,'Nómina','2021-04-30','2021-04-30','',3200.0,7,3),
     	 (-50.0,'Hotel Florida','2021-05-01','2021-05-01','21210741',3150.0,4,3),
     	 (-50.0,'Repsol','2021-05-02','2021-05-02','89789873',3100.0,2,3),
     	 (-50.0,'Mediamarkt','2021-05-03','2021-05-03','21210741',3050.0,9,3);
     INSERT INTO training2.movimientos (cantidad,concepto,fecha_operacion,fecha_valor,num_tarjeta,saldo_actual,id_categoria,id_cuenta) VALUES
     	 (-50.0,'Emasa Agua','2021-05-04','2021-05-04','89789873',3000.0,8,3),
     	 (-50.0,'Restaurante Paca','2021-05-05','2021-05-05','89789873',2950.0,1,3),
     	 (-50.0,'Dentista','2021-05-06','2021-05-06','21210741',2900.0,7,3),
     	 (-50.0,'ITV','2021-05-07','2021-05-07','89789873',2850.0,7,3),
     	 (-50.0,'Repsol','2021-04-23','2021-04-23','74210723',2800.0,2,4),
     	 (-200.0,'Mediamarkt','2021-04-24','2021-04-24','94270041',2600.0,6,4),
     	 (-50.0,'Movistar','2021-04-25','2021-04-25','74210723',2550.0,8,4),
     	 (-50.0,'Carrefour','2021-04-26','2021-04-26','94270041',2500.0,9,4),
     	 (100.0,'Ingreso','2021-04-27','2021-04-27','',2600.0,7,4),
     	 (-50.0,'Mercadona','2021-04-28','2021-04-28','74210723',2550.0,5,4);
     INSERT INTO training2.movimientos (cantidad,concepto,fecha_operacion,fecha_valor,num_tarjeta,saldo_actual,id_categoria,id_cuenta) VALUES
     	 (-50.0,'El Corte Ingles','2021-04-29','2021-04-29','94270041',2500.0,3,4),
     	 (-50.0,'Hotel Florida','2021-05-01','2021-05-01','74210723',2450.0,4,4),
     	 (50.0,'Ingreso','2021-05-02','2021-05-02','',2500.0,2,4),
     	 (-50.0,'Mediamarkt','2021-05-03','2021-05-03','74210723',2450.0,9,4),
     	 (-50.0,'Emasa Agua','2021-05-04','2021-05-04','94270041',2400.0,8,4),
     	 (-50.0,'Restaurante Paca','2021-05-05','2021-05-05','94270041',2350.0,1,4),
     	 (150.0,'Ingreso','2021-05-06','2021-05-06','',2200.0,7,4),
     	 (-50.0,'ITV','2021-05-07','2021-05-07','94270041',2150.0,7,4);

