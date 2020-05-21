CREATE TABLE IF NOT EXISTS account (
    id   SERIAL  NOT NULL ,
    name VARCHAR(128) NOT NULL,
    balance DOUBLE PRECISION,    
    PRIMARY KEY (id)
);