create sequence sq_coupon_cno;
create sequence sq_employee_eno;
create sequence sq_menu_menuno;
drop sequence sq_menu_menuno;
drop sequence sq_order_cus_ono;

create sequence sq_order_cus_ono;
create sequence sq_stock_sno;
drop sequence sq_stock_sno;

alter table order_cus add Constraint ck_order_cus_otype check(otype In ('테이크아웃','매장'));

Insert into Menu(menuno,menuname,mprice,mcount) values(sq_menu_menuno.nextval,'플랫화이트',4300,10);
Insert into Menu(menuno,menuname,mprice,mcount) values(sq_menu_menuno.nextval,'카라멜라테 마키아토',4600,8);
Insert into Menu(menuno,menuname,mprice,mcount) values(sq_menu_menuno.nextval,'카푸치노',4500,30);
Insert into Menu(menuno,menuname,mprice,mcount) values(sq_menu_menuno.nextval,'아이스 카푸치노',4500,10);
Insert into Menu(menuno,menuname,mprice,mcount) values(sq_menu_menuno.nextval,'아메리카노',4100,5);
Insert into Menu(menuno,menuname,mprice,mcount) values(sq_menu_menuno.nextval,'아이스 아메리카노',4100,12);
Insert into Menu(menuno,menuname,mprice,mcount) values(sq_menu_menuno.nextval,'초코치노',3500,25);
Insert into Menu(menuno,menuname,mprice,mcount) values(sq_menu_menuno.nextval,'그린티 라떼',3600,16);
Insert into Menu(menuno,menuname,mprice,mcount) values(sq_menu_menuno.nextval,'샌드위치',5000,8);

SELECT  menuname FROM menu order by menuno;

ALTER table order_cus add(OORDERno VARCHAR2(20));
ALTER table payment modify(ototalprice number(8));

ALTER table order_cus modify oorderno not null;
delete from order_cus ;
Insert into order_cus(ono,otime,otype,ctel,menuno,ocount,oorderno,ototalprice) VALUES(12,'19/05/07','테이크아웃','010-2222-2222',4,4,'2',200);
Insert into payment(oorderno,ototalprice) values('5',3000);


Alter table order_cus add CONSTRAINT fk_ordercus_oorderno FOREIGN key (oorderno) REFERENCES payment (oorderno);
Insert into order_cus(ono,otime,otype,ctel,menuno,ocount,cno,ototalprice) values(sq_menu_menuno.nextval,'샌드위치',5000,8);

select menuno,menuname,mprice,mcount from menu where menuno=1;
commit;



delete from stock;
INSERT INTO stock(sno,sorderno,stime,menuno,scount)  VALUES(1,2,3,4,5) ;

INSERT INTO stock(sno,menuno,scount)  VALUES(sq_stock_sno.nextval,1,2) ;
SELECT sorderno,menuno, scount FROM stock where sorderno = '20190524163913';

SELECT  distinct sorderno FROM stock order by sorderno;

Create table PAYMENT 
(Oorderno varchar2(20),OTotalPrice number(2),
CONSTRAINT pk_pay_Oorderno Primary Key (Oorderno));
 

SELECT S.SORDERNO SORDERNO, M.MENUNAME MENUNAME, S.SCOUNT, s.eno, s.stime 
FROM STOCK S INNER JOIN MENU M
ON S.MENUNO=M.MENUNO
ORDER BY SORDERNO;

SELECT S.SORDERNO SORDERNO, M.MENUNAME MENUNAME, S.SCOUNT scount, s.eno eno, s.stime stime  FROM STOCK S INNER JOIN MENU M ON S.MENUNO=M.MENUNO  ORDER BY SORDERNO;
SELECT S.SORDERNO SORDERNO, M.MENUNAME MENUNAME, S.SCOUNT scount, s.eno eno, s.stime stime  FROM STOCK S INNER JOIN MENU M ON S.MENUNO=M.MENUNO WHERE s.SORDERNO = '20190524163739'  ORDER BY m.Menuname;
SELECT S.SORDERNO SORDERNO, M.MENUNAME MENUNAME, S.SCOUNT scount, s.eno eno, s.stime stime    FROM STOCK S INNER JOIN MENU M   ON S.MENUNO=M.MENUNO   WHERE s.SORDERNO = '20190524163739'  ORDER BY m.Menuname;
SELECT Menuno, menuname, mcount from menu ;

SELECT Menuno, menuname, mcount from menu where menuname = '플랫화이트' order by menuno;

select oc.otime otime, c.ctel  ctel , c.cmile cmile, m.menuname menuname, m.mprice mprice, oc.ocount ocount, oc.otype type 
from customer c inner join order_cus oc
on c.ctel = oc.ctel
inner join menu m 
on oc.menuno = m.menuno
where trim(c.ctel) = '010-1111-1111'
order by otime desc;

select oc.otime otime, c.ctel  ctel , c.cmile cmile, m.menuname menuname, m.mprice mprice, oc.ocount ocount, oc.otype type   from customer c inner join order_cus oc
 on c.ctel = oc.ctel inner join menu m  on oc.menuno = m.menuno where trim(c.ctel) = '010-1111-1111' order by otime desc;