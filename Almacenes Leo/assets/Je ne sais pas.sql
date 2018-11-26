SELECT * 
FROM Almacenes AS A
INNER JOIN Envios E ON A.ID = E.AlmacenPreferido


SELECT *
	FROM Almacenes AS A
		INNER JOIN Distancias AS D
			ON A.ID = D.IDAlmacen1 AND D.IDAlmacen2 = A.ID
	WHERE A.ID < 20
UNION
SELECT *
	FROM Almacenes AS A
		INNER JOIN Distancias AS D
			ON D.IDAlmacen1 = A.ID AND A.ID = D.IDAlmacen2
	WHERE A.ID > 20

	SELECT IDAlmacen1 AS ID1, IDAlmacen2 AS ID2, Distancia
	FROM Distancias 
	UNION
	SELECT IDAlmacen2 AS ID1, IDAlmacen1 AS ID2, Distancia
	FROM Distancias 






	SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON A.ID = D.IDAlmacen1 AND IDAlmacen2 = 20 WHERE A.ID < 20 UNION SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON D.IDAlmacen1 = 20 AND A.ID = D.IDAlmacen2 WHERE A.ID > 20 ORDER BY ID


SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON A.ID = D.IDAlmacen1 AND IDAlmacen2 = 20 WHERE A.ID < 20 UNION SELECT * FROM Almacenes AS A INNER JOIN Distancias AS D ON D.IDAlmacen1 = 20 AND A.ID = D.IDAlmacen2 WHERE A.ID > 20


SELECT AlmacenPreferido FROM Envios WHERE ID = 2

SELECT * FROM Almacenes

SELECT AlmacenPreferido FROM Envios WHERE ID = ?