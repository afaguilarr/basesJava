-- Creación de tablas y tipos
DROP TYPE venta_type FORCE;
CREATE OR REPLACE TYPE venta_type
AS OBJECT( 
    x NUMBER(3), 
    y NUMBER(3),
    v NUMBER(8)
    );
/

CREATE OR REPLACE TYPE
nest_venta AS TABLE OF venta_type;
/

CREATE OR REPLACE TYPE vvcity_type AS 
OBJECT(
    CodigoVendedor NUMBER(8), 
    Ciudad VARCHAR2(20),
    Ventas nest_venta
    );
/

DROP TABLE VVCITY;
CREATE TABLE VVCITY OF vvcity_type
(CodigoVendedor PRIMARY KEY) 
NESTED TABLE Ventas STORE AS store_ventas;
/

-- Ejemplos insert
INSERT INTO VVCITY VALUES(8, 'Medellin',
                            nest_venta(
                                venta_type(4,10,10),
                                venta_type(3,5,50),
                                venta_type(3,2,50)
                                )
                        );
/

INSERT INTO VVCITY VALUES(9, 'Medellin',
                            nest_venta(
                                venta_type(4,10,10),
                                venta_type(3,5,50),
                                venta_type(3,2,50),
                                venta_type(3,3,70)
                                )
                        );
/

-- Ejemplos Select
SELECT CodigoVendedor, Ciudad, Tventas.* FROM VVCITY, TABLE(VVCITY.Ventas) Tventas;

SELECT CodigoVendedor, Ciudad, Tventas.* FROM VVCITY, TABLE(VVCITY.Ventas) Tventas WHERE ciudad = 'Medellin';
