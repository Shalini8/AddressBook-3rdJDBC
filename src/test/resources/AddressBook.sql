#UC16: Retrieve data and check no of users.
mysql> select * from userdetails;
+---------+------------+-----------+----------+-----------+--------------+---------+------------+-----------------+
| user_id | first_name | last_name | address  | city      | state        | zip     | phone      | email           |
+---------+------------+-----------+----------+-----------+--------------+---------+------------+-----------------+
|       1 | Shaan      | Raut      | Sector 1 | jagdalpur | Chhattisgarh | 123456  | 9876510123 | shaan@email.com |
|       2 | Sanju      | joshi     | Sector 1 | raipur    | Chhattisgarh | 494001  | 9845632178 | sanju@email.com |
|       3 | Shalini    | Pandey    | Asna     | jagdalpur | Chhattisgarh | 4942221 | 9845634578 | shalu@email.com |
+---------+------------+-----------+----------+-----------+--------------+---------+------------+-----------------+