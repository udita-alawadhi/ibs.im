package com.cg.ibs.im.dao;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;

public interface CustomerDao {
	
	
	boolean saveCustomer(CustomerBean customer) throws IBSCustomException;
	
	CustomerBean getCustomerDetails(String uci) throws IBSCustomException;
	
	Set<BigInteger> getAllCustomers();

	CustomerBean getCustomerByApplicantId(long applicantId);

	boolean copy(String srcPath, String destPath);

	boolean updatePassword(CustomerBean customer, String password);

	boolean updateLoginCount(CustomerBean customer);

	boolean checkCustomerByUsernameExists(String username);

	BigInteger getHighestUciValue();

	boolean checkCustomerExists(BigInteger uci);
}
