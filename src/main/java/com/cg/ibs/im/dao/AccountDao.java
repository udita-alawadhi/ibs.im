package com.cg.ibs.im.dao;

import java.math.BigInteger;

public interface AccountDao {

	boolean checkAccountExists(BigInteger accountNumber);

}
