CREATE TABLE public.tb_pessoa
(
    id bigint NOT NULL DEFAULT nextval('tb_pessoa_id_seq'::regclass),
    nome text COLLATE pg_catalog."default" NOT NULL,
    endereco_comercial text COLLATE pg_catalog."default",
    idade bigint,
    altura double precision,
    quantidade_quilos double precision,
    casado boolean,
    id_endereco bigint,
    foto bytea,
    lista_emails json,
    CONSTRAINT pk_id_pessoa PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.tb_pessoa
    OWNER to postgres;

CREATE TABLE public.tb_cidade
(
    id bigint NOT NULL DEFAULT nextval('tb_cidade_id_seq'::regclass),
    nome_da_cidade text COLLATE pg_catalog."default",
    CONSTRAINT pk_id_cidade PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.tb_cidade
    OWNER to postgres;

CREATE TABLE public.tb_endereco
(
    nome_da_rua text COLLATE pg_catalog."default",
    id_cidade bigint,
    id bigint NOT NULL DEFAULT nextval('tb_endereco_id_seq'::regclass),
    CONSTRAINT pk_id_endereco PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.tb_endereco
    OWNER to postgres;


CREATE TABLE public.tb_cliente
(
    id bigserial NOT NULL,
    nome text,
    idade bigint,
    peso double precision,
    altura double precision,
    casado boolean,
    CONSTRAINT pk_id_cliente PRIMARY KEY (id)
);

ALTER TABLE public.tb_cliente
    OWNER to postgres;