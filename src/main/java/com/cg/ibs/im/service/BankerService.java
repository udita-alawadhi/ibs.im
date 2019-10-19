package com.cg.ibs.im.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.cg.ibs.bean.AccountBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;

public interface BankerService {
	boolean verifyLogin(String user, String password) throws IBSCustomException;
	
	Set<Long> viewPendingApplications() throws IBSCustomException;
	
	Set<Long> viewApprovedApplications() throws IBSCustomException;
	
	Set<Long> viewDeniedApplications() throws IBSCustomException;
	
	boolean updateStatus(long applicantId, ApplicantStatus applicantStatus) throws IBSCustomException;
	
	String generatePassword(long applicantId);
	
	CustomerBean createNewCustomer(ApplicantBean applicant) throws IBSCustomException, SQLException;

	boolean isApplicantPresentInPendingList(long applicantId) throws IBSCustomException;

	boolean isApplicantPresent(long applicantId) throws IBSCustomException;

	ApplicantBean displayDetails(long applicantId) throws IBSCustomException;

	String generateUsername(long applicantId) throws IBSCustomException;

	boolean download(String destPath, String fileName) throws IBSCustomException;

	List<String> getFilesAvialable();

	AccountBean createNewAccount(ApplicantBean newApplicant) throws IBSCustomException;

	AccountBean createNewAccount(CustomerBean newCustomer);
}
