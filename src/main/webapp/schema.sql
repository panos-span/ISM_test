/*DROP TABLE User,Customer,Customer_Phones,Product,Sale*/
CREATE TABLE User
(
    Username VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,
    Password VARCHAR(255) NOT NULL,
    Name     VARCHAR(255) NOT NULL,
    Surname  VARCHAR(255) NOT NULL,
    Phone    VARCHAR(10)  NOT NULL,
    Email    VARCHAR(255) NOT NULL,
    Role     VARCHAR(255) NOT NULL
    /*CONSTRAINT US CHECK (Role = 1 OR Role = 2 )*/
);

CREATE TABLE Customer
(
    ID      INT          NOT NULL PRIMARY KEY,
    Name    VARCHAR(255) NOT NULL,
    Surname VARCHAR(255) NOT NULL,
    VAT     VARCHAR(255),
    Address VARCHAR(255) NOT NULL,
    Email   VARCHAR(255) NOT NULL,
    Details LONGTEXT
);

CREATE TABLE Customer_Phones
(
    ID    INT NOT NULL REFERENCES Customer (ID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    Phone VARCHAR(10),
    CONSTRAINT CP PRIMARY KEY (ID, Phone)
);

CREATE TABLE Product
(
    ID          INT          NOT NULL PRIMARY KEY,
    Name        VARCHAR(255) NOT NULL,
    Price       FLOAT        NOT NULL,
    Category    VARCHAR(255),
    Description LONGTEXT
);

CREATE TABLE Sale
(
    Cust_id    INT      NOT NULL REFERENCES Customer (ID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    Prod_id    INT      NOT NULL REFERENCES Product (ID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    Sale_Date  DATETIME NOT NULL,
    Quantity   INT      NOT NULL,
    Sale_Value FLOAT    NOT NULL,
    CONSTRAINT SA PRIMARY KEY (Cust_id, Prod_id, Sale_Date)
);