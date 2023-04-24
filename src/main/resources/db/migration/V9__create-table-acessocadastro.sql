create table acessocadastro(

    id bigint not null auto_increment,
    login varchar(100) not null,
    pin varchar(255) not null,
    data datetime not null,
    ativo tinyint not null,

    primary key(id)
);