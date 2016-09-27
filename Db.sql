sql 
1. Let  be the number of CITY entries in STATION, and let  be the number of distinct CITY names in STATION; query the value of  from STATION. In other words, find the difference between the total number of CITY entries in the table and the number of distinct CITY entries in the table.

Input Format

The STATION table is described as follows:


where LAT_N is the northern latitude and LONG_W is the western longitude.

Ans:  Select N1-N FROM (SELECT count( DISTINCT CITY) as N,count(CITY) as N1 FROM STATION ORDER BY CITY);


***************************************************************************************************************************
2. You are given three tables: Students, Friends and Packages. Students contains two columns: ID and Name.
Friends contains two columns: ID and Friend_ID (ID of the ONLY best friend). 
Packages contains two columns: ID and Salary (offered salary in $ thousands per month).

Ans :Select NAME FROM
(SELECT Students.ID as ID, Students.NAME as NAME, Packages.SALARY AS SAL ,Friends.Friend_ID AS F_ID,(Select SALARY from  Packages where ID= Friends.Friend_ID )
as F_SAL FROM  Students JOIN Friends ON  Students.ID=Friends.ID JOIN Packages ON  Students.ID= Packages.ID )
WHERE F_SAL>SAL ORDER BY F_SAL;
