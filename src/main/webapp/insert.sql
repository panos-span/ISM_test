INSERT INTO Customer (ID, Name, Surname, VAT, Address, Email, Details)
VALUES (1, "mpampis", "sougias", 123, "zaimi-2", "spanakis01@gmail.com", null);
INSERT INTO Customer (ID, Name, Surname, VAT, Address, Email, Details)
VALUES (2, "petros", "gotzilas", 123, "evrou-11", "spyros@paradise.gr", null);

INSERT INTO User (Username, Password, Name, Surname, Phone, Email, Role)
VALUES ("salesman", "s1234", "Panos", "Span", "6947324456", "t8200158@aueb.gr", "Salesman");
INSERT INTO User (Username, Password, Name, Surname, Phone, Email, Role)
VALUES ("product_manager", "pm1234", "Natal", "Spart", "6942474956", "t8200159@aueb.gr", "Product Manager");

INSERT INTO product (Name, Price, Category, Description) VALUES ("Protokaladista",420.69,"beverage",null);
INSERT INTO product (Name, Price, Category, Description) VALUES ("Physikos chimos protrokali",69.42,"beverage",null);



DELETE FROM customer
WHERE ID=8625;
SELECT *
FROM customer
WHERE ID=1;

SELECT COUNT(*) FROM customer;
insert into saleDAO (Cust_id, Prod_id, Sale_Date, Quantity, Sale_Value) values (12981,1,"1000-01-02",5,25);

insert into customer (Name, Surname, VAT, Address, Email, Details)
values ("Spyros","Gartop","5636543", "Paradisou 420, Thessaloniki","spyrakos@paradise.gr", "Gionis");
insert into customer (Name, Surname, VAT, Address, Email, Details)
values ("Theo","Mallikoko","90898675","Αντωνιάδου 22, Athens","mallo@upogeio.gr","Τοποθεσία : Υπόγειο");

insert into customer_phones (ID, Phone,POSITION) VALUES (1,"696439854",1);
insert into customer_phones (ID, Phone,POSITION) VALUES (1,"696969699",2);
insert into customer_phones (ID, Phone,POSITION)
values (2,"694563402",1);
insert into customer_phones (ID, Phone,POSITION)
values (2,"698756243",2);


update customer set Name="Spyros", Surname="Gartop", VAT="5636543", Address="Paradisou 420, Thessaloniki", Email="spyrakos@paradise.gr", Details="Gionis" WHERE (ID=12981);
delete from customer_phones where (ID=1);

select Phone FROM customer_phones WHERE ID=12981;

ALTER TABLE Customer AUTO_INCREMENT = 5;
