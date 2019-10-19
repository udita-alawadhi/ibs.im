package com.cg.ibs.im.dao;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;

public interface CustomerDao {
	
	
	boolean saveCustomer(CustomerBean customer) throws IBSCustomException;
	
	CustomerBean getCustomerDetails(String uci) throws IBSCustomException;
	
	Set<BigInteger> getAllCustomers() throws IBSCustomException;

	CustomerBean getCustomerByApplicantId(long applicantId) throws IBSCustomException;

	boolean copy(String srcPath, String destPath) throws IBSCustomException;

	boolean updatePassword(CustomerBean customer, String password) throws IBSCustomException;

	boolean updateLoginCount(CustomerBean customer) throws IBSCustomException;

	boolean checkCustomerByUsernameExists(String username) throws IBSCustomException;

	BigInteger getHighestUciValue() throws IBSCustomException;

	boolean checkCustomerExists(BigInteger uci) throws IBSCustomException;
}
