INSERT INTO currency (denomination) SELECT 'Ruble' WHERE NOT EXISTS (SELECT * FROM currency WHERE denomination = 'Ruble');
INSERT INTO currency (denomination) SELECT 'USD' WHERE NOT EXISTS (SELECT * FROM currency WHERE denomination = 'USD');
INSERT INTO currency (denomination) SELECT 'Sterling' WHERE NOT EXISTS (SELECT * FROM currency WHERE denomination = 'Sterling');
INSERT INTO currency (denomination) SELECT 'Tenge' WHERE NOT EXISTS (SELECT * FROM currency WHERE denomination = 'Tenge');

INSERT INTO credit_type (currency_id, max_amount, min_amount, name, rate, repayment_time) SELECT 1,300000,100000, 'Useful',5.5,10 WHERE NOT EXISTS (SELECT * FROM credit_type WHERE name = 'Useful');
INSERT INTO credit_type (currency_id, max_amount, min_amount, name, rate, repayment_time) SELECT 2,50000,1000,'America',11.5,10 WHERE NOT EXISTS (SELECT * FROM credit_type WHERE name = 'America');
INSERT INTO credit_type (currency_id, max_amount, min_amount, name, rate, repayment_time) SELECT 3,40500,5000,'For real',7.5,15 WHERE NOT EXISTS (SELECT * FROM credit_type WHERE name = 'For real');
INSERT INTO credit_type (currency_id, max_amount, min_amount, name, rate, repayment_time) SELECT 4,90000,10000,'Europe moment',2,13 WHERE NOT EXISTS (SELECT * FROM credit_type WHERE name = 'Europe moment');
INSERT INTO credit_type (currency_id, max_amount, min_amount, name, rate, repayment_time) SELECT 2,1000000,50000, 'Mediocre',55,100 WHERE NOT EXISTS (SELECT * FROM credit_type WHERE name = 'Mediocre');

INSERT INTO deposit_type (max_amount, min_amount, name, rate) SELECT 600000, 300000, 'Snow', 1.2 WHERE NOT EXISTS (SELECT * FROM deposit_type WHERE name = 'Snow');
INSERT INTO deposit_type (max_amount, min_amount, name, rate) SELECT 8000000, 400000, 'Little deposit', 1.5 WHERE NOT EXISTS (SELECT * FROM deposit_type WHERE name = 'Little deposit');
INSERT INTO deposit_type (max_amount, min_amount, name, rate) SELECT 2000000, 1000000, 'Double trouble', 3 WHERE NOT EXISTS (SELECT * FROM deposit_type WHERE name = 'Double trouble');