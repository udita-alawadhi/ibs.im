package com.cg.ibs.im.dao;

import java.sql.SQLException;
import java.util.Set;

import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.im.exception.IBSCustomException;

public interface ApplicantDao {
	
	boolean saveApplicant(ApplicantBean applicant) throws IBSCustomException;
	
	Set<Long> getAllApplicants() throws IBSCustomException;
	
	ApplicantBean getApplicantDetails(long applicantId) throws IBSCustomException;
	
	Set<Long> getApplicantsByStatus(ApplicantStatus applicantStatus) throws IBSCustomException;

	boolean isApplicantPresent(long applicantId) throws IBSCustomException;

	boolean updateStatusToApproved(ApplicantBean applicant) throws IBSCustomException;

	boolean updateLinkedApplication(ApplicantBean applicant) throws IBSCustomException;

	boolean validateBankLogin(String userId, String password) throws IBSCustomException;

	
	
	
	
}
