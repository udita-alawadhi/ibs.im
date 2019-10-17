package com.cg.ibs.im.dao;

import java.sql.SQLException;
import java.util.Set;

import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.im.exception.IBSCustomException;

public interface ApplicantDao {
	
	boolean saveApplicant(ApplicantBean applicant) throws SQLException; //change exception
	
	Set<Long> getAllApplicants();
	
	ApplicantBean getApplicantDetails(long applicantId) throws IBSCustomException;
	
	Set<Long> getApplicantsByStatus(ApplicantStatus applicantStatus);

	boolean isApplicantPresent(long applicantId);

	boolean updateStatusToApproved(ApplicantBean applicant);

	boolean updateLinkedApplication(ApplicantBean applicant);

	
	
	
	
}
