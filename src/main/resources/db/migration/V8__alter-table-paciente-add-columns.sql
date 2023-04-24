alter table pacientes
add nascimento datetime not null,
add nacionalidade varchar(99),
add estado_civil varchar(20),
add escolaridade varchar(99),
add curso varchar(99),
add profissao varchar(99),
add cargo varchar(99),
add contato_nome varchar(100),
add contato_vinculo varchar(100),
add contato_telefone varchar(20);