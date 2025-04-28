-- 1. Crear la base de datos "Authors"
CREATE DATABASE Authors;
GO

-- 2. Seleccionar la base de datos
USE Authors;
GO

-- 3. Crear la tabla "LiteraryGenre" para almacenar géneros literarios
CREATE TABLE LiteraryGenre (
    id_genre INT IDENTITY(1,1) PRIMARY KEY,       -- Clave primaria autoincremental
    genre_name NVARCHAR(100) NOT NULL UNIQUE       -- Nombre de género único
);
GO

-- 3.1 Insertar género "Desconocido" para valores por defecto
INSERT INTO LiteraryGenre (genre_name) VALUES ('Desconocido');
GO

-- 4. Crear la tabla "Author" para almacenar autores
CREATE TABLE Author (
    id_author INT IDENTITY(1,1) PRIMARY KEY,       -- Clave primaria autoincremental
    author_name NVARCHAR(150) NOT NULL,             -- Nombre del autor
    birth_date DATE NOT NULL,                       -- Fecha de nacimiento
    phone NVARCHAR(8) NOT NULL,                     -- Teléfono validado (sin guion)
    id_genre INT NOT NULL DEFAULT 1,                -- Relación con género, por defecto "Desconocido"
    status NVARCHAR(10) NOT NULL DEFAULT 'ACTIVE',  -- Estado lógico (activo/inactivo)
    
    CONSTRAINT FK_Author_Genre FOREIGN KEY (id_genre) REFERENCES LiteraryGenre(id_genre),
    CONSTRAINT UQ_Author_Name UNIQUE (author_name),   -- Nombre de autor único
    CONSTRAINT CK_Author_Phone CHECK (phone LIKE '[2,3,6,7][0-9][0-9][0-9][0-9][0-9][0-9][0-9]') -- Validación del teléfono
);
GO

-- 4.1 Crear índices para optimizar búsquedas
CREATE INDEX idx_author_name ON Author (author_name);
CREATE INDEX idx_author_genre ON Author (id_genre);
GO

-- 5. Crear la tabla "AuditLog" para registrar acciones de auditoría
CREATE TABLE AuditLog (
    id_log INT IDENTITY(1,1) PRIMARY KEY,    -- Clave primaria autoincremental
    action_type NVARCHAR(50),                 -- Tipo de acción (INSERT, UPDATE, DELETE)
    table_name NVARCHAR(50),                  -- Nombre de la tabla afectada
    record_id INT,                            -- ID del registro afectado
    action_date DATETIME DEFAULT GETDATE()    -- Fecha de la acción
);
GO

-- 6. Crear triggers para registrar acciones automáticamente en "AuditLog"

-- 6.1 Trigger para registrar inserciones en "Author"
CREATE TRIGGER trg_Author_Insert
ON Author
AFTER INSERT
AS
BEGIN
    INSERT INTO AuditLog (action_type, table_name, record_id)
    SELECT 'INSERT', 'Author', id_author
    FROM inserted;
END;
GO

-- 6.2 Trigger para registrar actualizaciones en "Author"
CREATE TRIGGER trg_Author_Update
ON Author
AFTER UPDATE
AS
BEGIN
    INSERT INTO AuditLog (action_type, table_name, record_id)
    SELECT 'UPDATE', 'Author', id_author
    FROM inserted;
END;
GO

-- 6.3 Trigger para registrar eliminaciones físicas en "Author"
CREATE TRIGGER trg_Author_Delete
ON Author
AFTER DELETE
AS
BEGIN
    INSERT INTO AuditLog (action_type, table_name, record_id)
    SELECT 'DELETE', 'Author', id_author
    FROM deleted;
END;
GO

-- Primero verifica el nombre exacto del constraint (por si el nombre fue generado automático o si estás seguro que es CK_Author_Phone)
ALTER TABLE Author DROP CONSTRAINT CK_Author_Phone;
GO

-- Consulta para ver si todavía existe algún CHECK en la tabla Author
SELECT 
    OBJECT_NAME(parent_object_id) AS TableName,
    name AS ConstraintName,
    type_desc AS ConstraintType
FROM 
    sys.check_constraints
WHERE 
    parent_object_id = OBJECT_ID('Author');



	SELECT id_author, author_name, birth_date, phone, id_genre, status
FROM Author;

ALTER TABLE Author
DROP CONSTRAINT UQ_Author_Name;

DELETE FROM Author;

DBCC CHECKIDENT ('Author', RESEED, 0);