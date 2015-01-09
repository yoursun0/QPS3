package qpses.util;

public class Constant {
    
        // System
    
        public static final String QPSES_ADMIN_GROUP = "A";
        public static final String QPSES_ADMIN_GROUP_DEFAULT = "DA";
        public static final String QPSES_ADMIN_PATH = "/qpsadmin/";
        public static final int QPSIS_CHALLENGE_ATTEMPT_LIMIT = 3;
        public static final int QPSIS_CPAR_ATTEMPT_LIMIT = 3;
        public static final int QPSIS_WRONG_PASSWORD_ATTEMPT_LIMIT = 5;
        public static final String QPSIS_TEMP_DIR = "//tmp";
        public static final String QPSIS_STARTUP_DATE = "01-Jan-1990";
        public static final String QPSIS_EXPIRY_DATE = "30-Jun-2047";
        public static final String QPSIS_CONTRACT_NO_PREFIX = "GCIO 5/11-";
    
        // CPS3 modification: Quality subcores --> Contractor Performance Score (CPS)
        
        public static final String QUALITY_SUBSCORE_SHEETNAME = "CPS"; // for CPS2 ="General Quality Sub-score";
        public static final String QUALITY_SUBSCORE_TITLE = "Contractor Performance Scores Assigned to SOA-QPS3 Contractors for the Period"; // for CPS2 = "General Quality Sub-scores Assigned to SOA-QPS Contractors for the Period";
        public static final int QUALITY_SUBSCORE_CONTRACTOR_COLNO = 2;
        public static final int QUALITY_SUBSCORE_CONTRACTOR_NAME_COLNO = 3;
        public static final String QUALITY_SUBSCORE_CONTRACTOR_COL_NAME = "Contractor"; // for CPS2 ="Contractors";
        public static final int QUALITY_SUBSCORE_QSCORE_COLNO = 4; // for CPS2 =8;
        public static final String QUALITY_SUBSCORE_QSCORE_COL_NAME = "Contractor Performance Score"; // for CPS2 ="General Quality\nSub-score";
        
        // public static final int QUALITY_SUBSCORE_ROWNO = 26;
        // public static final int QUALITY_SUBSCORE_STARTING_ROWNO = 5;        
        
        public static final int QUALITY_SUBSCORE_ROWNO = 12;        
        // QPS3 change request: Cat 2/J contains only 10 contractors instead of 12
        public static final int QUALITY_SUBSCORE_2J_ROWNO = 10;  
        public static final int SC1_QUALITY_SUBSCORE_STARTING_ROWNO = 6;        
        public static final int SC2N_QUALITY_SUBSCORE_STARTING_ROWNO = 23;        
        public static final int SC2J_QUALITY_SUBSCORE_STARTING_ROWNO = 36;        
        public static final int SC3N_QUALITY_SUBSCORE_STARTING_ROWNO = 53;        
        public static final int SC3J_QUALITY_SUBSCORE_STARTING_ROWNO = 66;        
        public static final int SC4_QUALITY_SUBSCORE_STARTING_ROWNO = 82;        
        
        //Ceiling Rate 1        
        public static final String CEILING_RATE_1_SHEETNAME = "Cat1";
        public static final String CEILING_RATE_1_TITLE = "Service Category 1";
        public static final int CEILING_RATE_1_TITLE_ROWNO = 1;
        public static final int CEILING_RATE_1_TITLE_COLNO = 1;
        public static final int CEILING_RATE_1_MINOR = 12; // =9 in QPS2
        public static final int CEILING_RATE_1_MAJOR = 4;
        public static final int CEILING_RATE_1_COL_OFFSET = 1;
        public static final int CEILING_RATE_1_MINOR_RESIDENT_ROWNO = 31;  // =28 in QPS2
        public static final int CEILING_RATE_1_MINOR_NON_RESIDENT_ROWNO = 49;  // =45 in QPS2
        public static final int CEILING_RATE_1_MINOR_OFFSHORE_ROWNO = 67;  // =62 in QPS2
        public static final int CEILING_RATE_1_MAJOR_RESIDENT_ROWNO = 35;
        public static final int CEILING_RATE_1_MAJOR_NON_RESIDENT_ROWNO = 52;
        public static final int CEILING_RATE_1_MAJOR_OFFSHORE_ROWNO = 69;
       // public static final int CEILING_RATE_1_SUPP_NO = 1;
        public static final int CEILING_RATE_1_SUPP_NO = 0;
        public static final int CEILING_RATE_1_SUPP_RESIDENT_ROWNO = 81;
        public static final int CEILING_RATE_1_SUPP_NON_RESIDENT_ROWNO = 85;
        public static final int CEILING_RATE_1_SUPP_OFFSHORE_ROWNO = 89;
        
        //Ceiling Rate 2       
        public static final String CEILING_RATE_2_SHEETNAME = "Cat2";
        public static final String CEILING_RATE_2_TITLE = "Service Category 2";
        public static final int CEILING_RATE_2_TITLE_ROWNO = 1;
        public static final int CEILING_RATE_2_TITLE_COLNO = 1;
        public static final int CEILING_RATE_2_MINOR = 12;  // =9 in QPS2
        public static final int CEILING_RATE_2_MAJOR = 10;  // =9 in QPS2
        public static final int CEILING_RATE_2_COL_OFFSET = 1;
        public static final int CEILING_RATE_2_MINOR_RESIDENT_ROW1NO =42;  // =36 in QPS2
        public static final int CEILING_RATE_2_MINOR_RESIDENT_ROW2NO = 75;  // =63 in QPS2
        public static final int CEILING_RATE_2_MINOR_NON_RESIDENT_ROW1NO = 109;  // =91 in QPS2
        public static final int CEILING_RATE_2_MINOR_NON_RESIDENT_ROW2NO = 142;  // =118 in QPS2
        public static final int CEILING_RATE_2_MINOR_OFFSHORE_ROW1NO = 175;  // =145 in QPS2
        public static final int CEILING_RATE_2_MINOR_OFFSHORE_ROW2NO = 208;  // =172 in QPS2
        public static final int CEILING_RATE_2_MAJOR_RESIDENT_ROW1NO = 56;  // =47 in QPS2
        public static final int CEILING_RATE_2_MAJOR_RESIDENT_ROW2NO = 89;  // =74 in QPS2
        public static final int CEILING_RATE_2_MAJOR_NON_RESIDENT_ROW1NO = 123;  // =102 in QPS2
        public static final int CEILING_RATE_2_MAJOR_NON_RESIDENT_ROW2NO = 156;  // =129 in QPS2
        public static final int CEILING_RATE_2_MAJOR_OFFSHORE_ROW1NO = 189;  // =156 in QPS2
        public static final int CEILING_RATE_2_MAJOR_OFFSHORE_ROW2NO = 222;  // =183 in QPS2
        public static final int CEILING_RATE_2_SUPP_NO = 0;
        //public static final int CEILING_RATE_2_SUPP_NO = 1;
        public static final int CEILING_RATE_2_SUPP_RESIDENT_ROWNO = 243;  // =201 in QPS2
        public static final int CEILING_RATE_2_SUPP_NON_RESIDENT_ROWNO = 247;  // =205 in QPS2
        public static final int CEILING_RATE_2_SUPP_OFFSHORE_ROWNO = 211;  // =209 in QPS2
        
        //Ceiling Rate 3        
        public static final String CEILING_RATE_3_SHEETNAME = "Cat3";
        public static final String CEILING_RATE_3_TITLE = "Service Category 3";
        public static final int CEILING_RATE_3_TITLE_ROWNO = 1;
        public static final int CEILING_RATE_3_TITLE_COLNO = 1;
        public static final int CEILING_RATE_3_MINOR = 12;  // =9 in QPS2
        public static final int CEILING_RATE_3_MAJOR = 12;  // =9 in QPS2
        public static final int CEILING_RATE_3_COL_OFFSET = 1;
        public static final int CEILING_RATE_3_MINOR_ONEOFF_RESIDENT_ROWNO = 47;  // =38 in QPS2
        public static final int CEILING_RATE_3_MINOR_ONEOFF_NON_RESIDENT_ROWNO = 79;  // =64 in QPS2
        public static final int CEILING_RATE_3_MINOR_ONEOFF_OFFSHORE_ROWNO = 111;  // =90 in QPS2
        public static final int CEILING_RATE_3_MAJOR_ONEOFF_RESIDENT_ROWNO = 61;  // =49 in QPS2
        public static final int CEILING_RATE_3_MAJOR_ONEOFF_NON_RESIDENT_ROWNO = 93;  // =75 in QPS2
        public static final int CEILING_RATE_3_MAJOR_ONEOFF_OFFSHORE_ROWNO = 125;  // =101 in QPS2
        public static final int CEILING_RATE_3_MINOR_ONGOING_RESIDENT_ROW1NO = 167;  // =140 in QPS2
        public static final int CEILING_RATE_3_MINOR_ONGOING_RESIDENT_ROW2NO = 201;  // =168 in QPS2
        public static final int CEILING_RATE_3_MINOR_ONGOING_NON_RESIDENT_ROW1NO = 235;  // =196 in QPS2
        public static final int CEILING_RATE_3_MINOR_ONGOING_NON_RESIDENT_ROW2NO = 269;  // =224 in QPS2
        public static final int CEILING_RATE_3_MINOR_ONGOING_OFFSHORE_ROW1NO = 303;  // =252 in QPS2
        public static final int CEILING_RATE_3_MINOR_ONGOING_OFFSHORE_ROW2NO = 337;  // =280 in QPS2
        public static final int CEILING_RATE_3_MAJOR_ONGOING_RESIDENT_ROW1NO = 181;  // =151 in QPS2
        public static final int CEILING_RATE_3_MAJOR_ONGOING_RESIDENT_ROW2NO = 215;  // =179 in QPS2
        public static final int CEILING_RATE_3_MAJOR_ONGOING_NON_RESIDENT_ROW1NO = 249;  // =207 in QPS2
        public static final int CEILING_RATE_3_MAJOR_ONGOING_NON_RESIDENT_ROW2NO = 283;  // =235 in QPS2
        public static final int CEILING_RATE_3_MAJOR_ONGOING_OFFSHORE_ROW1NO = 317;  // =263 in QPS2
        public static final int CEILING_RATE_3_MAJOR_ONGOING_OFFSHORE_ROW2NO = 351;  // =291 in QPS2
        public static final int CEILING_RATE_3_SUPP_NO = 0;
        //public static final int CEILING_RATE_3_SUPP_NO = 1;
        public static final int CEILING_RATE_3_ONEOFF_SUPP_RESIDENT_ROWNO = 118;
        public static final int CEILING_RATE_3_ONEOFF_SUPP_NON_RESIDENT_ROWNO = 122;
        public static final int CEILING_RATE_3_ONEOFF_SUPP_OFFSHORE_ROWNO = 126;
        public static final int CEILING_RATE_3_ONGOING_SUPP_RESIDENT_ROWNO = 309;
        public static final int CEILING_RATE_3_ONGOING_SUPP_NON_RESIDENT_ROWNO = 313;
        public static final int CEILING_RATE_3_ONGOING_SUPP_OFFSHORE_ROWNO = 317;
    
         //Ceiling Rate 4        
        public static final String CEILING_RATE_4_SHEETNAME = "Cat4";
        public static final String CEILING_RATE_4_TITLE = "Service Category 4";
        public static final int CEILING_RATE_4_TITLE_ROWNO = 1;
        public static final int CEILING_RATE_4_TITLE_COLNO = 1;
        public static final int CEILING_RATE_4_MINOR = 12; // =9 in QPS2
        public static final int CEILING_RATE_4_MAJOR = 5;
        public static final int CEILING_RATE_4_COL_OFFSET = 1;
        public static final int CEILING_RATE_4_MINOR_RESIDENT_ROWNO = 29;  // =25 in QPS2
        public static final int CEILING_RATE_4_MINOR_NON_RESIDENT_ROWNO = 47;  // =41 in QPS2
        public static final int CEILING_RATE_4_MINOR_OFFSHORE_ROWNO = 65;  // =57 in QPS2
        public static final int CEILING_RATE_4_MAJOR_RESIDENT_ROWNO = 30;
        public static final int CEILING_RATE_4_MAJOR_NON_RESIDENT_ROWNO = 46;
        public static final int CEILING_RATE_4_MAJOR_OFFSHORE_ROWNO = 62;
        public static final int CEILING_RATE_4_MINOR_INCIDENT_RESPONSE_SUPP_SERVICE_ROWNO = 89;  // =79 in QPS2
        public static final int CEILING_RATE_4_MAJOR_INCIDENT_RESPONSE_SUPP_SERVICE_ROWNO = 84;
        public static final int CEILING_RATE_4_MINOR_ITSECURITY_MONITORING_SERVICE_ROWNO = 111;  // =98 in QPS2
        public static final int CEILING_RATE_4_MAJOR_ITSECURITY_MONITORING_SERVICE_ROWNO = 103;
               
         //Work Assignment        
        public static final int WORK_ASSIGNMENT_SHEET = 0;
        public static final int WORK_ASSIGNMENT_TITLE_ROWNO = 0;
        public static final int WORK_ASSIGNMENT_TITLE_COLNO = 0;
        public static final String WORK_ASSIGNMENT_TITLE = "SOA-QPS3 Work Assignment Statistics as of";
        public static final int WORK_ASSIGNMENT_WANO_COLNO = 7;
        public static final int WORK_ASSIGNMENT_WANO_ROWNO = 0;
        public static final int WORK_ASSIGNMENT_START_ROWNO = 3;
        public static final int WORK_ASSIGNMENT_STATUS_COLNO = 7;                
        public static final int WORK_ASSIGNMENT_SERVICE_CATEGORY_COLNO = 0;
        public static final int WORK_ASSIGNMENT_DEPT_COLNO = 1;
        public static final int WORK_ASSIGNMENT_ISSUE_DATE_COLNO = 2;
        public static final int WORK_ASSIGNMENT_CLOSING_DATE_COLNO = 3;
        public static final int WORK_ASSIGNMENT_AWARD_CONTRACTOR_COLNO = 5;
        public static final int WORK_ASSIGNMENT_DEBAR_CONTRACTOR_COLNO = 25;
        public static final int WORK_ASSIGNMENT_AUTHORIZED_PERSON_COLNO = 19;
        public static final int WORK_ASSIGNMENT_FILE_PART_COLNO = 13;
        public static final int WORK_ASSIGNMENT_FILE_NO_COLNO = 14;
        public static final int WORK_ASSIGNMENT_WA_TITLE_COLNO = 9;
        public static final int WORK_ASSIGNMENT_AWARDED_DATE_COLNO = 6;
        
        /** QPS3: Add 3 fields Awarded Contract Value, Aggregated Effort and Total Project Cost for CPAR **/
        
        public static final int WORK_ASSIGNMENT_CONTRACT_VALUE_COLNO = 12;
        public static final int WORK_ASSIGNMENT_AGGREGATED_EFFORT_COLNO = 43;
        public static final int WORK_ASSIGNMENT_TOTAL_PROJECT_COST_COLNO = 46;
        public static final int WORK_ASSIGNMENT_POST_RANK_COLNO = 20;
        public static final int WORK_ASSIGNMENT_SERVICE_CONTRACT_NO_COLNO = 8;
        
        /** QPS3: Add 2 fields Planned Start Date and Planned Completion Date for work assignment **/
        public static final int WORK_ASSIGNMENT_PLANNED_START_DATE_COLNO = 15;
        public static final int WORK_ASSIGNMENT_PLANNED_COMPLETION_DATE_COLNO = 16;
        
        /** QPS3: Add 2 fields Contact Phone Number and Internet Mail for work assignment **/
        public static final int WORK_ASSIGNMENT_CONTACT_PHONE_NUMBER_COLNO = 22;
        public static final int WORK_ASSIGNMENT_INTERNET_MAIL_COLNO = 23;
        
        
         //ACL
        public static final String ACL_SHEETNAME = "Part I, II & III";
        public static final int ACL_START_ROWNO = 13;  
        public static final int ACL_HEADER_ROWNO = 11;  
        public static final int ACL_USER_ID_COLNO = 5;
        public static final String ACL_USER_ID_COL_NAME = "Departmental Portal ID";
        public static final int ACL_EFFECTIVE_DATE_COLNO = 7;
        public static final String ACL_EFFECTIVE_DATE_COL_NAME = "Access Right \nEffective Date (dd.mm.yyyy)";
        public static final int ACL_VALID_USER_COLNO = 11;        
        
        //Statistic Report & CPAR 
        public static final String CPAR_TEMPLATE_NAME = "QPS3-CPAR-Sample.doc";
        public static final String REPORT_MARKER= "${m}";
        public static final String STAT_REPORT_NAME = "ManagementStatisticsSample.xls";
        
        //Generate CPS
        public static final String CPS_TEMPLATE_NAME = "GenerateCPSSample.xls";
        public static final double CPS_DEFAULT_MARK = 22.5;
        public static final int CPS_EXCEL_CONTRACTOR_ID_COLUMN = 3;
        public static final int CPS_EXCEL_CONTRACTOR_NAME_COLUMN = 4;
        public static final int CPS_EXCEL_SCORE_COLUMN = 5;
        public static final int CPS_EXCEL_CAT_1_START_ROW = 7;
        public static final int CPS_EXCEL_CAT_2N_START_ROW = 24;
        public static final int CPS_EXCEL_CAT_2J_START_ROW = 37;
        public static final int CPS_EXCEL_CAT_3N_START_ROW = 54;
        public static final int CPS_EXCEL_CAT_3J_START_ROW = 67;
        public static final int CPS_EXCEL_CAT_4_START_ROW = 83;
        
        //CPARs Outstanding
        public static final String CPARS_OUTSTANDING_TEMPLATE_NAME = "CPARsOutstandingSample.xls";
        public static final int CPARS_OUTSTANDING_START_ROW = 4;
        public static final int CPARS_OUTSTANDING_NO_COLUMN = 1;
        public static final int CPARS_OUTSTANDING_SERVICE_CONTRACT_REF_NO_COLUMN = 2;
        public static final int CPARS_OUTSTANDING_DEPARTMENT_COLUMN = 3;
        public static final int CPARS_OUTSTANDING_CONTRACTOR_COLUMN = 4;
        public static final int CPARS_OUTSTANDING_TITLE_COLUMN = 5;
        public static final int CPARS_OUTSTANDING_AUTHORISED_PERSON_COLUMN = 6;
        public static final int CPARS_OUTSTANDING_POST_COLUMN = 7;
        public static final int CPARS_OUTSTANDING_CONTACT_PHONE_NUMBER_COLUMN = 8;
        public static final int CPARS_OUTSTANDING_INTERNET_MAIL_COLUMN = 9;
        
        //CPARs List Download
        public static final String CPARS_LIST_DOWNLOAD_TEMPLATE_NAME = "CPARsRecordSample.xls";
        public static final int CPARS_LIST_DOWNLOAD_START_ROW = 4;
        public static final int CPARS_LIST_DOWNLOAD_DEPARTMENT_ID_COLUMN = 1;
        public static final int CPARS_LIST_DOWNLOAD_CONTRACTOR_COLUMN = 2;
        public static final int CPARS_LIST_DOWNLOAD_SERVICE_CONTRACT_REF_NO_COLUMN = 3;
        public static final int CPARS_LIST_DOWNLOAD_CPAR_ATATUS_COLUMN = 4;
        public static final int CPARS_LIST_DOWNLOAD_AUTHORISED_PERSON_COLUMN = 5;
        public static final int CPARS_LIST_DOWNLOAD_POST_COLUMN = 6;
        public static final int CPARS_LIST_DOWNLOAD_CONTACT_PHONE_NUMBER_COLUMN = 7;
        public static final int CPARS_LIST_DOWNLOAD_INTERNET_MAIL_COLUMN = 8;
        public static final int CPARS_LIST_DOWNLOAD_WA_TITLE_COLUMN = 9;
        public static final int CPARS_LIST_DOWNLOAD_finalized_COLUMN = 10;
        public static final int CPARS_LIST_DOWNLOAD_START_DAY_COLUMN = 11;
        public static final int CPARS_LIST_DOWNLOAD_END_DAY_COLUMN = 12;
        // Section A
        public static final int CPARS_LIST_DOWNLOAD_A1_COLUMN = 13;
        public static final int CPARS_LIST_DOWNLOAD_A2_COLUMN = 14;
        public static final int CPARS_LIST_DOWNLOAD_A3_COLUMN = 15;
        public static final int CPARS_LIST_DOWNLOAD_A4_COLUMN = 16;
        public static final int CPARS_LIST_DOWNLOAD_A5_COLUMN = 17;
        public static final int CPARS_LIST_DOWNLOAD_A6_COLUMN = 18;
        public static final int CPARS_LIST_DOWNLOAD_A7_COLUMN = 19;
        public static final int CPARS_LIST_DOWNLOAD_A8_COLUMN = 20;
        public static final int CPARS_LIST_DOWNLOAD_A9_COLUMN = 21;
        // Section B
        public static final int CPARS_LIST_DOWNLOAD_B1_COLUMN = 22;
        public static final int CPARS_LIST_DOWNLOAD_B2_COLUMN = 23;
        public static final int CPARS_LIST_DOWNLOAD_B3_COLUMN = 24;
        public static final int CPARS_LIST_DOWNLOAD_B4_COLUMN = 25;
        public static final int CPARS_LIST_DOWNLOAD_B5_COLUMN = 26;
        // Section C
        public static final int CPARS_LIST_DOWNLOAD_C1_COLUMN = 27;
        public static final int CPARS_LIST_DOWNLOAD_C2_COLUMN = 28;
        public static final int CPARS_LIST_DOWNLOAD_C3_COLUMN = 29;
        public static final int CPARS_LIST_DOWNLOAD_C4_COLUMN = 30;
        public static final int CPARS_LIST_DOWNLOAD_C5_COLUMN = 31;
        // Overall
        public static final int CPARS_LIST_DOWNLOAD_OVERALL1_COLUMN = 32;
        public static final int CPARS_LIST_DOWNLOAD_OVERALL2_COLUMN = 33;
        public static final int CPARS_LIST_DOWNLOAD_OVERALL3_COLUMN = 34;
        
        public static final int CPARS_LIST_DOWNLOAD_PERFORMANCE_MARK_COLUMN = 35;

}
