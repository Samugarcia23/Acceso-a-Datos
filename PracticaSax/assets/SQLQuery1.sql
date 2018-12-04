Use Ejemplos
GO
CREATE Table Discos(
	ID SmallInt Not NULL Identity Constraint PK_Discos Primary Key,
	autor VarChar(30) Not NULL, 
	titulo VarChar(50) Not NULL,
    formato VarChar(25) NULL,
	localizacion VarChar(100) NULL
)