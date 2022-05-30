drop database Tienda;
create database Tienda;
use Tienda;

CREATE TABLE productos(
	CodProducto 	VARCHAR(10) primary key,
	Nombre    	VARCHAR(20) NOT NULL,
	LineaProducto	VARCHAR(10),
	PrecioUnitario	INTEGER(6),
	Stock 		INTEGER(5)
);

CREATE TABLE ventas(
	CodVenta  		VARCHAR(4) primary key,
	CodProducto 		VARCHAR(10) NOT NULL,
	FechaVenta 		DATE,
	UnidadesVendidas	INTEGER(3),
	Foreign key (CodProducto) references productos(CodProducto)
	on delete cascade on update cascade
);

INSERT INTO productos VALUES ('1','Procesador P133','Proc',15000,20);
INSERT INTO productos VALUES ('2','Placa base VX','PB',18000,15);
INSERT INTO productos VALUES ('3','Simm EDO 16Mb','Memo',7000,30);
INSERT INTO productos VALUES ('4','Disco SCSI 4Gb','Disc',38000, 5);
INSERT INTO productos VALUES ('5','Procesador K6-2','Proc',18500,10);
INSERT INTO productos VALUES ('6','Disco IDE 2.5Gb','Disc',20000,25);
INSERT INTO productos VALUES ('7','Procesador MMX','Proc',15000, 5);
INSERT INTO productos VALUES ('8','Placa Base Atlas','PB',12000, 3);
INSERT INTO productos VALUES ('9','DIMM SDRAM 32Mb','Memo',17000,12);
 
INSERT INTO ventas VALUES('V1','2','97/09/22',2);
INSERT INTO ventas VALUES('V2','4','97/09/22',1);
INSERT INTO ventas VALUES('V3','6','97/09/23',3);
INSERT INTO ventas VALUES('V4','5','97/09/26',5);
INSERT INTO ventas VALUES('V5','9','97/09/28',3);
INSERT INTO ventas VALUES('V6','4','97/09/28',1);
INSERT INTO ventas VALUES('V7','6','97/10/02',2);
INSERT INTO ventas VALUES('V8','6','97/10/02',1);
INSERT INTO ventas VALUES('V9','2','97/10/04',4);
INSERT INTO ventas VALUES('V10','9','97/10/04',4);
INSERT INTO ventas VALUES('V11','6','97/10/05',2);
INSERT INTO ventas VALUES('V12','7','97/10/07',1);
INSERT INTO ventas VALUES('V13','4','97/10/10',3);
INSERT INTO ventas VALUES('V14','4','97/10/16',2);
INSERT INTO ventas VALUES('V15','3','97/10/18',3);
INSERT INTO ventas VALUES('V16','4','97/10/18',5);
INSERT INTO ventas VALUES('V17','6','97/10/22',2);
INSERT INTO ventas VALUES('V18','6','97/11/02',2);
INSERT INTO ventas VALUES('V19','2','97/11/04',3);
INSERT INTO ventas VALUES('V20','9','97/12/04',3);
