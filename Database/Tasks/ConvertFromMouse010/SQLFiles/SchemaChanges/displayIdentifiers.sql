SELECT 
  (CASE LENGTH(SUBSTR(ANO_PUBLIC_ID,7))
    WHEN 6 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '0', SUBSTR(ANO_PUBLIC_ID,7,6))
    WHEN 5 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '00', SUBSTR(ANO_PUBLIC_ID,7,5))
    WHEN 4 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '000', SUBSTR(ANO_PUBLIC_ID,7,4))
    WHEN 3 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '0000', SUBSTR(ANO_PUBLIC_ID,7,3))
    WHEN 2 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '00000', SUBSTR(ANO_PUBLIC_ID,7,2))
    WHEN 1 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '000000', SUBSTR(ANO_PUBLIC_ID,7,1))
    ELSE 'Error!!! No Digits!'
  END) AS ANO_DISPLAY_ID
FROM ANA_NODE
ORDER BY ANO_DISPLAY_ID
LIMIT 0,30000;

SELECT 
  (CASE LENGTH(SUBSTR(ATN_PUBLIC_ID,6))
    WHEN 5 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '00', SUBSTR(ATN_PUBLIC_ID,6,5))
    WHEN 4 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '000', SUBSTR(ATN_PUBLIC_ID,6,4))
    WHEN 3 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '0000', SUBSTR(ATN_PUBLIC_ID,6,3))
    WHEN 2 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '00000', SUBSTR(ATN_PUBLIC_ID,6,2))
    WHEN 1 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '000000', SUBSTR(ATN_PUBLIC_ID,6,1))
    ELSE 'Error!!! No Digits!'
  END) as ATN_DISPLAY_ID
FROM ANA_TIMED_NODE
ORDER BY ATN_DISPLAY_ID
LIMIT 0,30000;


ALTER TABLE ANA_TIMED_NODE ADD COLUMN ATN_DISPLAY_ID varchar(20) NOT NULL;

ALTER TABLE ANA_NODE ADD COLUMN ANO_DISPLAY_ID varchar(20) NOT NULL;

UPDATE ANA_NODE SET ANO_DISPLAY_ID =
  (CASE LENGTH(SUBSTR(ANO_PUBLIC_ID,7))
    WHEN 6 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '0', SUBSTR(ANO_PUBLIC_ID,7,6))
    WHEN 5 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '00', SUBSTR(ANO_PUBLIC_ID,7,5))
    WHEN 4 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '000', SUBSTR(ANO_PUBLIC_ID,7,4))
    WHEN 3 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '0000', SUBSTR(ANO_PUBLIC_ID,7,3))
    WHEN 2 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '00000', SUBSTR(ANO_PUBLIC_ID,7,2))
    WHEN 1 THEN CONCAT(SUBSTR(ANO_PUBLIC_ID,1,6), '000000', SUBSTR(ANO_PUBLIC_ID,7,1))
    ELSE 'Error!!! No Digits!'
  END)
  
UPDATE ANA_TIMED_NODE SET ATN_DISPLAY_ID =
  (CASE LENGTH(SUBSTR(ATN_PUBLIC_ID,6))
    WHEN 5 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '00', SUBSTR(ATN_PUBLIC_ID,6,5))
    WHEN 4 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '000', SUBSTR(ATN_PUBLIC_ID,6,4))
    WHEN 3 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '0000', SUBSTR(ATN_PUBLIC_ID,6,3))
    WHEN 2 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '00000', SUBSTR(ATN_PUBLIC_ID,6,2))
    WHEN 1 THEN CONCAT(SUBSTR(ATN_PUBLIC_ID,1,5), '000000', SUBSTR(ATN_PUBLIC_ID,6,1))
    ELSE 'Error!!! No Digits!'
  END)
  