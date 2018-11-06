--ALMACENES LEO

/*
	Una base de datos gestiona una serie de almacenes. En los almacenes se utilizan unos “contenedores” con medidas estandard.

	Cada almacén tiene una capacidad limitada, expresada por el número de contenedores que caben en él.


------------Queremos una aplicación que asigne envíos a los almacenes según las siguientes reglas:-------------------------------------------------------------------------------

	Un envío se compone de uno o más contenedores.

	--El envío debe estar en un único almacén, no pueden separarse los contenedores de un mismo pedido en almacenes distintos
	Para ello, se consultará la tabla Envios y se comprobarán todos los envíos que tengan un valor NULL en la columna “Fecha Asignación”.

	Se comprobará si el envío cabe en el almacén que figure en la columna “Almacen Preferido” y caso de no ser así, se intentará derivarlo al almacén más cercano. 
	Si en este tampoco hubiese espacio, probaríamos en el segundo más cercano y así sucesivamente. Si no hubiera espacio suficiente en ninguno, se dejaría sin asignar.
	Para comprobar si el pedido cabe en un almacen se sumaran todos los contenedores de todos los pedidos de la la tabla “Asignaciones” que estén actualmente asignados a 
	ese almacén y se restará esta cantidad de la capacidad del almacen. Para esto puede usarse una función

	Antes de asignarlo a un almacén alternativo, la aplicación pedirá confirmación al usuario, que decidirá si acepta la alternativa o deja el pedido sin asignar.

	--Para comprobar si hay espacio, la aplicación intentará hacer una actualización (inserción en Asignaciones y actualizar fecha) asignándolo a un almacén 
	y si no hay espacio deberá lanzarse una excepción personalizada con RAISERROR. La excepción la lanzará un TRIGGER, que será el encargado de comprobar si hay espacio o no.
*/



/*Creacion de la base de datos y de sus tablas*/

CREATE DATABASE AlmacenesLeo
GO
USE AlmacenesLeo
GO

/*
	Tabla para guardar informacion sobre los almacenes, su clave primaria es la id del almacen
*/
CREATE TABLE Almacenes ( 
ID Int NOT NULL CONSTRAINT PK_Almacenes Primary Key
,Denominacion NVarChar (30) Not NULL
,Direccion NVarChar (50) Not NULL
,Capacidad BigInt Not NULL
)

GO

/*
	Tabla que guarda las distancias entre 2 almacenes y la guarda en la variable distancia, su id esta compuesta por la id de los 2 almacenes
*/

CREATE TABLE Distancias (
IDAlmacen1 Int NOT NULL
,IDAlmacen2 Int NOT NULL
,Distancia Int NOT NULL
,CONSTRAINT PK_Distancias Primary Key (IDAlmacen1, IDAlmacen2)
,CONSTRAINT FK_DistanciaAlmacen1 Foreign KEy (IDAlmacen1) REFERENCES Almacenes (ID)
,CONSTRAINT FK_DistanciaAlmacen2 Foreign Key (IDAlmacen2) REFERENCES Almacenes (ID)
)

/*
	Tabla que guarda informacion de los envios 
*/
CREATE TABLE Envios (
ID BigInt NOT NULL CONSTRAINT PK_Envios Primary Key
,NumeroContenedores Int Not NULL DEFAULT 1
,FechaCreacion DATE NOT NULL
,FechaAsignacion DATE NULL
,AlmacenPreferido Int NOT NULL
)

/*
	Tabla para guardar las asignaciones de cada envio
*/

GO
CREATE TABLE Asignaciones (
IDEnvio BigInt NOT NULL CONSTRAINT PK_Asignaciones Primary Key
,IDAlmacen Int NOT NULL
,CONSTRAINT FK_AsignacionEnvio Foreign KEy (IDEnvio) REFERENCES Envios (ID)
,CONSTRAINT FK_AsignacionAlmacen Foreign KEy (IDAlmacen) REFERENCES Almacenes (ID)
)

/*
	Funcion que devuelva una tabla con los envios que no se hayan asignado (fecha asignacion) a un almacen (NULL)
	Entradas: Ninguna
	Salida: Tabla con los envios no asignados
*/

GO
CREATE FUNCTION fnTablaEnviosSinAsignar () RETURNS TABLE
AS
		RETURN	(SELECT * 
				FROM Envios 
				WHERE FechaAsignacion IS NULL)
GO

--SELECT * FROM fnTablaEnviosSinAsignar()

/*
	Trigger quen se ejecuta al detectar que no hay espacio en un almacen
*/

GO
CREATE TRIGGER NoHayEspacioEnElAlmacen ON Asignaciones AFTER INSERT
AS
	BEGIN
		DECLARE @LLENO INT = (SELECT (Al.Capacidad - E.NumeroContenedores) 
								FROM inserted AS A
								INNER JOIN Almacenes AS Al ON A.IDAlmacen = Al.ID
								INNER JOIN Envios AS E ON A.IDEnvio = E.ID)
		IF (@LLENO < 0) 
			BEGIN
				RAISERROR ('Error, El almacen está lleno', 10, 1)
				ROLLBACK TRANSACTION
			END
	END
GO















/*
	Funcion para calcular el almacen mas cercano
	Entrada: 
	Salida:	id del almacen mas cercano
*/
	
GO
CREATE FUNCTION fnCalcularAlmacenMasCercano (@IDAlmacen1 Bigint, @IDAlmacen2 Bigint) RETURNS Bigint
AS
	BEGIN
		RETURN (SELECT min(Distancia)
				FROM Distancias
				WHERE IDAlmacen1 = @IDAlmacen1 AND IDAlmacen2 = @IDAlmacen2)
	END
