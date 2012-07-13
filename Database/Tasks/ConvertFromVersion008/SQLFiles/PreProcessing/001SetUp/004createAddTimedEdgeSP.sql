DROP PROCEDURE IF EXISTS ANASP_ADD_TIMED_EDGE;
DELIMITER |
CREATE PROCEDURE ANASP_ADD_TIMED_EDGE( IN childnodeid VARCHAR(100),
                                 IN parentnodeid VARCHAR(100),
                                 IN stage VARCHAR(100),
                                 IN sourceid VARCHAR(150) )
BEGIN
  DECLARE rows, id INT DEFAULT 0;

  IF (childnodeid != parentnodeid) THEN
    SELECT SQL_CALC_FOUND_ROWS AME_T_OID FROM ANAM_TIMED_EDGE
              WHERE AME_T_CHILD_NODE = childnodeid
              AND AME_T_PARENT_NODE = parentnodeid
              AND AME_T_HOPS = 0;
    SELECT FOUND_ROWS() INTO rows;
    IF (rows <= 0) THEN
      SELECT SQL_CALC_FOUND_ROWS AME_T_OID FROM ANAM_TIMED_EDGE
                WHERE AME_T_CHILD_NODE = parentnodeid
                AND AME_T_PARENT_NODE = childnodeid;
      SELECT FOUND_ROWS() INTO rows;
      IF (rows <= 0) THEN
        -- Insert the first edge
        INSERT INTO ANAM_TIMED_EDGE 
            (AME_T_CHILD_NODE,
             AME_T_PARENT_NODE,
             AME_T_STAGE,
             AME_T_HOPS,
             AME_T_SOURCE)
          VALUES 
            (childnodeid,
             parentnodeid,
             stage,
             0,
             sourceid);
        SELECT MAX(AME_T_OID) INTO id FROM ANAM_TIMED_EDGE;
        UPDATE ANAM_TIMED_EDGE
          SET AME_T_ENTRY_EDGE_ID = id,
              AME_T_EXIT_EDGE_ID = id,
              AME_T_DIRECT_EDGE_ID = id
          WHERE AME_T_OID = id;

        -- step 1: A's incoming edges to B
        INSERT INTO ANAM_TIMED_EDGE (
          AME_T_ENTRY_EDGE_ID,
          AME_T_DIRECT_EDGE_ID,
          AME_T_EXIT_EDGE_ID,
          AME_T_CHILD_NODE,
          AME_T_PARENT_NODE,
          AME_T_STAGE,
          AME_T_HOPS,
          AME_T_SOURCE)
          SELECT AME_T_OID,
                 id,
                 id, 
                 AME_T_CHILD_NODE,
                 parentnodeid,
                 stage,
                 AME_T_HOPS + 1,
                 sourceid
          FROM ANAM_TIMED_EDGE
          WHERE AME_T_PARENT_NODE = childnodeid;

        -- step 2: A to B's outgoing edges
        INSERT INTO ANAM_TIMED_EDGE (
          AME_T_ENTRY_EDGE_ID,
          AME_T_DIRECT_EDGE_ID,
          AME_T_EXIT_EDGE_ID,
          AME_T_CHILD_NODE,
          AME_T_PARENT_NODE,
          AME_T_STAGE,
          AME_T_HOPS,
          AME_T_SOURCE)
          SELECT id,
                 id,
                 AME_T_OID, 
                 childnodeid,
                 AME_T_PARENT_NODE,
                 AME_T_STAGE,
                 AME_T_HOPS + 1,
                 sourceid
          FROM ANAM_TIMED_EDGE
          WHERE AME_T_CHILD_NODE = parentnodeid;

        -- step 3: A 's incoming edges to end node of B's outgoing edges
        INSERT INTO ANAM_TIMED_EDGE (
          AME_T_ENTRY_EDGE_ID,
          AME_T_DIRECT_EDGE_ID,
          AME_T_EXIT_EDGE_ID,
          AME_T_CHILD_NODE,
          AME_T_PARENT_NODE,
          AME_T_STAGE,
          AME_T_HOPS,
          AME_T_SOURCE)
          SELECT a.AME_T_OID,
                 id,
                 b.AME_T_OID, 
                 a.AME_T_CHILD_NODE,
                 b.AME_T_PARENT_NODE,
                 b.AME_T_STAGE,
                 a.AME_T_HOPS + b.AME_T_HOPS + 1,
                 sourceid
          FROM ANAM_TIMED_EDGE a
          CROSS JOIN ANAM_TIMED_EDGE b
          WHERE a.AME_T_PARENT_NODE = childnodeid
          AND b.AME_T_CHILD_NODE = parentnodeid;

      END IF;
    END IF;
  END IF;
END;
|
DELIMITER ;

