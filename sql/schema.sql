DROP TABLE IF EXISTS cdkeys;
DROP TABLE IF EXISTS games;
create table games (
    id bigint primary key not null GENERATED ALWAYS AS IDENTITY,
    name varchar(50) not null,
    platform varchar(25),
    state varchar(5) check (state in ('在玩', '通关', '弃坑')),
    rating int check (rating >=0 and rating <=100),
    reflection varchar(1000),
    created_at timestamp with time zone default current_timestamp not null
);

create table cdkeys (
    id bigint primary key not null GENERATED ALWAYS AS IDENTITY,
    game_id bigint not null references games(id) on delete cascade,
    code varchar(50) not null unique,
    status varchar(5) check (status in ('未售', '已售')) not null default '未售',
    price NUMERIC(10,2) check (price >=0),
    created_at timestamp with time zone default current_timestamp not null
);
