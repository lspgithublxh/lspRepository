
    alter table EMPLOYEE 
        drop 
        foreign key FK_Employee_Dept_DEPTID;

    drop table if exists DEPT;

    drop table if exists EMPLOYEE;

    create table DEPT (
        ID varchar(32) not null comment 'id',
        NAME varchar(32) not null comment 'name',
        PERIOD_OF_VALIDITY integer not null comment 'period_of_validity',
        COLOR varchar(255) comment 'color',
        primary key (ID)
    ) comment='Dept';

    create table EMPLOYEE (
        ID varchar(32) not null comment 'id',
        DEPT_ID_1 varchar(32),
        NAME varchar(32) comment 'name',
        DEPT_ID integer not null comment 'dept_id',
        primary key (ID)
    ) comment='Employee';

    alter table EMPLOYEE 
        add index FK_Employee_Dept_DEPTID (DEPT_ID_1), 
        add constraint FK_Employee_Dept_DEPTID 
        foreign key (DEPT_ID_1) 
        references DEPT (ID);
