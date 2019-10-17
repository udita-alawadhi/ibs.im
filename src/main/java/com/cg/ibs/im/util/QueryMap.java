package com.cg.ibs.im.util;

public interface QueryMap {
	// applicantId, firstName, lastName, fatherName, motherName, dob, gender,
	// mobileNumber,
	// alternateMobileNumber, emailId, aadharNumber, panNumber, accountType,
	// accountHolder, applicantStatus, applicationDate, linkedApplication,
	// existingCustomer
	public static String insertApplicantDetails = "INSERT INTO APPLICANTS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	//change
	public static String getAllApplications = "SELECT APPLICANT_ID, FIRST_NAME, LAST_NAME, FATHER_NAME, "
			+ "MOTHER_NAME, DOB, GENDER, MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID, "
			+ "AADHAR_NUMBER, PAN_NUMBER, ACCOUNT_TYPE, ACCOUNT_HOLDER, APPLICANT_STATUS, "
			+ "APPLICATION_DATE, LINKED_APPLICATION, EXISTING_CUSTOMER FROM APPLICANTS";

	public static String getApplicantDetails = "SELECT APPLICANT_ID, FIRST_NAME, LAST_NAME, FATHER_NAME,"
			+ "MOTHER_NAME, DOB, GENDER, MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID,"
			+ "AADHAR_NUMBER, PAN_NUMBER, ACCOUNT_TYPE, ACCOUNT_HOLDER, APPLICANT_STATUS,"
			+ "APPLICATION_DATE, LINKED_APPLICATION, EXISTING_CUSTOMER FROM APPLICANTS WHERE APPLICANT_ID = ?";
	
	public static String getAllApplicationsByStatus = "SELECT APPLICANT_ID, FIRST_NAME, LAST_NAME, FATHER_NAME,"
			+ "MOTHER_NAME, DOB, GENDER, MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID,"
			+ "AADHAR_NUMBER, PAN_NUMBER, ACCOUNT_TYPE, ACCOUNT_HOLDER, APPLICANT_STATUS,"
			+ "APPLICATION_DATE, LINKED_APPLICATION, EXISTING_CUSTOMER FROM APPLICANTS WHERE APPLICANT_STATUS = ?";
	
	public static String updateApplicantStatus = "UPDATE APPLICANTS SET APPLICANT_STATUS = 'APPROVED' "
			+ "WHERE APPLICANT_ID = ?";
	
	public static String updateLinkedApplication = "UPDATE APPLICANTS SET LINKED_APPLICATION = ? WHERE APPLICANT_ID = ?";
	
	public static String insertCustomersDetails = "INSERT INTO CUSTOMERS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static String getAllCustomerDetails = "SELECT UCI, USER_ID, PASSWORD, APPLICANT_ID, "
	  + "FIRST_NAME, LAST_NAME, FATHER_NAME, MOTHER_NAME, DOB, GENDER, "
	  + "MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID, AADHAR_NUMBER, PAN_NUMBER, "
	  + "ACCOUNT_TYPE, ACCOUNT_HOLDER FROM CUSTOMERS";
	
	public static String getCustomerDetails = "SELECT UCI, USER_ID, PASSWORD, APPLICANT_ID,"
	  + "FIRST_NAME, LAST_NAME, FATHER_NAME, MOTHER_NAME, DOB, GENDER,"
	   + "MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID, AADHAR_NUMBER, PAN_NUMBER,"
	  + "ACCOUNT_TYPE, ACCOUNT_HOLDER FROM CUSTOMERS WHERE UCI = ? ";
	
	public static String getCustomerDetailsByApplicantId = "SELECT UCI, USER_ID, PASSWORD, APPLICANT_ID,"
	   + "FIRST_NAME, LAST_NAME, FATHER_NAME, MOTHER_NAME, DOB, GENDER,"
	   + "MOBILE_NUMBER, ALTERNATE_MOBILE_NUMBER, EMAIL_ID, AADHAR_NUMBER, PAN_NUMBER,"
	   + "ACCOUNT_TYPE, ACCOUNT_HOLDER FROM CUSTOMERS WHERE APPLICANT_ID = ? ";
	
	public static String getAllCustomers = "SELECT UCI FROM CUSTOMERS";
	
	public static String updateCustomerPassword = "UPDATE CUSTOMERS SET PASSWORD= ? WHERE UCI = ?";
	
	
}
