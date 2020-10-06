

DROP TABLE CITY;
CREATE TABLE CITY(
 Nombre_ciudad VARCHAR2(80) PRIMARY KEY,
 locales XMLTYPE NOT NULL
);
/


-- Ejemplos insert
INSERT INTO CITY VALUES ('Medellin', '<locales><rectangulo><a>0</a><b>0</b><c>30</c><d>30</d></rectangulo><rectangulo><a>50</a><b>50</b><c>200</c><d>200</d></rectangulo><rectangulo><a>400</a><b>400</b><c>100</c><d>100</d></rectangulo></locales>');
INSERT INTO CITY VALUES ('Cali', '<locales><rectangulo><a>0</a><b>0</b><c>30</c><d>30</d></rectangulo><rectangulo><a>50</a><b>50</b><c>200</c><d>200</d></rectangulo><rectangulo><a>400</a><b>400</b><c>100</c><d>100</d></rectangulo></locales>');
INSERT INTO CITY VALUES ('Bogota', '<locales><rectangulo><a>0</a><b>0</b><c>30</c><d>30</d></rectangulo><rectangulo><a>50</a><b>50</b><c>200</c><d>200</d></rectangulo><rectangulo><a>400</a><b>400</b><c>100</c><d>100</d></rectangulo></locales>');
INSERT INTO CITY VALUES ('Barranquilla', '<locales><rectangulo><a>0</a><b>0</b><c>30</c><d>30</d></rectangulo><rectangulo><a>50</a><b>50</b><c>200</c><d>200</d></rectangulo><rectangulo><a>400</a><b>400</b><c>100</c><d>100</d></rectangulo></locales>');
INSERT INTO CITY VALUES ('Barranquilla', '<locales><rectangulo><a>0</a><b>0</b><c>30</c><d>30</d></rectangulo><rectangulo><a>50</a><b>50</b><c>200</c><d>200</d></rectangulo><rectangulo><a>300</a><b>300</b><c>100</c><d>100</d></rectangulo></locales>');

-- Ejemplos Select

SELECT Nombre_ciudad, EXTRACTVALUE(locales,'/locales/rectangulo[%s]/a') AS a, EXTRACTVALUE(locales,'/locales/rectangulo[%s]/b') AS b, EXTRACTVALUE(locales,'/locales/rectangulo[%s]/c') AS c,EXTRACTVALUE(locales,'/locales/rectangulo[%s]/d') AS d FROM CITY WHERE Nombre_ciudad = '%s';
SELECT Nombre_ciudad, EXTRACTVALUE(locales,'/locales/rectangulo/a') AS a, EXTRACTVALUE(locales,'/locales/rectangulo/b') AS b, EXTRACTVALUE(locales,'/locales/rectangulo/c') AS c,EXTRACTVALUE(locales,'/locales/rectangulo/d') AS d FROM CITY WHERE Nombre_ciudad = 'Medellin';

SELECT Nombre_ciudad, EXTRACT(locales,'/locales/rectangulo/a') AS a FROM CITY WHERE Nombre_ciudad = 'Medellin';


SELECT Nombre_ciudad, Trectangulos.* FROM CITY, TABLE(CITY.locales) Tventas;
