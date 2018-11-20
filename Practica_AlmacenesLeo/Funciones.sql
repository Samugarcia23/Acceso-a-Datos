--ALMACENES LEO

/*
	Una base de datos gestiona una serie de almacenes. En los almacenes se utilizan unos �contenedores� con medidas estandard.

	Cada almac�n tiene una capacidad limitada, expresada por el n�mero de contenedores que caben en �l.


------------Queremos una aplicaci�n que asigne env�os a los almacenes seg�n las siguientes reglas:-------------------------------------------------------------------------------

	--Un env�o se compone de uno o m�s contenedores.

	--El env�o debe estar en un �nico almac�n, no pueden separarse los contenedores de un mismo pedido en almacenes distintos
	--Para ello, se consultar� la tabla Envios y se comprobar�n todos los env�os que tengan un valor NULL en la columna �Fecha Asignaci�n�.

	Se comprobar� si el env�o cabe en el almac�n que figure en la columna �Almacen Preferido� y caso de no ser as�, se intentar� derivarlo al almac�n m�s cercano. 
	Si en este tampoco hubiese espacio, probar�amos en el segundo m�s cercano y as� sucesivamente. Si no hubiera espacio suficiente en ninguno, se dejar�a sin asignar.
	--Para comprobar si el pedido cabe en un almacen se sumaran todos los contenedores de todos los pedidos de la la tabla �Asignaciones� que est�n actualmente asignados a 
	--ese almac�n y se restar� esta cantidad de la capacidad del almacen. Para esto puede usarse una funci�n

	Antes de asignarlo a un almac�n alternativo, la aplicaci�n pedir� confirmaci�n al usuario, que decidir� si acepta la alternativa o deja el pedido sin asignar.

	--Para comprobar si hay espacio, la aplicaci�n intentar� hacer una actualizaci�n (inserci�n en Asignaciones y actualizar fecha) asign�ndolo a un almac�n 
	--y si no hay espacio deber� lanzarse una excepci�n personalizada con RAISERROR. La excepci�n la lanzar� un TRIGGER, que ser� el encargado de comprobar si hay espacio o no.
*/
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
	Para comprobar si el pedido cabe en un almacen se sumaran todos los contenedores de todos los pedidos de la la tabla �Asignaciones� que est�n actualmente asignados a 
	ese almac�n y se restar� esta cantidad de la capacidad del almacen. Para esto puede usarse una funci�n
*/
	
GO
CREATE FUNCTION fnCabePedidoEnAlmacen (@IDAlmacen Bigint, @IDEnvio BigInt) RETURNS Bit
AS
	BEGIN
		DECLARE @Ret Bit
		DECLARE @Capacidad BigInt	=  (SELECT D.disponible 
										FROM 
										(
											SELECT A.ID, A.Capacidad, Sum(E.NumeroContenedores) AS Ocupado, A.Capacidad - Sum(E.NumeroContenedores) AS disponible 
											From Almacenes AS A 
											Inner Join Asignaciones As Ag ON A.ID = Ag.IDAlmacen
											Inner Join Envios AS E ON Ag.IDEnvio = E.ID
											Group By A.ID, A.Capacidad
										) AS D
										WHERE D.ID = @IDAlmacen)						

		DECLARE @Contenedores BigInt =  (SELECT NumeroContenedores
										 FROM Envios
										 WHERE ID = @IDEnvio)

			IF (@Capacidad >= @Contenedores) 
				SET @Ret = 1
			ELSE
				SET @Ret = 0	

		RETURN @Ret
	END
GO

/*
	Funcion que comprueba si la id introducida corresponde a un almacen de la base de datos
*/

GO
CREATE FUNCTION fnValidarIdAlmacen (@ID Bigint) RETURNS bit
AS
	BEGIN
		DECLARE @ret bit = 0
		IF (EXISTS (SELECT ID FROM Almacenes WHERE ID = @ID))
			SET @ret = 1
		RETURN @ret
	END
GO

EXECUTE fnValidarIdAlmacen 10
