create database Construccion;
use Construccion;

create table proveedor(
   numprov varchar(3) primary key,
   nombreprov varchar(15),
   estado int,
   ciudad varchar(10));
   
insert into proveedor values ('v1','Smith',20,'Londres');
insert into proveedor values ('v2','Jones',10,'Paris');
insert into proveedor values ('v3','Blake',30,'Paris');  
insert into proveedor values ('v4','Clarke',20,'Londres');
insert into proveedor values ('v5','Adams',30,'Atenas');
        
create table parte(
   numparte varchar(3) primary key,
   nombreparte varchar(15),
   color varchar(10),
   peso int,
   ciudad varchar(10));
   
insert into parte values ('p1','Tuerca','Rojo',12,'Londres');
insert into parte values ('p2','Perno','Verde',17,'Paris');
insert into parte values ('p3','Tornillo','Azul',17,'Roma');  
insert into parte values ('p4','Tornillo','Rojo',14,'Londres');
insert into parte values ('p5','Leva','Azul',12,'Paris');   
insert into parte values ('p6','Engranaje','Rojo',19,'Londres');   

create table proyecto(
   numproyecto varchar(3) primary key,
   nombreproyecto varchar(15),
   ciudad varchar(10));
   
insert into proyecto values ('y1','Clasificador','Paris');
insert into proyecto values ('y2','Monitor','Roma');
insert into proyecto values ('y3','OCR','Atenas');  
insert into proyecto values ('y4','Consola','Atenas');
insert into proyecto values ('y5','RAID','Londres');   
insert into proyecto values ('y6','EDS','Oslo');   
insert into proyecto values ('y7','Cinta','Londres');    
  
create table suministra(
   numprov varchar(3),
   numparte varchar(3),
   numproyecto varchar(3),
   primary key(numprov,numparte,numproyecto),
   cantidad int);

insert into suministra values ('v1','p1','y1',300);
insert into suministra values ('v1','p1','y4',500);
insert into suministra values ('v1','p2','y1',400); 
insert into suministra values ('v2','p3','y2',200);
insert into suministra values ('v2','p3','y3',300);
insert into suministra values ('v2','p3','y4',100);  
insert into suministra values ('v2','p3','y6',400);
insert into suministra values ('v2','p5','y2',100);  
insert into suministra values ('v3','p3','y1',300);
insert into suministra values ('v3','p4','y2',400);   
insert into suministra values ('v4','p6','y3',300);
insert into suministra values ('v4','p6','y7',100);
insert into suministra values ('v5','p2','y2',300);  
insert into suministra values ('v5','p4','y4',600);
insert into suministra values ('v5','p5','y4',500);  
insert into suministra values ('v5','p6','y4',300);

        