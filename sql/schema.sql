create table games (
    id bigint primary key not null GENERATED ALWAYS AS IDENTITY,
    name varchar(50) not null,
    platform varchar(25),
    state varchar(5) check (state in ('在玩', '通关', '弃坑')),
    rating int check (rating >=0 and rating <=100),
    reflection varchar(1000),
    created_at timestamp with time zone default current_timestamp not null
);