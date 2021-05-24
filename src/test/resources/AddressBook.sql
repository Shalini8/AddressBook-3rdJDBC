#UC16: Retrieve data and check no of users.
mysql> select * from userdetails;
+---------+------------+-----------+----------+-----------+--------------+---------+------------+-----------------+
| user_id | first_name | last_name | address  | city      | state        | zip     | phone      | email           |
+---------+------------+-----------+----------+-----------+--------------+---------+------------+-----------------+
|       1 | Shaan      | Raut      | Sector 1 | jagdalpur | Chhattisgarh | 123456  | 9876510123 | shaan@email.com |
|       2 | Sanju      | joshi     | Sector 1 | raipur    | Chhattisgarh | 494001  | 9845632178 | sanju@email.com |
|       3 | Shalini    | Pandey    | Asna     | jagdalpur | Chhattisgarh | 4942221 | 9845634578 | shalu@email.com |
+---------+------------+-----------+----------+-----------+--------------+---------+------------+-----------------+
mysql> select * from address_book;
+------------+-----------+------------+-----------+---------------+---------+------------+-----------------+------------+
| first_name | last_name | address    | city      | state         | zip     | phone      | email           | date       |
+------------+-----------+------------+-----------+---------------+---------+------------+-----------------+------------+
| Sanju      | joshi     | Sector 1   | raipur    | Chhattisgarh  | 494001  | 9845632178 | sanju@email.com | 2020-03-19 |
| Zoya       | Khan      | Sector 5   | ambikapur | Chhattisgarh  | 494221  | 9845634578 | zoya@email.com  | 2020-04-20 |
| Aanya      | Pandey    | asokaratan | katni     | MadhyaPradesh | 4920001 | 932974578  | aanya@email.com | 2020-02-01 |
+------------+-----------+------------+-----------+---------------+---------+------------+-----------------+------------+
#UC20 :ADD contact
mysql> select * from address_book;
+------------+-----------+------------+-----------+---------------+---------+------------+-----------------+------------+------+--------+
| first_name | last_name | address    | city      | state         | zip     | phone      | email           | date       | name | type   |
+------------+-----------+------------+-----------+---------------+---------+------------+-----------------+------------+------+--------+
| Sanju      | joshi     | Sector 1   | raipur    | Chhattisgarh  | 494001  | 9845632178 | sanju@email.com | 2020-03-19 | NULL | NULL   |
| Zoya       | Khan      | Sector 5   | ambikapur | Chhattisgarh  | 494221  | 9845634578 | zoya@email.com  | 2020-04-20 | NULL | NULL   |
| Aanya      | Pandey    | asokaratan | katni     | MadhyaPradesh | 4920001 | 932974578  | aanya@email.com | 2020-02-01 | NULL | NULL   |
| Shalini    | Pandey    | Asna       | jagdalpur | Gujrat        | 456789  | 9191902020 | shalu@email.com | 2021-05-24 | name | Family |
+------------+-----------+------------+-----------+---------------+---------+------------+-----------------+------------+------+--------+