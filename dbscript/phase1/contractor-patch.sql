-- MySQL stored procedures for QPS3 Database 
-- Contractor Data Migration Patch
-- HKMCI appends their change to this DB script after running the SQL init
-- ------------------------------------------------------
-- Server version	5.5-community


use qpsis;

--
-- Delete Ceiling Rates and CPS for replacing contractors
--

DELETE FROM `ceiling_rate_1`;
DELETE FROM `ceiling_rate_2`;
DELETE FROM `ceiling_rate_3`;
DELETE FROM `ceiling_rate_4`;
DELETE FROM `quality_subscore`;

--
-- Modify Table structure for table `qpsis`.`contractor`
--

DELETE FROM `contractor` ;
ALTER TABLE `contractor`
MODIFY `contractor_name` varchar(150) NOT NULL;
ALTER TABLE `contractor`
MODIFY `1N_indicator` varchar(150) NOT NULL DEFAULT '0';
ALTER TABLE `contractor`
MODIFY `4N_indicator` varchar(150) NOT NULL DEFAULT '0';

--
-- Dumping data for table `qpsis`.`contractor`
--

INSERT INTO `contractor` (`contractor_name`,`contractor_id`,`1J_indicator`,`2N_indicator`,`2J_indicator`,`3N_indicator`,`3J_indicator`,`4J_indicator`) VALUES 
('Accentrix Company Limited','Accentrix','0','0','0','-1','0','0'),
('Accenture Company Limited','Accenture','-1','0','0','0','0','0'),
('Access Testing HK Limited','Access Testing','0','0','0','0','0','-1'),
('Arcotect Limited','Arcotect','-1','-1','0','-1','0','-1'),
('Atos Information Technology HK Limited','Atos','0','0','-1','0','-1','0'),
('Au Posford Consultants Limited','Au Posford','0','0','0','-1','0','0'),
('Automated Systems (HK) Limited','ASL','-1','0','0','0','-1','-1'),
('Azeus Systems Limited','Azeus','-1','0','-1','0','-1','0'),
('China Communications Services Corporation Limited','ChinaComm','0','0','-1','0','0','0'),
('Cisco Services (Hong Kong) Limited','Cisco','0','0','-1','0','0','0'),
('Communications & Services Experts Limited','Comm & Service','0','0','-1','0','0','0'),
('Computer And Technologies Solutions Limited','C&T','0','-1','0','0','-1','-1'),
('Deloitte Consulting (Hong Kong) Limited','Deloitte','-1','0','0','0','0','0'),
('Dimension Data China/Hong Kong Limited','Dimension Data','0','0','-1','0','0','0'),
('DMX Technologies (HK) Limited','DMX','0','0','0','0','0','-1'),
('Doctor A Security Systems (HK) Limited','DoctorA','0','0','0','0','0','-1'),
('Esri China (Hong Kong) Limited','ESRI','0','-1','0','-1','0','0'),
('FDS Solutions Limited','FDS','0','-1','0','-1','0','0'),
('Flexsystem Limited','Flexsystem','0','0','0','0','-1','0'),
('Fujitsu Hong Kong Limited','Fujitsu','0','0','-1','0','-1','0'),
('Future Solutions Laboratory Limited','Future','0','-1','0','-1','0','0'),
('Gamatech Limited','Gamatech','0','0','0','-1','0','0'),
('Global Technology Integrators Limited','GTI','0','0','-1','0','-1','0'),
('Golden Dynamic Enterprises Limited','Golden Dynamic','0','0','0','0','-1','0'),
('GuangDong Morning Star Technology Development Co Ltd','GD Morning Star','0','0','0','0','-1','0'),
('Hewlett-Packard HK SAR Ltd.','HP','-1','0','0','0','0','-1'),
('Integrated Enterprise Solutions Ltd','IES','0','-1','0','-1','0','0'),
('Integrated Global Solutions Limited','IGS','0','-1','0','0','0','0'),
('IT-Partners Limited','IT-Partners','0','0','0','0','0','-1'),
('Jardine OneSolution (HK) Limited','JOS','0','-1','0','0','-1','0'),
('Kinetix Systems Limited','Kinetix','-1','-1','0','0','-1','-1'),
('Macro Systems Limited','Macro','0','0','0','-1','0','0'),
('Mappa Systems Limited','Mappa','0','0','0','-1','0','0'),
('Master Concept (Hong Kong) Limited','Master Concept','0','-1','0','-1','0','0'),
('Mobigator (H.K.) Limited','Mobigator','-1','0','0','0','0','-1'),
('Motion Networks Technology (HK) Limited','Motion Networks','0','-1','0','0','0','0'),
('NEC Hong Kong Limited','NEC','0','0','-1','0','-1','0'),
('Pactera (Hong Kong) Limited','Pactera','0','0','-1','0','0','0'),
('PCCW Solutions Ltd','PCCW','-1','0','0','0','0','0'),
('Satyam Computer Services Ltd.','Satyam','-1','0','0','0','0','0'),
('SMS Management & Technology Asia Pty Ltd','SMS Mgmt & Tech','-1','0','0','0','0','-1'),
('Speedy Group Corp. Limited','Speedy Group','0','-1','0','0','0','0'),
('Unisys China/HongKong Limited','Unisys','-1','0','0','0','0','-1'),
('Wai On Services Limited','Wai On','0','0','0','-1','0','0');

COMMIT;