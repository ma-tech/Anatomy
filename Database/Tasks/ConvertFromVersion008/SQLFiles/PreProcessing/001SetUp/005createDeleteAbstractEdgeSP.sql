DROP PROCEDURE IF EXISTS ANASP_REMOVE_EDGE;
DELIMITER |
CREATE PROCEDURE ANASP_REMOVE_EDGE( IN edgeid INT )
BEGIN
  DECLARE rows INT DEFAULT 0;

  SELECT SQL_CALC_FOUND_ROWS AME_OID FROM ANAM_EDGE
             WHERE AME_OID = edgeid
             AND AME_HOPS = 0;
  SELECT FOUND_ROWS() INTO rows;

  IF (rows > 0) THEN
    DROP TABLE IF EXISTS purge_edge;
    CREATE TABLE purge_edge ( del_id INT );

    INSERT INTO purge_edge 
      SELECT AME_OID
        FROM ANAM_EDGE
          WHERE AME_DIRECT_EDGE_ID = edgeid;

    WHILE rows <> 0 DO
      INSERT INTO purge_edge 
        SELECT AME_OID
          FROM ANAM_EDGE
            WHERE AME_HOPS > 0
            AND (AME_ENTRY_EDGE_ID IN (SELECT del_id FROM purge_edge)
                 OR AME_EXIT_EDGE_ID IN (SELECT del_id FROM purge_edge))
            AND AME_OID NOT IN (SELECT del_id FROM purge_edge);
      SELECT ROW_COUNT() INTO rows;
    END WHILE;

    DELETE FROM ANAM_EDGE
      WHERE AME_OID IN (SELECT del_id FROM purge_edge);

  END IF;
END;
|
DELIMITER ;

