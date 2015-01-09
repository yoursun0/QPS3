-- MySQL script for QPS3 Database
-- Redesign DB schema for Access control function
-- HKMCI appends their change to this DB script after running all phase 1 SQL script
-- ------------------------------------------------------
-- Server version 5.5-community

use qpsis;

ALTER TABLE acl ADD COLUMN `email_indicator` TINYINT(1);
UPDATE acl SET `email_indicator` = '0' WHERE `user_group` = 'U' or `user_group` = 'M';
UPDATE acl SET `email_indicator` = '-1' WHERE `user_group` = 'S' or `user_group` = 'SP';

--
-- Procedure `qpsis`.`sp_insert_acl`
--

DROP PROCEDURE IF EXISTS `sp_insert_acl`;
DELIMITER $$

CREATE PROCEDURE `sp_insert_acl`(IN p_user_id varchar(30),
 IN p_user_dp_dept_id varchar(10),
 IN p_user_soa_dept_id varchar(10),

 IN p_firstname varchar(80),
 IN p_lastname varchar(80),
 IN p_email varchar(80),
 IN p_password varchar(80),
 IN p_user_group varchar(2),
 IN p_email_ind int,
 IN p_effective_date DATE,
 IN p_expiry_date DATE,
 IN p_admin_id varchar(30),
 IN p_dp_dept_id varchar(10),
 IN p_soa_dept_id varchar(10),
 OUT return_code INT)
BEGIN
  DECLARE EXIT HANDLER FOR NOT FOUND SET return_code = -2;
  SET @progID = p_admin_id;
  SET @adminID = p_dp_dept_id;
  SET @dpDeptId = p_dp_dept_id;
  SET @soaDeptId = p_soa_dept_id;
  SET @remarks = concat('ADD User: ',p_user_id,' to Dept: ',p_user_soa_dept_id);
  IF (SELECT COUNT(*) from acl
        WHERE user_id = p_user_id AND
              dp_department_id = p_user_dp_dept_id AND
              soa_department_id = p_user_soa_dept_id> 0) THEN
	BEGIN
		SET return_code = -99;
		INSERT INTO audit_trail(
		user_id, dp_department_id, soa_department_id, function_id,
		entity_name, record_id, action_performed, date_time, remarks
		)
		VALUES
		(
		@adminID, @dpDeptId, @soaDeptId, 'ACLADD',
		'Access Control List Maintainence', 'Fail', 'A', SYSDATE(), concat('ADD User: ',p_user_id,' fail')
		);
	END;
  ELSE
    BEGIN
      INSERT INTO acl
      (user_id,
       dp_department_id,
       soa_department_id,
       user_group,
       firstname,
       lastname,
       email,
       password,
       active_indicator,
       password_indicator,
	   email_indicator,
	   login_failure_count,
       created_datetime,
       last_updated_datetime,
       last_updated_by_user,
       effective_date,
       expiry_date,
       access_failure_count)
      Values
      (p_user_id,
       p_user_dp_dept_id,
       p_user_soa_dept_id,
       p_user_group,
       p_firstname,
       p_lastname,
       p_email,
       QPSES_ENCRYPT(p_user_id,"password",p_password),
       -1,
	   0,
	   p_email_ind,
       0,
       SYSDATE(),
       SYSDATE(),
       p_admin_id,
       p_effective_date,
       p_expiry_date,
       0);
	INSERT INTO audit_trail(
		user_id, dp_department_id, soa_department_id, function_id,
		entity_name, record_id, action_performed, date_time, remarks
		)
		VALUES
		(
		@adminID, @dpDeptId, @soaDeptId, 'ACLADD',
		'Access Control List Maintainence', p_user_id, 'A', SYSDATE(), @remarks
		);
    SET return_code = 1;
  END;
END IF;
END $$

DELIMITER ;


--
-- Procedure `qpsis`.`sp_update_acl`
--

DROP PROCEDURE IF EXISTS `sp_update_acl`;
DELIMITER $$

CREATE PROCEDURE `sp_update_acl`(
 IN p_effective_date Date,
 IN p_expiry_date Date,
 IN p_active_ind int,
 IN p_email_ind int,
 IN p_password varchar(80),
 IN p_admin_id varchar(30),
 IN p_dp_dept_id varchar(10),
 IN p_soa_dept_id varchar(10),
 IN p_key1 varchar(10),
 IN p_key2 varchar(10),
 IN p_key3 varchar(30),
 OUT return_code INT)
BEGIN
  DECLARE EXIT HANDLER FOR NOT FOUND SET return_code = -2;
  SET @progID ="ACLUPDATE";
  SET @dpDeptId = p_dp_dept_id;
  SET @soaDeptId = p_soa_dept_id;
  SET @remarks = concat('UPDATE User ',p_key3,': active_indicator =',p_active_ind,' +  Effective Date=',p_effective_date,' +  Expiry Date=',p_expiry_date);
  SET return_code =0;
  IF (p_password = 'NO') THEN
	UPDATE acl SET
       active_indicator = p_active_ind,
	   email_indicator = p_email_ind,
       last_updated_datetime = SYSDATE(),
       last_updated_by_user = p_admin_id,
       effective_date = p_effective_date,
       expiry_date = p_expiry_date
       WHERE dp_department_id = p_key1 AND
             soa_department_id = p_key2 AND
             user_id = p_key3;
  ELSE
      UPDATE acl SET
       active_indicator = p_active_ind,
	   email_indicator = p_email_ind,
       last_updated_datetime = SYSDATE(),
       last_updated_by_user = p_admin_id,
       effective_date = p_effective_date,
       expiry_date = p_expiry_date,
       password = QPSES_ENCRYPT(p_key3,"password",p_password)
       WHERE dp_department_id = p_key1 AND
             soa_department_id = p_key2 AND
             user_id = p_key3;
  END IF;
      INSERT INTO audit_trail(
		user_id, dp_department_id, soa_department_id, function_id,
		entity_name, record_id, action_performed, date_time, remarks
		)
		VALUES
		(
		p_admin_id, p_dp_dept_id, p_soa_dept_id, 'ACLUPDATE',
		'Access Control List Maintainence', p_key3, 'A', SYSDATE(), @remarks
		);
       SET return_code = 1;
END $$

DELIMITER ;


COMMIT;