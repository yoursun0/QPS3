-- MySQL script for QPS3 Database
-- Discount Rate Data Patch
-- HKMCI appends their change to this DB script after running all phrase1 SQL script
-- ------------------------------------------------------
-- Server version 5.5-community

use qpsis;

--
-- Import New Discount_rate PDF files to production by average ceiling rate upload.
-- First, uplaod the 6 PDFs to average ceiling rate with effective dates = 1 Aug 2013 to 6 Aug 2013
-- Then run the following scripts
--


update discount_rate set
effective_date = '2013-07-31',
pdf_file = (SELECT pdf_file from average_ceiling_rate
      where effective_date = '2013-08-01')
where service_category_group = '1';

update discount_rate set
effective_date = '2013-07-31',
pdf_file = (SELECT pdf_file from average_ceiling_rate
      where effective_date = '2013-08-02')
where service_category_group = '2/J';


update discount_rate set
effective_date = '2013-07-31',
pdf_file = (SELECT pdf_file from average_ceiling_rate
      where effective_date = '2013-08-03')
where service_category_group = '2/N';

update discount_rate set
effective_date = '2013-07-31',
pdf_file = (SELECT pdf_file from average_ceiling_rate
      where effective_date = '2013-08-04')
where service_category_group = '3/J';

update discount_rate set
effective_date = '2013-07-31',
pdf_file = (SELECT pdf_file from average_ceiling_rate
      where effective_date = '2013-08-05')
where service_category_group = '3/N';

update discount_rate set
effective_date = '2013-07-31',
pdf_file = (SELECT pdf_file from average_ceiling_rate
      where effective_date = '2013-08-06')
where service_category_group = '4';

delete from average_ceiling_rate where 
effective_date in ('2013-08-01','2013-08-02','2013-08-03','2013-08-04','2013-08-05','2013-08-06');

COMMIT;