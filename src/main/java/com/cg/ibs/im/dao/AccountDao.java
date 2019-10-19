package com.cg.ibs.im.dao;

import java.math.BigInteger;

import com.cg.ibs.bean.AccountBean;
import com.cg.ibs.im.exception.IBSCustomException;

public interface AccountDao {
	
	public boolean saveAccount(BigInteger uci, AccountBean account) throws IBSCustomException;

	AccountBean getAccountDetails(BigInteger uci) throws IBSCustomException;

	boolean checkAccountExists(BigInteger accountNumber) throws IBSCustomException;

}
