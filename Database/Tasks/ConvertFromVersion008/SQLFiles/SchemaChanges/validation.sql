select ano_public_id, ano_component_name from ana_node

--NEW COMPONENTS

--identify new components
SELECT aoc_obo_id FROM ANA_OBO_COMPONENT
where aoc_obo_id not in (select ano_public_id from ana_node)
and SUBSTRING(aoc_obo_id,1,2) <> 'TS'
and SUBSTRING(aoc_obo_id,1,4) <> 'EMAP'
and SUBSTRING(aoc_obo_id,1,8) <> 'Tmp_new_'

--CHANGED NAMES

--identify changed component names - 1971 name changes!
SELECT aoc_obo_id, aoc_name, ano_component_name
FROM ANA_OBO_COMPONENT
join ana_node on ano_public_id = aoc_obo_id
where aoc_name <> ano_component_name

--IDENTIFY GROUP TERMS

-- Group terms
SELECT * FROM ANA_OBO_COMPONENT_RELATIONSHIP
where acr_obo_parent = 'group_term'

SELECT * FROM ANA_OBO_COMPONENT_RELATIONSHIP
where acr_obo_type = 'group_part_of'


--CHANGED STAGE RANGES

--identify changed component stage starts and ends - 59 changes!
SELECT aoc_obo_id, a.stg_sequence as NEW_START, b.stg_sequence as NEW_END, ano_public_id, anav_stage_min as OLD_START, anav_stage_max as OLD_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node on ano_public_id = aoc_obo_id
join anav_stage_range on anav_node_fk = ano_oid
where a.stg_sequence <> anav_stage_min
or b.stg_sequence <> anav_stage_max

--Earlier Start - 29
SELECT aoc_obo_id, a.stg_sequence as NEW_START, b.stg_sequence as NEW_END, ano_public_id, anav_stage_min as OLD_START, anav_stage_max as OLD_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node on ano_public_id = aoc_obo_id
join anav_stage_range on anav_node_fk = ano_oid
where a.stg_sequence < anav_stage_min
AND b.stg_sequence = anav_stage_max

--Earlier End - 0
SELECT aoc_obo_id, a.stg_sequence as NEW_START, b.stg_sequence as NEW_END, ano_public_id, anav_stage_min as OLD_START, anav_stage_max as OLD_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node on ano_public_id = aoc_obo_id
join anav_stage_range on anav_node_fk = ano_oid
where a.stg_sequence = anav_stage_min
AND b.stg_sequence < anav_stage_max

--Later Start - 0
SELECT aoc_obo_id, a.stg_sequence as NEW_START, b.stg_sequence as NEW_END, ano_public_id, anav_stage_min as OLD_START, anav_stage_max as OLD_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node on ano_public_id = aoc_obo_id
join anav_stage_range on anav_node_fk = ano_oid
where a.stg_sequence > anav_stage_min
AND b.stg_sequence = anav_stage_max

--Later End - 30
SELECT aoc_obo_id, a.stg_sequence as NEW_START, b.stg_sequence as NEW_END, ano_public_id, anav_stage_min as OLD_START, anav_stage_max as OLD_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node on ano_public_id = aoc_obo_id
join anav_stage_range on anav_node_fk = ano_oid
where a.stg_sequence = anav_stage_min
AND b.stg_sequence > anav_stage_max

--Earlier Start && Earlier End - 0
SELECT aoc_obo_id, a.stg_sequence as NEW_START, b.stg_sequence as NEW_END, ano_public_id, anav_stage_min as OLD_START, anav_stage_max as OLD_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node on ano_public_id = aoc_obo_id
join anav_stage_range on anav_node_fk = ano_oid
where a.stg_sequence < anav_stage_min
AND b.stg_sequence < anav_stage_max

--Later Start && Later End - 0
SELECT aoc_obo_id, a.stg_sequence as NEW_START, b.stg_sequence as NEW_END, ano_public_id, anav_stage_min as OLD_START, anav_stage_max as OLD_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node on ano_public_id = aoc_obo_id
join anav_stage_range on anav_node_fk = ano_oid
where a.stg_sequence > anav_stage_min
AND b.stg_sequence > anav_stage_max

--Later Start && Earlier End - 0
SELECT aoc_obo_id, a.stg_sequence as NEW_START, b.stg_sequence as NEW_END, ano_public_id, anav_stage_min as OLD_START, anav_stage_max as OLD_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node on ano_public_id = aoc_obo_id
join anav_stage_range on anav_node_fk = ano_oid
where a.stg_sequence > anav_stage_min
AND b.stg_sequence < anav_stage_max

--Earlier Start && Later End - 0
SELECT aoc_obo_id, a.stg_sequence as NEW_START, b.stg_sequence as NEW_END, ano_public_id, anav_stage_min as OLD_START, anav_stage_max as OLD_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node on ano_public_id = aoc_obo_id
join anav_stage_range on anav_node_fk = ano_oid
where a.stg_sequence < anav_stage_min
AND b.stg_sequence > anav_stage_max


--VALIDATE STAGE RANGES

--Check EXISTING COMPONENTS Child Stage Range is within Existing Parent Stage Range - 43
SELECT 
aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END, 
h.ano_public_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END
FROM ANA_OBO_COMPONENT
join ana_stage a on a.stg_name = aoc_start
join ana_stage b on b.stg_name = aoc_end
join ana_node g on g.ano_public_id = aoc_obo_id
join ana_relationship on g.ano_oid = rel_child_fk
join ana_node h on h.ano_oid = rel_parent_fk
join anav_stage_range on anav_node_fk = h.ano_oid
join ana_stage c on c.stg_sequence = anav_stage_min
join ana_stage d on d.stg_sequence = anav_stage_max
where a.stg_sequence < anav_stage_min
or b.stg_sequence > anav_stage_max

--Check EXISTING COMPONENTS Child Stage Range is within Proposed Parent Stage Range - 4
SELECT 
e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END,
f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END
FROM ANA_OBO_COMPONENT e
join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id
join ana_obo_component f on f.aoc_obo_id = acr_obo_parent
join ana_stage a on a.stg_name = e.aoc_start
join ana_stage b on b.stg_name = e.aoc_end
join ana_stage c on c.stg_name = f.aoc_start
join ana_stage d on d.stg_name = f.aoc_end
where substring(e.aoc_obo_id, 1, 3) <> 'TH:'
and substring(f.aoc_obo_id, 1, 3) = 'TH:'
and (a.stg_sequence < c.stg_sequence
or b.stg_sequence > d.stg_sequence)

--Check PROPOSED COMPONENTS Child Node Ranges are within Proposed Parent Stage Ranges - 8
SELECT 
e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END,
f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END
FROM ANA_OBO_COMPONENT e
join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id
join ana_obo_component f on f.aoc_obo_id = acr_obo_parent
join ana_stage a on a.stg_name = e.aoc_start
join ana_stage b on b.stg_name = e.aoc_end
join ana_stage c on c.stg_name = f.aoc_start
join ana_stage d on d.stg_name = f.aoc_end
where substring(e.aoc_obo_id, 1, 3) = 'TH:'
and substring(f.aoc_obo_id, 1, 3) = 'TH:'
and (a.stg_sequence < c.stg_sequence
or b.stg_sequence > d.stg_sequence)

--Check PROPOSED NEW Child Node Ranges are within EXISTING Parent Stage Ranges - 1
SELECT 
e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END,
f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END
FROM ANA_OBO_COMPONENT e
join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id
join ana_obo_component f on f.aoc_obo_id = acr_obo_parent
join ana_stage a on a.stg_name = e.aoc_start
join ana_stage b on b.stg_name = e.aoc_end
join ana_stage c on c.stg_name = f.aoc_start
join ana_stage d on d.stg_name = f.aoc_end
where substring(e.aoc_obo_id, 1, 3) = 'TH:'
and substring(f.aoc_obo_id, 1, 3) <> 'TH:'
and (a.stg_sequence < c.stg_sequence
or b.stg_sequence > d.stg_sequence)