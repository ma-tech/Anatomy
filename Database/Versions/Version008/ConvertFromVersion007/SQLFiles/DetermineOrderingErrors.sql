SELECT rel_oid, rlp_sequence, a.ano_oid, a.ano_public_id as Parent, a.ano_component_name as Parent_Name, b.ano_oid, b.ano_public_id as Child, b.ano_component_name as Child_Name
FROM ANA_RELATIONSHIP
join ANA_NODE a on a.ano_oid = rel_parent_fk
join ANA_NODE b on b.ano_oid = rel_child_fk
join ANA_RELATIONSHIP_PROJECT on rel_oid = rlp_relationship_fk
where a.ano_public_id = 'EMAPA:19180'
and RLP_PROJECT_FK = 'GUDMAP'
order by rlp_sequence