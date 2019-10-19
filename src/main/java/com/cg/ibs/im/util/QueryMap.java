package com.cg.ibs.im.util;

public interface QueryMap {
	// applicantId, firstName, lastName, fatherName, motherName, dob, gender,
	// mobileNumber,
	// alternateMobileNumber, emailId, aadharNumber, panNumber, accountType,
	// accountHolder, applicantStatus, applicationDate, linkedApplication,
	// existingCustomer
	public static String INSERT_APPLICANT_DETAILS = "INSERT INTO APPLICANTS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	//change
	public static String GET_ALL_APPLICATIONS = "SELECT APPLICANT_ID, FIRST_NAME, LAST_NAME, FATHER_NAME, "
			+ "MOTHER_NAME, DOB, GENDER, MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID, "
			+ "AADHAR_NUMBER, PAN_NUMBER, ACCOUNT_TYPE, ACCOUNT_HOLDER, APPLICANT_STATUS, "
			+ "APPLICATION_DATE, LINKED_APPLICATION, EXISTING_CUSTOMER FROM APPLICANTS";

	public static String GET_APPLICANT_DETAILS = "SELECT APPLICANT_ID, FIRST_NAME, LAST_NAME, FATHER_NAME,"
			+ "MOTHER_NAME, DOB, GENDER, MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID,"
			+ "AADHAR_NUMBER, PAN_NUMBER, ACCOUNT_TYPE, ACCOUNT_HOLDER, APPLICANT_STATUS,"
			+ "APPLICATION_DATE, LINKED_APPLICATION, EXISTING_CUSTOMER FROM APPLICANTS WHERE APPLICANT_ID = ?";
	
	public static String GET_ALL_APPLICATIONS_BY_STATUS = "SELECT APPLICANT_ID FROM APPLICANTS WHERE APPLICANT_STATUS = ?";
	
	public static String UPDATE_APPLICANT_STATUS = "UPDATE APPLICANTS SET APPLICANT_STATUS = 'APPROVED' "
			+ "WHERE APPLICANT_ID = ?";
	
	public static String UPDATE_LINKED_APPLICATION = "UPDATE APPLICANTS SET LINKED_APPLICATION = ? WHERE APPLICANT_ID = ?";
	
	public static String INSERT_CUSTOMERS_DETAILS = "INSERT INTO CUSTOMERS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static String GET_ALL_CUSTOMER_DETAILS = "SELECT UCI, USER_ID, PASSWORD, APPLICANT_ID, "
	  + "FIRST_NAME, LAST_NAME, FATHER_NAME, MOTHER_NAME, DOB, GENDER, "
	  + "MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID, AADHAR_NUMBER, PAN_NUMBER, "
	  + "ACCOUNT_TYPE, ACCOUNT_HOLDER FROM CUSTOMERS";
	
	public static String GET_CUSTOMER_DETAILS = "SELECT UCI, USER_ID, PASSWORD, APPLICANT_ID,"
	  + "FIRST_NAME, LAST_NAME, FATHER_NAME, MOTHER_NAME, DOB, GENDER,"
	   + "MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID, AADHAR_NUMBER, PAN_NUMBER,"
	  + "ACCOUNT_TYPE, ACCOUNT_HOLDER, LOGIN_COUNT FROM CUSTOMERS WHERE UCI = ? ";
	
	public static String GET_CUSTOMER_DETAILS_BY_APPLICANTID = "SELECT UCI, USER_ID, PASSWORD, APPLICANT_ID,"
	   + "FIRST_NAME, LAST_NAME, FATHER_NAME, MOTHER_NAME, DOB, GENDER,"
	   + "MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID, AADHAR_NUMBER, PAN_NUMBER,"
	   + "ACCOUNT_TYPE, ACCOUNT_HOLDER FROM CUSTOMERS WHERE APPLICANT_ID = ? ";
	
	public static String GET_ALL_CUSTOMERS = "SELECT UCI FROM CUSTOMERS";
	
	public static String UPDATE_CUSTOMER_PASSWORD = "UPDATE CUSTOMERS SET PASSWORD= ? WHERE UCI = ?";
	
	public static String UPDATE_LOGIN_COUNT = "UPDATE CUSTOMERS SET LOGIN_COUNT= 1 WHERE UCI = ?";
	
	public static String SAVE_PERMANENT_ADDRESS = "INSERT INTO PERMANENT_ADDRESS VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static String SAVE_CURRENT_ADDRESS = "INSERT INTO CURRENT_ADDRESS VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static String GET_PERMANENT_ADDRESS = "SELECT APPLICANT_ID, HOUSE_NUMBER, STREET_NAME, AREA, LANDMARK, CITY,"
			+ "STATE, COUNTRY, PINCODE FROM PERMANENT_ADDRESS WHERE APPLICANT_ID=?";

	public static String GET_CURRENT_ADDRESS = "SELECT APPLICANT_ID, HOUSE_NUMBER, STREET_NAME, AREA, LANDMARK, CITY,"
			+ "STATE, COUNTRY, PINCODE FROM CURRENT_ADDRESS WHERE APPLICANT_ID=?";
	
	public static String CHECK_USERNAME = "SELECT USER_ID FROM CUSTOMERS WHERE USER_ID= ? ";
	
	public static String SELECT_HIGHEST_UCI = "SELECT MAX(UCI) FROM CUSTOMERS";
	
	public static String CHECK_UCI = "SELECT UCI FROM CUSTOMERS WHERE UCI= ? ";
	
	public static String CHECK_ACCOUNT_NUMBER = "SELECT ACCOUNT_NUMBER FROM ACCOUNT WHERE ACCOUNT_NUMBER=?";
	
	public static String BANKER_DETAILS = "SELECT admin_id, password from bankadmins where admin_id=?";
	
	public static String SAVE_ACCOUNT_DETAILS =  "INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?,?)";
	
	public static String GET_ACCOUNT_DETAILS = "SELECT ACCOUNT_NUMBER, UCI, BALANCE, TRANSAC_PASS, ACC_CREATION_DATE, ACCOUNT_TYPE "
			+ "FROM ACCOUNT WHERE UCI = ?";
	
}
