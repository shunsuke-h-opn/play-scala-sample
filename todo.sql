create table todo(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    status varchar(31) NOT NULL,
    title varchar(255) NOT NULL,
    PRIMARY KEY (ID)) AUTO_INCREMENT=10000;

insert into todo(id, status, title) values(null, 'UNDONE', 'すごい機能の要件定義');
insert into todo(id, status, title) values(null, 'UNDONE', 'すごい機能の設計');
insert into todo(id, status, title) values(null, 'UNDONE', 'すごい機能の実装');