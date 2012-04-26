DROP PROCEDURE IF EXISTS ANASP_ADD_EDGE;
DELIMITER |
CREATE PROCEDURE ANASP_ADD_EDGE( IN childnodeid VARCHAR(100),
                                 IN childminstage VARCHAR(100),
                                 IN childmaxstage VARCHAR(100),
                                 IN parentnodeid VARCHAR(100),
                                 IN parentminstage VARCHAR(100),
                                 IN parentmaxstage VARCHAR(100),
                                 IN sourceid VARCHAR(150) )
BEGIN
  DECLARE rows, id INT DEFAULT 0;

  IF (childnodeid != parentnodeid) THEN
    SELECT SQL_CALC_FOUND_ROWS AME_OID FROM ANAM_EDGE
              WHERE AME_CHILD_NODE = childnodeid
              AND AME_PARENT_NODE = parentnodeid
              AND AME_HOPS = 0;
    SELECT FOUND_ROWS() INTO rows;
    IF (rows <= 0) THEN
      SELECT SQL_CALC_FOUND_ROWS AME_OID FROM ANAM_EDGE
                WHERE AME_CHILD_NODE = parentnodeid
                AND AME_PARENT_NODE = childnodeid;
      SELECT FOUND_ROWS() INTO rows;
      IF (rows <= 0) THEN
        -- Insert the first edge
        INSERT INTO ANAM_EDGE 
            (AME_CHILD_NODE,
             AME_CHILD_STG_MIN,
             AME_CHILD_STG_MAX,
             AME_PARENT_NODE,
             AME_PARENT_STG_MIN,
             AME_PARENT_STG_MAX,
             AME_HOPS,
             AME_SOURCE)
          VALUES 
            (childnodeid,
             childminstage,
             childmaxstage,
             parentnodeid,
             parentminstage,
             parentmaxstage,
             0,
             sourceid);
        SELECT MAX(AME_OID) INTO id FROM ANAM_EDGE;
        UPDATE ANAM_EDGE
          SET AME_ENTRY_EDGE_ID = id,
              AME_EXIT_EDGE_ID = id,
              AME_DIRECT_EDGE_ID = id
          WHERE AME_OID = id;

        -- step 1: A's incoming edges to B
        INSERT INTO ANAM_EDGE (
          AME_ENTRY_EDGE_ID,
          AME_DIRECT_EDGE_ID,
          AME_EXIT_EDGE_ID,
          AME_CHILD_NODE,
          AME_CHILD_STG_MIN,
          AME_CHILD_STG_MAX,
          AME_PARENT_NODE,
          AME_PARENT_STG_MIN,
          AME_PARENT_STG_MAX,
          AME_HOPS,
          AME_SOURCE)
          SELECT AME_OID,
                 id,
                 id, 
                 AME_CHILD_NODE,
                 AME_CHILD_STG_MIN,
                 AME_CHILD_STG_MAX,
                 parentnodeid,
                 parentminstage,
                 parentmaxstage,
                 AME_HOPS + 1,
                 sourceid
          FROM ANAM_EDGE
          WHERE AME_PARENT_NODE = childnodeid;

        -- step 2: A to B's outgoing edges
        INSERT INTO ANAM_EDGE (
          AME_ENTRY_EDGE_ID,
          AME_DIRECT_EDGE_ID,
          AME_EXIT_EDGE_ID,
          AME_CHILD_NODE,
          AME_CHILD_STG_MIN,
          AME_CHILD_STG_MAX,
          AME_PARENT_NODE,
          AME_PARENT_STG_MIN,
          AME_PARENT_STG_MAX,
          AME_HOPS,
          AME_SOURCE)
          SELECT id,
                 id,
                 AME_OID, 
                 childnodeid,
                 childminstage,
                 childmaxstage,
                 AME_PARENT_NODE,
                 AME_PARENT_STG_MIN,
                 AME_PARENT_STG_MAX,
                 AME_HOPS + 1,
                 sourceid
          FROM ANAM_EDGE
          WHERE AME_CHILD_NODE = parentnodeid;

        -- step 3: A 's incoming edges to end node of B's outgoing edges
        INSERT INTO ANAM_EDGE (
          AME_ENTRY_EDGE_ID,
          AME_DIRECT_EDGE_ID,
          AME_EXIT_EDGE_ID,
          AME_CHILD_NODE,
          AME_CHILD_STG_MIN,
          AME_CHILD_STG_MAX,
          AME_PARENT_NODE,
          AME_PARENT_STG_MIN,
          AME_PARENT_STG_MAX,
          AME_HOPS,
          AME_SOURCE)
          SELECT a.AME_OID,
                 id,
                 b.AME_OID, 
                 a.AME_CHILD_NODE,
                 a.AME_CHILD_STG_MIN,
                 a.AME_CHILD_STG_MAX,
                 b.AME_PARENT_NODE,
                 b.AME_PARENT_STG_MIN,
                 b.AME_PARENT_STG_MAX,
                 a.AME_HOPS + b.AME_HOPS + 1,
                 sourceid
          FROM ANAM_EDGE a
          CROSS JOIN ANAM_EDGE b
          WHERE a.AME_PARENT_NODE = childnodeid
          AND b.AME_CHILD_NODE = parentnodeid;

      END IF;
    END IF;
  END IF;
END;
|
DELIMITER ;

