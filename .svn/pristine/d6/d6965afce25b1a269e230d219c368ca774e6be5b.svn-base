-- MySQL stored procedures for QPS3 Database 
-- Access Control and Login function Procedure
-- HKMCI appends their change to this DB script after running the SQL init
-- ------------------------------------------------------
-- Server version	5.5-community


use qpsis;



--
-- Procedure `qpsis`.`sp_select_user_status`
--

DROP PROCEDURE IF EXISTS `sp_select_user_status`;
DELIMITER $$

CREATE PROCEDURE `sp_select_user_status`(
 In p_user_id varchar(30),
 IN p_dp_dept_id varchar(10),
 IN p_soa_dept_id varchar(10),
 IN p_password varchar(80),
 OUT return_code INT)
BEGIN
DECLARE EXIT HANDLER FOR NOT FOUND SET return_code = -2;
   SET return_code = 0;
/* check active users from ACL*/
IF (SELECT COUNT(*) FROM acl
      WHERE user_id = p_user_id AND
			password = QPSES_ENCRYPT(user_id,'password',p_password) AND
            effective_date <= CURDATE()) = 1 THEN

   SELECT user_id, soa_department_id, dp_department_id, firstname, lastname, email,
          active_indicator, password_indicator, expiry_date, access_failure_count, login_failure_count,
          login_failure_date, last_access_date
     FROM acl
     WHERE user_id = p_user_id AND
           dp_department_id = p_dp_dept_id AND
           soa_department_id = p_soa_dept_id AND
		   password = QPSES_ENCRYPT(user_id,'password',p_password);
   SET return_code =1;


ELSE
 /* write audit trail for unsuccess login*/
    INSERT INTO access_log
      (dp_department_id,
       user_id,
       access_type,
       access_status,
       date_time)
    VALUES
    ("system",
     p_user_id,
     "IN",
     "WRONG PASSWORD",
     SYSDATE()
    );
	
	IF ((SELECT login_failure_count FROM acl WHERE user_id = p_user_id) < 5 ) THEN
		SET return_code = -1;
		UPDATE acl SET login_failure_count = login_failure_count+1 WHERE user_id = p_user_id;
	ELSE
		SET return_code = -2;
		UPDATE acl SET login_failure_count = login_failure_count+1 WHERE user_id = p_user_id;
		UPDATE acl SET login_failure_date = SYSDATE() WHERE user_id = p_user_id;
	END IF;

END IF;
END $$

DELIMITER ;



--
-- Procedure `qpsis`.`sp_select_security_context`
--

DROP PROCEDURE IF EXISTS `sp_select_security_context`;
DELIMITER $$

CREATE PROCEDURE `sp_select_security_context`(
 IN p_user_id varchar(30),
 OUT return_code INT)
BEGIN
DECLARE EXIT HANDLER FOR NOT FOUND SET return_code = -2;
SET return_code =0;
/* check active users from ACL*/
IF (SELECT COUNT(*) FROM acl
      WHERE user_id = p_user_id AND
            effective_date <= CURDATE()) = 1 THEN
  BEGIN
  /* get user information from ACL*/
    SELECT * FROM acl as a
      WHERE a.user_id = p_user_id;
    SET return_code = 1;
  END;
ELSE
 /* write audit trail for unsuccess login*/
    INSERT INTO access_log
      (dp_department_id,
       user_id,
       access_type,
       access_status,
       date_time)
    VALUES
    ("system",
     p_user_id,
     "IN",
     "UNAUTHORIZED ACCESS",
     SYSDATE()
    );
END IF;
END $$

DELIMITER ;


--
-- Procedure `qpsis`.`sp_select_acl`
--

DROP PROCEDURE IF EXISTS `sp_select_acl`;
DELIMITER $$

CREATE PROCEDURE `sp_select_acl`(IN p_order_by varchar(20),
IN p_order_dir varchar (10))
BEGIN
  IF (p_order_by = "UserId") THEN
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S')
        ORDER BY user_id DESC, bd_name;
    ELSE
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S')
        ORDER BY user_id, bd_name;
    END IF;
  ELSEIF (p_order_by = "EffectiveDate") THEN
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S')
        ORDER BY effective_date DESC, bd_name;
    ELSE
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S')
        ORDER BY effective_date, bd_name;
    END IF;
  ELSEIF (p_order_by = "ExpiryDate") THEN
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S')
        ORDER BY expiry_date DESC, bd_name;
    ELSE
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S') 
        ORDER BY expiry_date, bd_name;
    END IF;
  ELSEIF (p_order_by = "UserRole") THEN
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S')
        ORDER BY user_group DESC, bd_name;
    ELSE
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S') 
        ORDER BY user_group, bd_name;
    END IF;
  ELSE
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S')
        ORDER BY bd_name DESC, user_id;
    ELSE
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_group in ('U','M','SP','S')
        ORDER BY bd_name, user_id;
    END IF;
  END IF;
END $$

DELIMITER ;



--
-- Procedure `qpsis`.`sp_search_acl`
--

DROP PROCEDURE IF EXISTS `sp_search_acl`;
DELIMITER $$

CREATE PROCEDURE `sp_search_acl`(
 IN p_user_id varchar(30),
 IN p_dp_dept_id varchar(10),
 IN p_soa_dept_id varchar(10),
 IN p_user_group varchar(2),
 IN p_active_ind int,
 IN p_expiry_date DATE,
 IN p_order_by varchar(20),
 IN p_order_dir varchar(10))
BEGIN
  IF p_user_id IS NULL THEN
    SET p_user_id = '%';
  ELSE
    SET p_user_id = CONCAT('%',p_user_id,'%');
  END IF;
  IF p_order_by = "UserId" THEN
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_id LIKE p_user_id AND
              a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
              a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
              user_group LIKE IFNULL(p_user_group,'%') AND
              active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
              expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY user_id DESC, bd_name;
    ELSE
        SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
          ON a.dp_department_id = b.dp_department_id AND
             a.soa_department_id = b.soa_department_id
          WHERE user_id LIKE p_user_id AND
                a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
                a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
                user_group LIKE IFNULL(p_user_group,'%') AND
                active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
                expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY user_id, bd_name;
    END IF;
  ELSEIF p_order_by = "UserRole" THEN
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_id LIKE p_user_id AND
              a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
              a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
              user_group LIKE IFNULL(p_user_group,'%') AND
              active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
              expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY user_group DESC, bd_name;
    ELSE
        SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
          ON a.dp_department_id = b.dp_department_id AND
             a.soa_department_id = b.soa_department_id
          WHERE user_id LIKE p_user_id AND
                a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
                a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
                user_group LIKE IFNULL(p_user_group,'%') AND
                active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
                expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY user_group, bd_name;
    END IF;
  ELSEIF p_order_by = "EffectiveDate" THEN
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_id LIKE p_user_id AND
              a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
              a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
              user_group LIKE IFNULL(p_user_group,'%') AND
              active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
              expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY effective_date DESC, bd_name;
    ELSE
        SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
          ON a.dp_department_id = b.dp_department_id AND
             a.soa_department_id = b.soa_department_id
          WHERE user_id LIKE p_user_id AND
                a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
                a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
                user_group LIKE IFNULL(p_user_group,'%') AND
                active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
                expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY effective_date, bd_name;
    END IF;
  ELSEIF p_order_by = "ExpiryDate" THEN
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_id LIKE p_user_id AND
              a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
              a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
              user_group LIKE IFNULL(p_user_group,'%') AND
              active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
              expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY expiry_date DESC, bd_name;
    ELSE
        SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
          ON a.dp_department_id = b.dp_department_id AND
             a.soa_department_id = b.soa_department_id
          WHERE user_id LIKE p_user_id AND
                a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
                a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
                user_group LIKE IFNULL(p_user_group,'%') AND
                active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
                expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY expiry_date, bd_name;
    END IF;
  ELSE
    IF p_order_dir = "DESC" THEN
      SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
        ON a.dp_department_id = b.dp_department_id AND
           a.soa_department_id = b.soa_department_id
        WHERE user_id LIKE p_user_id AND
              a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
              a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
              user_group LIKE IFNULL(p_user_group,'%') AND
              active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
              expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY bd_name DESC, user_id ASC;
    ELSE
        SELECT a.*,b.bd_name FROM acl as a JOIN bd_name as b
          ON a.dp_department_id = b.dp_department_id AND
             a.soa_department_id = b.soa_department_id
          WHERE user_id LIKE p_user_id AND
                a.dp_department_id LIKE IFNULL(p_dp_dept_id,'%') AND
                a.soa_department_id LIKE IFNULL(p_soa_dept_id,'%') AND
                user_group LIKE IFNULL(p_user_group,'%') AND
                active_indicator LIKE IF(p_active_ind =-9, '%',p_active_ind) AND
                expiry_date LIKE IFNULL(p_expiry_date,'%')
        ORDER BY bd_name, user_id;
    END IF;
  END IF;
END $$

DELIMITER ;


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