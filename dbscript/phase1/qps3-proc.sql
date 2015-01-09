-- MySQL stored procedures for QPS3 Database
-- QPS3 general stored procedure
-- HKMCI appends their change to this DB script after running the SQL init
-- ------------------------------------------------------
-- Server version	5.5-community


use qpsis;

--
-- Procedure `qpsis`.`sp_select_dept_by_keys`
--

DROP PROCEDURE IF EXISTS `sp_select_dept_by_keys`;
DELIMITER $$

CREATE PROCEDURE `sp_select_dept_by_keys`(IN p_dp_id char(10),
IN p_soa_id char(10)
)
BEGIN
    SELECT * FROM bd_name WHERE dp_department_id = p_dp_id AND soa_department_id = p_soa_id ORDER BY bd_name;
END $$

DELIMITER ;


--
-- Function `qpsis`.`QPSES_ENCRYPT`
--

DROP FUNCTION IF EXISTS `QPSES_ENCRYPT`;
DELIMITER $$

CREATE FUNCTION `QPSES_ENCRYPT`(p_key_str VARCHAR(1024), p_field_name VARCHAR(1024), p_field_value VARCHAR(1024)) RETURNS blob
BEGIN 
  RETURN AES_ENCRYPT(p_field_value, SHA1(CONCAT(p_key_str, p_field_name)));
END $$

DELIMITER ;

--
-- Procedure `qpsis`.`sp_create_temp_work_assignment`
--

DROP PROCEDURE IF EXISTS `sp_create_temp_work_assignment`;
DELIMITER $$

CREATE PROCEDURE `sp_create_temp_work_assignment`(OUT return_code INT)
BEGIN

  DECLARE EXIT HANDLER FOR NOT FOUND SET return_code = -2;

  SET return_code =0;

  DROP TABLE IF EXISTS `temp_work_assignment`;

  CREATE TABLE `temp_work_assignment` (

    `service_category_group` varchar(3) NOT NULL,

    `project_file_part` varchar(15) NOT NULL,

    `project_file_no` int(10) unsigned NOT NULL,

    `department_id` varchar(10) NOT NULL,

    `assignment_status` char(1) NOT NULL,

    `work_assignment_title` varchar(400) NOT NULL,

    `closing_date` date NOT NULL,

    `debarred_contractor` varchar(100) default NULL,

    `issue_date` date NOT NULL,

    `authorized_person` varchar(100) NOT NULL,

    `awarded_contractor` varchar(20) default NULL,

    `awarded_date` date default NULL,

	`awarded_contract_value` DECIMAL(20,3),

	`aggregated_effort` DECIMAL(20,3),

	`total_project_cost` DECIMAL(20,3),

    PRIMARY KEY  (`service_category_group`,`project_file_part`,`project_file_no`,`department_id`)

  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  SET return_code = 1;

END $$

DELIMITER ;

--
-- Procedure `qpsis`.`sp_write_work_assignment_header`
--

DROP PROCEDURE IF EXISTS `sp_write_work_assignment_header`;
DELIMITER $$

CREATE PROCEDURE `sp_write_work_assignment_header`(IN p_counter INT,
 IN p_admin_id varchar(30),
 IN p_dp_dept_id varchar(10),
 IN p_soa_dept_id varchar(10),
 OUT return_code INT)
BEGIN
  DECLARE EXIT HANDLER FOR NOT FOUND
  BEGIN
   ROLLBACK;
   SET return_code = -2;
  END;
  START TRANSACTION;
  INSERT INTO temp_work_assignment
  (service_category_group,
   project_file_part,
   project_file_no,
   department_id,
   issue_date,
   closing_date,
   assignment_status,
   work_assignment_title,
   authorized_person
  )
  Values
  ("HDR",
   "1",
   p_counter,
   "QPSIS",
   SYSDATE(),
   SYSDATE(),
   "h",
   "QPSIS Header",
   p_admin_id);
/* write audit_trail*/
  INSERT INTO audit_trail
      (user_id,
       soa_department_id,
       dp_department_id,
       function_id,
	   record_id,
       entity_name,
       action_performed,
       date_time,
       remarks)
    VALUES
    (p_admin_id,
     p_soa_dept_id,
     p_dp_dept_id,
     "WAIMPORT",
     "Import WA",
     "work_assignment",
     "A",
     SYSDATE(),
     CONCAT(p_counter, " records imported")
    );
  SET return_code = 1;
END $$

DELIMITER ;


--
-- Procedure `qpsis`.`sp_insert_work_assignment`
--

DROP PROCEDURE IF EXISTS `sp_insert_work_assignment`;
DELIMITER $$

CREATE PROCEDURE `sp_insert_work_assignment`(IN p_service_category_group varchar(3),

 IN p_file_part varchar(15),

 IN p_file_no int,

 IN p_dept_id varchar(10),

 IN p_status char(1),

 IN p_title varchar(400),

 IN p_closing_date DATE,

 IN p_debarred_contractor varchar(100),

 IN p_issued_date DATE,

 IN p_authorized_person varchar(100),

 IN p_awarded_contractor varchar(20),

 IN p_awarded_date DATE,

 IN p_awarded_contract_value FLOAT,

 IN p_aggregated_effort FLOAT,

 IN p_total_project_cost FLOAT,

 OUT return_code INT)
BEGIN

DECLARE EXIT HANDLER FOR NOT FOUND SET return_code = -2;

SET return_code = 0;

IF (SELECT COUNT(*) FROM temp_work_assignment

      WHERE service_category_group = p_service_category_group AND

            project_file_part = p_file_part AND

            project_file_no = p_file_no AND

            department_id = p_dept_id > 0) THEN

  SET return_code = -99;

END IF;

IF return_code =0 THEN

/* check for any B/D not found in the database*/

  BEGIN

    IF (SELECT COUNT(*) FROM bd_name WHERE soa_department_id = p_dept_id) =0 THEN

      SET return_code = -89;

    END IF;

  END;

END IF;

/* insert record if no error found*/

IF return_code =0 THEN

 BEGIN

  INSERT INTO temp_work_assignment

    (service_category_group,

     project_file_part,

     project_file_no,

     department_id,

     assignment_status,

     work_assignment_title,

     closing_date,

     debarred_contractor,

     issue_date,

     authorized_person,

     awarded_contractor,

     awarded_date,
	
	 awarded_contract_value,

	 aggregated_effort,

	 total_project_cost
	)

    Values

    (p_service_category_group,

     p_file_part,

     p_file_no,

     p_dept_id,

     p_status,

     p_title,

     p_closing_date,

     p_debarred_contractor,

     p_issued_date,

     p_authorized_person,

     p_awarded_contractor,

     p_awarded_date,

	 p_awarded_contract_value,

	 p_aggregated_effort,

	 p_total_project_cost
  );

    SET return_code = 1;

 END;

END IF;

END $$

DELIMITER ;


--
-- Procedure `qpsis`.`sp_select_srv_wa_by_keys_user`
--

DROP PROCEDURE IF EXISTS `sp_select_srv_wa_by_keys_user`;
DELIMITER $$

CREATE PROCEDURE `sp_select_srv_wa_by_keys_user`(

	IN p_scg VARCHAR(3),

	IN p_file_part_no VARCHAR(15),

	IN p_file_no INT,

	IN p_dept_id VARCHAR(10)

)
BEGIN

	SELECT *

	FROM work_assignment AS WA, bd_name AS BD  

	WHERE  

		BD.soa_department_id      = p_dept_id      AND

		WA.service_category_group = p_scg          AND

		WA.project_file_part      = p_file_part_no AND

		WA.project_file_no        = p_file_no      AND

		WA.department_id          = p_dept_id

	;

END $$

DELIMITER ;


--
-- Procedure `qpsis`.`sp_select_work_assignment_by_keys`
--

DROP PROCEDURE IF EXISTS `sp_select_work_assignment_by_keys`;
DELIMITER $$

CREATE PROCEDURE `sp_select_work_assignment_by_keys`(IN p_service_category_group VARCHAR(3),

 IN p_file_part_no VARCHAR(15),

 IN p_file_no INT,

 IN p_dept_id VARCHAR(10))
BEGIN

    SELECT *,b.bd_name FROM work_assignment as w

      JOIN bd_name as b

        ON w.department_id = b.soa_department_id

      WHERE service_category_group = p_service_category_group AND

            project_file_part = p_file_part_no AND

            project_file_no = p_file_no AND

            department_id = p_dept_id;

END $$

DELIMITER ;


--
-- Procedure `qpsis`.`sp_select_wa_no_inv_cont`
--

DROP PROCEDURE IF EXISTS `sp_select_wa_no_inv_cont`;
DELIMITER $$

CREATE PROCEDURE `sp_select_wa_no_inv_cont`(p_service_category_group VARCHAR(3), p_project_file_part VARCHAR(15), p_project_file_no INT, p_department_id VARCHAR(10))
BEGIN

	DECLARE v_debarred_contractor VARCHAR(100);

	SET v_debarred_contractor =

	(

		SELECT debarred_contractor

		FROM work_assignment W

		WHERE

			W.service_category_group = p_service_category_group AND

			W.project_file_part      = p_project_file_part      AND

			W.project_file_no        = p_project_file_no        AND

			W.department_id          = p_department_id

	);

	CALL sp_split_string(v_debarred_contractor, ',');

	SELECT COUNT(1) AS 'no_of_inv_cont'

	FROM work_assignment W, contractor C

	WHERE

		W.service_category_group = p_service_category_group AND

		W.project_file_part      = p_project_file_part      AND

		W.project_file_no        = p_project_file_no        AND

		W.department_id          = p_department_id          AND

		(

			

			(C.1J_indicator = -1 AND W.service_category_group = '1') OR

			(C.2N_indicator = -1 AND W.service_category_group = '2/N') OR

			(C.2J_indicator = -1 AND W.service_category_group = '2/J') OR

			(C.3N_indicator = -1 AND W.service_category_group = '3/N') OR

			(C.3J_indicator = -1 AND W.service_category_group = '3/J') OR   			

			(C.4J_indicator = -1 AND W.service_category_group = '4')

		)

		AND C.contractor_id NOT IN (SELECT splitted_value FROM tmp_split_values);

END $$

DELIMITER ;


--
-- Procedure `qpsis`.`sp_insert_challenge_log`
--

DROP PROCEDURE IF EXISTS `sp_insert_challenge_log`;
DELIMITER $$

CREATE PROCEDURE `sp_insert_challenge_log`(

	p_user_id                VARCHAR(30) ,

	p_dp_department_id       VARCHAR(10) ,

	p_soa_department_id      VARCHAR(10) ,

	p_function_id            VARCHAR(100),

	p_question               VARCHAR(100),

	p_answer                 VARCHAR(100),

	p_is_correct_answer      VARCHAR(1),

	p_is_locked              VARCHAR(1),

	p_service_category_group VARCHAR(3),

	p_project_file_part      VARCHAR(15),

	p_project_file_no	 INT,

	p_department_id          VARCHAR(10)

)
BEGIN

	SET @entity_name      = "work_assignment";

	SET @record_id        =

		CONCAT(p_service_category_group, "+", p_project_file_part, "+", p_project_file_no, "+", p_department_id); 

	SET @action_performed = "C";

	SET @remarks          = "";

	

	IF p_is_locked = "Y" THEN

		SET @remarks = CONCAT(p_question, "+", p_answer, "+", "Locked");

	ELSEIF p_is_locked = "N" AND p_is_correct_answer = "Y" THEN

		SET @remarks = CONCAT(p_question, "+", p_answer, "+", "Correct");

	ELSEIF p_is_locked = "N" AND p_is_correct_answer = "N" THEN

		SET @remarks = CONCAT(p_question, "+", p_answer, "+", "Incorrect");

	ELSEIF p_is_locked = "" AND p_is_correct_answer = "" THEN

		SET @remarks = CONCAT(p_question, "+", "Ask");

	END IF;

	

	INSERT INTO audit_trail

	(

		user_id, dp_department_id, soa_department_id, function_id,

		entity_name, record_id, action_performed, date_time, remarks

	)

	VALUES

	(

		p_user_id, p_dp_department_id, p_soa_department_id, p_function_id,

		@entity_name, @record_id, @action_performed, SYSDATE(), @remarks

	);

END $$

DELIMITER ;


COMMIT;