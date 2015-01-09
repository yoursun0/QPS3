-- MySQL stored procedures for QPS3 Database 
-- Department Data Migration Patch
-- HKMCI appends their change to this DB script after running the SQL init
-- ------------------------------------------------------
-- Server version	5.5-community


use qpsis;


DELETE FROM `acl` ;

DELETE FROM `bd_name`;

INSERT INTO `bd_name` (`dp_department_id`,`soa_department_id`,`bd_name`) VALUES 
('afcd','AFCD','Agriculture, Fisheries and Conservation Department'),
('archsd','ArchSD','Architectural Services Department'),
('audc','AUD','Audit Commission'),
('ams','AMS','Auxiliary Medical Service'),
('bd','BD','Buildings Department'),
('censtatd','C&SD','Census and Statistics Department'),
('cso','CPU','Central Policy Unit'),
('ceo','CEO','Chief Executive\'s Office'),
('cso','CSO','Chief Secretary for Administration\'s Office'),
('cas','CAS','Civil Aid Service'),
('cad','CAD','Civil Aviation Department'),
('cedd','CEDD','Civil Engineering and Development Department'),
('csb','CSB','Civil Service Bureau'),
('cedb','CEDB','Commerce and Economic Development Bureau'),
('cr','CR','Companies Registry'),
('cmab','CMAB','Constiutional and Mainland Affairs Bureau'),
('csd','CSD','Correctional Services Department'),
('CREATEHK','CEDB','Create HK'),
('customs','C&ED','Customs & Excise Department'),
('dh','DH','Department of Health'),
('doj','DOJ','Department of Justice'),
('devb','DEVB','Development Bureau'),
('dsd','DSD','Drainage Services Department'),
('edb','EDB','Education Bureau'),
('cso','EU','Efficiency Unit'),
('emsd','EMSD','Electrical and Mechanical Services Department'),
('enb','ENB','Environment Bureau'),
('epd','EPD','Environmental Protection Department'),
('fso','FSO','Financial Secretary\'s Office'),
('fstb','FSTB','Financial Services and the Treasury Bureau'),
('hkfsd','FSD','Fire Services Department'),
('fehd','FEHD','Food and Environmental Hygiene Department'),
('fhb','FHB','Food and Health Bureau'),
('gfs','GFS','Government Flying Service'),
('govtlab','GLab','Government Laboratory'),
('gld','GLD','Government Logistics Department'),
('gpa','GPA','Government Property Agency'),
('cso','GRS','Government Records Service'),
('hyd','HyD','Highways Department'),
('bo','BO','HKSAR Beijing Office'),
('hab','HAB','Home Affairs Bureau'),
('had','HAD','Home Affairs Department'),
('hko','HKO','Hong Kong Observatory'),
('hpf','HKPF','Hong Kong Police Force'),
('po','HKPO','Hongkong Post'),
('hd','HD','Housing Department'),
('immd','ImmD','Immigration Department'),
('icac','ICAC','Independent Commission Against Corruption'),
('isd','ISD','Information Services Department'),
('ird','IRD','Inland Revenue Department'),
('itc','ITC','Innovation and Technology Commission'),
('ipd','IPD','Intellectual Property Department'),
('investhk','INVESTHK','Invest Hong Kong'),
('jsscs','JSSCS','Joint Secretariat for the Advisory Bodies on Civil Service and Judicial Salaries and Conditions of Service'),
('jud','JUD','Judiciary'),
('lwb','LWB','Labour and Welfare Bureau'),
('ld','LD','Labour Department'),
('landreg','LR','Land Registry'),
('landsd','LandsD','Lands Department'),
('lad','LAD','Legal Aid Department'),
('legco','LegCo','Legislative Council'),
('lcsd','LCSD','Leisure and Cultural Services Department'),
('md','MD','Marine Department'),
('ofca','OFCA','Office of the Communications Authority '),
('oci','OCI','Office of the Commissioner of Insurance'),
('ogcio','OGCIO','Office of the Government Chief Information Officer'),
('oro','ORO','Official Receiver\'s Office'),
('pland','PlanD','Planning Department'),
('psc','PSC','Public Service Commission'),
('rthk','RTHK','Radio Television Hong Kong'),
('rvd','RVD','Rating and Valuation Department'),
('reo','REO','Registration and Electoral Office'),
('sb','SB','Security Bureau'),
('swd','SWD','Social Welfare Department'),
('sfaa','SFAA','Student Financial Assistance Agency'),
('cso','SDU','Sustainable Development Unit'),
('cedb','TC','Tourism Commission'),
('tid','TID','Trade and Industry Department'),
('thb','THB','Transport and Housing Bureau'),
('td','TD','Transport Department'),
('try','TRY','Treasury'),
('ugc','UGC','University Grants Committee Secretariat'),
('wsd','WSD','Water Supplies Department');

--
-- Create test user henry.ogcio and helic.hkpf
--

insert into `acl` (user_id,firstname,lastname,email,login_failure_count,soa_department_id,user_group,active_indicator,password_indicator,created_datetime,last_updated_datetime,last_updated_by_user,dp_department_id,effective_date,application_duration,expiry_date,access_failure_count,last_access_date) VALUES
('owen.sadmin', 'Owen', 'SAdmin', 'owen.mang@hkmci.com', '0', 'OGCIO', 'S', '-1', '-1', '2013-06-01', '2013-06-01', 'a', 'ogcio', '2006-09-01', NULL, '2019-06-27', '0', '2013-06-01 00:00:00');
insert into `acl` (user_id,firstname,lastname,email,login_failure_count,soa_department_id,user_group,active_indicator,password_indicator,created_datetime,last_updated_datetime,last_updated_by_user,dp_department_id,effective_date,application_duration,expiry_date,access_failure_count,last_access_date) VALUES
('may.lcsd', 'May', 'LCSD', 'mcmcheung@ogcio.gov.hk', '0', 'LCSD', 'U', '-1', '-1', '2013-06-01', '2013-06-01', 'helic.admin', 'lcsd', '2006-09-01', NULL, '2019-06-27', '0', '2013-06-01 00:00:00');
insert into `acl` (user_id,firstname,lastname,email,login_failure_count,soa_department_id,user_group,active_indicator,password_indicator,created_datetime,last_updated_datetime,last_updated_by_user,dp_department_id,effective_date,application_duration,expiry_date,access_failure_count,last_access_date) VALUES
('helic.admin', 'Helic', 'Admin', 'helic.leung@hkmci.com', '0', 'OGCIO', 'SP', '-1', '-1', '2013-06-01', '2013-06-01', 'a', 'ogcio', '2006-09-01', NULL, '2019-06-27', '0', '2013-06-01 00:00:00');
insert into `acl` (user_id,firstname,lastname,email,login_failure_count,soa_department_id,user_group,active_indicator,password_indicator,created_datetime,last_updated_datetime,last_updated_by_user,dp_department_id,effective_date,application_duration,expiry_date,access_failure_count,last_access_date) VALUES
('helic.hpf', 'Helic', 'HKPF', 'helic.leung@hkmci.com', '0', 'HKPF', 'U', '-1', '-1', '2013-06-01', '2013-06-01', 'helic.admin', 'hpf', '2006-09-01', NULL, '2019-06-27', '0', '2013-06-01 00:00:00');
insert into `acl` (user_id,firstname,lastname,email,login_failure_count,soa_department_id,user_group,active_indicator,password_indicator,created_datetime,last_updated_datetime,last_updated_by_user,dp_department_id,effective_date,application_duration,expiry_date,access_failure_count,last_access_date) VALUES
('henry.ogcio', 'Henry', 'OGCIO', 'chwoo@ogcio.gov.hk', '0', 'OGCIO', 'U', '-1', '-1', '2013-06-01', '2013-06-01', 'helic.admin', 'ogcio', '2006-09-01', NULL, '2019-06-27', '0', '2013-06-01 00:00:00');

UPDATE `acl` SET password = QPSES_ENCRYPT(user_id,'password','password1');

COMMIT;