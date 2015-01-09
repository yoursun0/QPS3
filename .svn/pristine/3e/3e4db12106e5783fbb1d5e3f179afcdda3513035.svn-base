-- MySQL stored procedures for QPS3 Database 
-- Contractor Performance Score / Quality Subscore Procedure
-- HKMCI appends their change to this DB script after running the SQL init
-- ------------------------------------------------------
-- Server version	5.5-community


use qpsis;

--
-- Table structure for table `qpsis`.`quality_subscore`
--

DROP TABLE IF EXISTS `quality_subscore`;
CREATE TABLE `quality_subscore` (
  `effective_period_id` int(10) unsigned NOT NULL,
  `service_category_group` varchar(3) NOT NULL,
  `contractor_id` varchar(20) NOT NULL,
  `quality_subscore` blob,
  `created_datetime` datetime NOT NULL,
  `last_updated_datetime` datetime NOT NULL,
  `last_updated_by_user` varchar(30) NOT NULL,
  PRIMARY KEY  (`effective_period_id`,`service_category_group`,`contractor_id`),
  KEY `FK_quality_subscore` (`contractor_id`),
  CONSTRAINT `FK_quality_subscore` FOREIGN KEY (`contractor_id`) REFERENCES `contractor` (`contractor_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Dumping data for table `qpsis`.`quality_subscore_effective_period`
--

/*!40000 ALTER TABLE `quality_subscore_effective_period` DISABLE KEYS */;
DELETE FROM `quality_subscore_effective_period`;
INSERT INTO `quality_subscore_effective_period` (`effective_period_id`,`period_desc`,`effective_start_date`,`effective_end_date`) VALUES 
-- (1,'31 Jul 2009 - 30 Jan 2010','2009-07-31','2010-01-30'),
--  (2,'31 Jan 2010 - 30 Jul 2010','2010-01-31','2010-07-30'),
--  (3,'31 Jul 2010 - 30 Jan 2011','2010-07-31','2011-01-30'),
--  (4,'31 Jan 2011 - 30 Jul 2011','2011-01-31','2011-07-30'),
--  (5,'31 Jul 2011 - 30 Jan 2012','2011-07-31','2012-01-30'),
--  (6,'31 Jan 2012 - 30 Jul 2012','2012-01-31','2012-07-30'),
--  (7,'31 Jul 2012 - 30 Jan 2013','2012-07-31','2013-01-30'),
--  (8,'31 Jan 2013 - 30 Jul 2013','2013-01-31','2013-07-30'),

 (1,'31 Jul 2013 - 02 Feb 2014','2013-07-31','2014-02-02'),
 (2,'03 Feb 2014 - 31 Jul 2014','2014-02-03','2014-07-31'),
 (3,'01 Aug 2014 - 01 Feb 2015','2014-08-01','2015-02-01'),
 (4,'02 Feb 2015 - 02 Aug 2015','2015-02-02','2015-08-02'),
 (5,'03 Aug 2015 - 31 Jan 2016','2015-08-03','2016-01-31'),
 (6,'01 Feb 2016 - 31 Jul 2016','2016-02-01','2016-07-31'),
 (7,'01 Aug 2016 - 31 Jan 2017','2016-08-01','2017-01-31'),
 (8,'01 Feb 2017 - 30 Jul 2017','2017-02-01','2017-07-30'),
 (9,'31 Jul 2017 - 30 Jan 2018','2017-07-31','2018-01-30'),
 (10,'31 Jan 2018 - 30 Jul 2018','2018-01-01','2018-07-30'),
 (11,'31 Jul 2018 - 30 Jan 2019','2018-07-31','2019-01-30'),
 (12,'31 Jan 2019 - 30 Jul 2019','2019-01-01','2019-07-30')
 ;


/*!40000 ALTER TABLE `quality_subscore_effective_period` ENABLE KEYS */;


--
-- Procedure `qpsis`.`sp_create_temp_quality_subscore`
--

DROP PROCEDURE IF EXISTS `sp_create_temp_quality_subscore`;
DELIMITER $$

CREATE PROCEDURE `sp_create_temp_quality_subscore`(OUT return_code INT)
BEGIN
  DECLARE EXIT HANDLER FOR NOT FOUND
  BEGIN
    SET return_code = -2;
    ROLLBACK;
  END;
  SET return_code =0;
  START TRANSACTION;
  DROP TABLE IF EXISTS `temp_quality_subscore`;
  CREATE TABLE `temp_quality_subscore` (
  `effective_period_id` int(10) unsigned NOT NULL,
  `service_category_group` varchar(3) NOT NULL,
  `contractor_id` varchar(20) NOT NULL,
  `quality_subscore` blob,
  `created_datetime` datetime NOT NULL,
  `last_updated_datetime` datetime NOT NULL,
  `last_updated_by_user` varchar(30) NOT NULL,
  PRIMARY KEY  (`effective_period_id`,`service_category_group`,`contractor_id`),
  KEY `FK_temp_quality_subscore` (`contractor_id`),
  CONSTRAINT `FK_temp_quality_subscore` FOREIGN KEY (`contractor_id`) REFERENCES `contractor` (`contractor_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  INSERT INTO temp_quality_subscore SELECT * FROM quality_subscore;
  COMMIT;
  SET return_code = 1;
END $$

DELIMITER ;

--
-- Procedure `qpsis`.`sp_get_quality_subscore_by_period`
--

DROP PROCEDURE IF EXISTS `sp_get_quality_subscore_by_period`;
DELIMITER $$

CREATE PROCEDURE `sp_get_quality_subscore_by_period`(IN p_period_id varchar(2),IN p_service_category_group varchar(3))
BEGIN
SELECT q.effective_period_id, e.period_desc, q.service_category_group, c.contractor_name,
      QPSES_DECRYPT(CONCAT(q.effective_period_id, q.created_datetime), 'quality_subscore', q.quality_subscore) AS 'quality_subscore'
     FROM quality_subscore as q
     join quality_subscore_effective_period as e ON q.effective_period_id = e.effective_period_id
     join contractor as c ON q.contractor_id = c.contractor_id
    WHERE q.effective_period_id = p_period_id
    AND   q.service_category_group = p_service_category_group
    ORDER BY c.contractor_name;
END $$

DELIMITER ;



--
-- Procedure `qpsis`.`sp_insert_quality_subscore`
--

DROP PROCEDURE IF EXISTS `sp_insert_quality_subscore`;
DELIMITER $$

CREATE PROCEDURE `sp_insert_quality_subscore`(IN p_effective_period_id int,
 IN p_service_category_group varchar(3),
 IN p_contractor_name varchar(150),
 IN p_score float,
 IN p_admin_id varchar(30),
 OUT return_code INT)
BEGIN
DECLARE CID VARCHAR(30);
DECLARE crdate DATETIME;
DECLARE EXIT HANDLER FOR NOT FOUND SET return_code = -2;
SET crdate = SYSDATE();
IF (SELECT COUNT(*) FROM `quality_subscore` AS q
      JOIN `contractor` AS c ON
            q.contractor_id = c.contractor_id
      WHERE c.contractor_name=p_contractor_name AND
            service_category_group = p_service_category_group AND
            effective_period_id = p_effective_period_id > 0) THEN
  SET return_code = -99;
ELSE
  BEGIN
    SELECT contractor_id INTO CID
      FROM contractor
      WHERE contractor_name = p_contractor_name;
    INSERT INTO temp_quality_subscore
    (effective_period_id,
    service_category_group,
    contractor_id,
    quality_subscore,
    created_datetime,
    last_updated_datetime,
    last_updated_by_user)
    Values
    (p_effective_period_id,
     p_service_category_group,
     CID,
     QPSES_ENCRYPT(CONCAT(p_effective_period_id, crdate),'quality_subscore',CAST(p_score AS CHAR)),
     crdate,
     crdate,
     p_admin_id);
    SET return_code = 1;
  END;
END IF;
END $$

DELIMITER ;



--
-- Procedure `qpsis`.`sp_select_quality_subscore_user`
--

DROP PROCEDURE IF EXISTS `sp_select_quality_subscore_user`;
DELIMITER $$

CREATE PROCEDURE `sp_select_quality_subscore_user`(p_closing_date DATE, p_scg VARCHAR(3), p_user_id VARCHAR(30), p_dp_department_id VARCHAR(10), p_soa_department_id VARCHAR(10), p_function_id VARCHAR(30), p_dept_id VARCHAR(10), p_wa_file_part VARCHAR(15), p_wa_file_no INT)
BEGIN

	INSERT INTO audit_trail (user_id, dp_department_id, soa_department_id, function_id, entity_name, record_id, action_performed, date_time, remarks)

	VALUES (p_user_id, p_dp_department_id, p_soa_department_id, p_function_id, 'quality_subscore', CONCAT(p_closing_date, '+', p_scg, '+', p_dept_id, '+', p_wa_file_part, '+', p_wa_file_no), 'S', NOW(),'Enquire Contractor Performance Score');

	

	SET @d_contractor_id =

	(

		SELECT debarred_contractor

		FROM work_assignment

		WHERE

			service_category_group = p_scg          AND 

			project_file_part      = p_wa_file_part AND

			project_file_no        = p_wa_file_no   AND

			department_id          = p_dept_id

	);

	

	CALL sp_split_string(@d_contractor_id, ',');

	

	SELECT

		qes.effective_start_date,

		c.contractor_name, 

		QPSES_DECRYPT(CONCAT(q.effective_period_id, q.created_datetime), 'quality_subscore', quality_subscore) AS 'quality_subscore'

	FROM quality_subscore AS q, contractor AS c, quality_subscore_effective_period AS qes

	WHERE 

		q.effective_period_id = qes.effective_period_id AND

    	q.service_category_group = p_scg                AND

		c.contractor_id       = q.contractor_id         AND 

		c.contractor_id NOT IN (SELECT splitted_value FROM tmp_split_values) AND

		p_closing_date BETWEEN qes.effective_start_date AND qes.effective_end_date AND

		(

			

			(p_scg = '1' AND c.1J_indicator = -1) OR

			(p_scg = '2/N' AND c.2N_indicator = -1) OR

			(p_scg = '2/J' AND c.2J_indicator = -1) OR

			(p_scg = '3/N' AND c.3N_indicator = -1) OR

			(p_scg = '3/J' AND c.3J_indicator = -1) OR    			

			(p_scg = '4' AND c.4J_indicator = -1)

		)

	ORDER BY c.contractor_name;

END $$

DELIMITER ;



--
-- Procedure `qpsis`.`sp_select_srv_qs_user`
--

DROP PROCEDURE IF EXISTS `sp_select_srv_qs_user`;
DELIMITER $$

CREATE PROCEDURE `sp_select_srv_qs_user`(p_contractor_id_list VARCHAR(1024), p_closing_date DATE, p_scg VARCHAR(3))
BEGIN

	CALL sp_split_string(p_contractor_id_list, ',');

	

	SELECT 	

		qes.effective_start_date,

		c.contractor_name,

		QPSES_DECRYPT(CONCAT(q.effective_period_id, q.created_datetime), 'quality_subscore', quality_subscore) AS 'quality_subscore'

        FROM

		quality_subscore AS q, contractor AS c,

		quality_subscore_effective_period AS qes

        WHERE  

		q.effective_period_id = qes.effective_period_id AND

		q.service_category_group = p_scg                AND

		c.contractor_id       = q.contractor_id         AND

                c.contractor_id IN (SELECT splitted_value FROM tmp_split_values) AND 

                p_closing_date BETWEEN qes.effective_start_date AND qes.effective_end_date AND

		(

			(p_scg = '1' AND c.1J_indicator = -1) OR

			(p_scg = '2/N' AND c.2N_indicator = -1) OR

			(p_scg = '2/J' AND c.2J_indicator = -1) OR

			(p_scg = '3/N' AND c.3N_indicator = -1) OR

			(p_scg = '3/J' AND c.3J_indicator = -1) OR

			(p_scg = '4' AND c.4J_indicator = -1)

		)

	ORDER BY c.contractor_name ASC;

END $$

DELIMITER ;


--
-- Procedure `qpsis`.`sp_select_quality_subscore_dcontractor_user`
--

DROP PROCEDURE IF EXISTS `sp_select_quality_subscore_dcontractor_user`;
DELIMITER $$

CREATE PROCEDURE `sp_select_quality_subscore_dcontractor_user`(p_dept_id VARCHAR(10), p_scg VARCHAR(3), p_wa_file_part VARCHAR(15), p_wa_file_no INT)
BEGIN

	SET @d_contractor_id =

	(

		SELECT debarred_contractor

		FROM work_assignment

		WHERE

			service_category_group = p_scg          AND

			project_file_part      = p_wa_file_part AND

			project_file_no        = p_wa_file_no   AND

			department_id          = p_dept_id

	);

	CALL sp_split_string(@d_contractor_id, ',');

	

	SELECT *

	FROM contractor

	WHERE

		contractor_id IN (SELECT splitted_value FROM tmp_split_values)

        ORDER BY contractor_name ASC

	;

END $$

DELIMITER ;

COMMIT;