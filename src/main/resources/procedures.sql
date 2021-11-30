DELIMITER //
CREATE PROCEDURE GetSpecificBankUser(
	IN surnamearg VARCHAR(255)
)
BEGIN
	SELECT *
 	FROM card_owner
    left join bank_card
	on card_owner.id = bank_card.card_id
	WHERE surname = surnamearg;

END //
DELIMITER ;

CALL GetSpecificBankUser('Fe24535');

DELIMITER //
CREATE PROCEDURE GetBankUsers()
BEGIN
	SELECT *
 	FROM card_owner
    left join bank_card
	on card_owner.id = bank_card.card_id;
END //
DELIMITER ;

CALL GetBankUsers();



DELIMITER //
CREATE PROCEDURE StartApplication()
BEGIN
    alter table bank_card
       drop
       foreign key FKdqlysu1n5jflc0w1m32cjcj7a;

    alter table credit_card
       drop
       foreign key FKh3hp347fett74f4s5gb0js753;

    alter table debit_card
       drop
       foreign key FK8kgimp0f221dsjyovdtn9d6rf;

    drop table if exists bank_card;
    drop table if exists card_owner;
    drop table if exists credit_card;
    drop table if exists debit_card;
    drop table if exists hibernate_sequence;

    create table bank_card (
       id bigint not null,
        brand varchar(15),
        card_number bigint not null,
        csv varchar(3),
        state varchar(255),
        validity datetime not null,
        card_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table card_owner (
       id bigint not null,
        name varchar(15),
        surname varchar(15),
        primary key (id)
    ) engine=InnoDB;

    create table credit_card (
       id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table debit_card (
       id bigint not null,
        primary key (id)
    ) engine=InnoDB;



    alter table bank_card
       add constraint UK3v4a17bn9pa33d6u233jsmg2v unique (card_number);

    alter table card_owner
       add constraint UKlxthg169qfee0pirc2tne00cf unique (surname);

    alter table bank_card
       add constraint FKdqlysu1n5jflc0w1m32cjcj7a
       foreign key (card_id)
       references card_owner (id);

    alter table credit_card
       add constraint FKh3hp347fett74f4s5gb0js753
       foreign key (id)
       references bank_card (id);

    alter table debit_card
       add constraint FK8kgimp0f221dsjyovdtn9d6rf
       foreign key (id)
       references bank_card (id);
END //

call StartApplication();
