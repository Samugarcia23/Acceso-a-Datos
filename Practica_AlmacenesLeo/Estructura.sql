--ALMACENES LEO

/*
	Una base de datos gestiona una serie de almacenes. En los almacenes se utilizan unos �contenedores� con medidas estandard.

	Cada almac�n tiene una capacidad limitada, expresada por el n�mero de contenedores que caben en �l.


------------Queremos una aplicaci�n que asigne env�os a los almacenes seg�n las siguientes reglas:-------------------------------------------------------------------------------

	Un env�o se compone de uno o m�s contenedores.

	--El env�o debe estar en un �nico almac�n, no pueden separarse los contenedores de un mismo pedido en almacenes distintos
	Para ello, se consultar� la tabla Envios y se comprobar�n todos los env�os que tengan un valor NULL en la columna �Fecha Asignaci�n�.

	Se comprobar� si el env�o cabe en el almac�n que figure en la columna �Almacen Preferido� y caso de no ser as�, se intentar� derivarlo al almac�n m�s cercano. 
	Si en este tampoco hubiese espacio, probar�amos en el segundo m�s cercano y as� sucesivamente. Si no hubiera espacio suficiente en ninguno, se dejar�a sin asignar.
	Para comprobar si el pedido cabe en un almacen se sumaran todos los contenedores de todos los pedidos de la la tabla �Asignaciones� que est�n actualmente asignados a 
	ese almac�n y se restar� esta cantidad de la capacidad del almacen. Para esto puede usarse una funci�n

	Antes de asignarlo a un almac�n alternativo, la aplicaci�n pedir� confirmaci�n al usuario, que decidir� si acepta la alternativa o deja el pedido sin asignar.

	--Para comprobar si hay espacio, la aplicaci�n intentar� hacer una actualizaci�n (inserci�n en Asignaciones y actualizar fecha) asign�ndolo a un almac�n 
	y si no hay espacio deber� lanzarse una excepci�n personalizada con RAISERROR. La excepci�n la lanzar� un TRIGGER, que ser� el encargado de comprobar si hay espacio o no.
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
				RAISERROR ('Error, El almacen est� lleno', 10, 1)
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
