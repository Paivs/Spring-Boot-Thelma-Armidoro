create table diarioemocoes(

    id bigint not null auto_increment,
    data datetime not null,
    titulo varchar(100),
    texto MEDIUMTEXT,
    paciente_id bigint,

    primary key(id),
    constraint fk_diarioemocoes_paciente_id foreign key(paciente_id) references pacientes(id)
);