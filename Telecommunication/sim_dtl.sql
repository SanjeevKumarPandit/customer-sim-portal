CREATE TABLE `sim_dtl` (
   `SIM_ID` int NOT NULL AUTO_INCREMENT,
   `COMPANY_NAME` varchar(30) DEFAULT NULL,
   `ACTIVATION_DATE` date DEFAULT NULL,
   `SIM_STATUS` varchar(1) DEFAULT NULL,
   `CUSTOMER_ID` int DEFAULT NULL,
   PRIMARY KEY (`SIM_ID`)
 )