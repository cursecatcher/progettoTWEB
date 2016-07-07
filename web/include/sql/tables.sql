CREATE TABLE Pizza (
    id_pizza INT NOT NULL PRIMARY KEY
        GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
    nome VARCHAR(33) NOT NULL UNIQUE, 
    prezzo FLOAT NOT NULL, 
    ingredienti VARCHAR(129) NOT NULL,
    CHECK (prezzo > 0)
/* inserire flag: nopomo, nolattosio */
); 

CREATE TABLE Utente (
    id_utente INT NOT NULL PRIMARY KEY 
        GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
    email VARCHAR(65) NOT NULL UNIQUE, /* !!! */
    password CHAR(40), /* Possibili valori null per il login tramite social */
    ruolo VARCHAR(10) NOT NULL 
); 

CREATE TABLE Ingrediente (
    id_ingrediente INT NOT NULL PRIMARY KEY 
        GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
    nome VARCHAR(33) NOT NULL UNIQUE, 
    prezzo FLOAT NOT NULL, /* forse inutile */
    CHECK (prezzo >= 0) 

);