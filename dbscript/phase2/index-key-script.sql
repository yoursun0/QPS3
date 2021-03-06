-- MySQL script for QPS3 Database
-- Redesign DB schema for indexing and foreign keys
-- HKMCI appends their change to this DB script after running all phrase1 SQL script
-- ------------------------------------------------------
-- Server version 5.5-community

use qpsis;

--
-- Create index for search filter
--

-- DROP INDEX `idx_work_assignment_1` ON `work_assignment`;
CREATE INDEX `idx_work_assignment_1`
ON `work_assignment` (department_id,assignment_status);

-- DROP INDEX `idx_work_assignment_2` ON `work_assignment`;
CREATE INDEX `idx_work_assignment_2`
ON `work_assignment` (department_id,service_category_group,assignment_status);

-- DROP INDEX `idx_work_assignment_3` ON `work_assignment`;
CREATE INDEX `idx_work_assignment_3`
ON `work_assignment` (issue_date,awarded_date);

-- DROP INDEX `idx_work_assignment_4` ON `work_assignment`;
CREATE INDEX `idx_work_assignment_4`
ON `work_assignment` (`service_contract_no`);


-- DROP INDEX `idx_acl_1` ON `acl`;
CREATE INDEX `idx_acl_1`
ON `acl` (user_group,user_id);

-- DROP INDEX `idx_acl_2` ON `acl`;
CREATE INDEX `idx_acl_2`
ON `acl` (dp_department_id,soa_department_id);

-- DROP INDEX `idx_acl_3` ON `acl`;
CREATE INDEX `idx_acl_3`
ON `acl` (user_id,dp_department_id,soa_department_id);

-- DROP INDEX `idx_contractor_1` ON `contractor`;
CREATE INDEX `idx_contractor_1`
ON `contractor` (`1J_indicator`,`2N_indicator`,`2J_indicator`,`3N_indicator`,`3J_indicator`,`4J_indicator`);

-- DROP INDEX `idx_avg_ceiling_rate_1` ON `average_ceiling_rate`;
CREATE INDEX `idx_avg_ceiling_rate_1`
ON `average_ceiling_rate` (`effective_date`);

-- DROP INDEX `idx_ceiling_rate_1` ON `ceiling_rate_1`;
CREATE INDEX `idx_ceiling_rate_1`
ON `ceiling_rate_1` (`effective_date`);

-- DROP INDEX `idx_ceiling_rate_2` ON `ceiling_rate_2`;
CREATE INDEX `idx_ceiling_rate_2`
ON `ceiling_rate_2` (`effective_date`);

-- DROP INDEX `idx_ceiling_rate_3` ON `ceiling_rate_3`;
CREATE INDEX `idx_ceiling_rate_3`
ON `ceiling_rate_3` (`effective_date`);

-- DROP INDEX `idx_ceiling_rate_4` ON `ceiling_rate_4`;
CREATE INDEX `idx_ceiling_rate_4`
ON `ceiling_rate_4` (`effective_date`);

-- DROP INDEX `idx_quality_subscore_1` ON `quality_subscore`;
CREATE INDEX `idx_quality_subscore_1`
ON `quality_subscore` (`effective_period_id`);

-- DROP INDEX `idx_cpar_1` ON `cpar`;
CREATE INDEX `idx_cpar_1`
ON `cpar` (`service_category_group`,`project_file_part`,`project_file_no`,`department_id`);

-- DROP INDEX `idx_debarrment_1` ON `debarrment`;
CREATE INDEX `idx_debarrment_1`
ON `debarrment` (`contractor_id`);

--
-- Alter Tables to Add some foreign keys
--

ALTER TABLE `audit_trail` 
ADD CONSTRAINT `fk_audit_trail` 
FOREIGN KEY (`dp_department_id`,`soa_department_id`)
REFERENCES `bd_name` (`dp_department_id`,`soa_department_id`)
ON UPDATE CASCADE;

ALTER TABLE `staff_rate_validation_log` 
ADD CONSTRAINT `fk_staff_rate_validation_log` 
FOREIGN KEY (`dp_department_id`,`soa_department_id`)
REFERENCES `bd_name` (`dp_department_id`,`soa_department_id`)
ON UPDATE CASCADE;

ALTER TABLE `quality_subscore` 
ADD CONSTRAINT `fk_quality_subscore_2` 
FOREIGN KEY (`effective_period_id`)
REFERENCES `quality_subscore_effective_period` (`effective_period_id`)
ON UPDATE CASCADE;

ALTER TABLE `cpar_history` 
ADD CONSTRAINT `fk_cpar_history` 
FOREIGN KEY (`service_category_group`,`project_file_part`,`project_file_no`,`department_id`,`cpar_no`)
REFERENCES `cpar` (`service_category_group`,`project_file_part`,`project_file_no`,`department_id`,`cpar_no`)
ON UPDATE CASCADE
ON DELETE CASCADE;

Commit;


