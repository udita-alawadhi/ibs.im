package com.cg.ibs.im.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.util.OracleConnection;
import com.cg.ibs.im.util.QueryMap;

public class AccountDaoImpl implements AccountDao {
	private CustomerBean newCustomer = new CustomerBean();
	
	@Override
	public boolean checkAccountExists(BigInteger accountNumber) {
		boolean result = false;
		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.checkAccountNumber);) {
			String tempAccountNumber = accountNumber.toString();
			preparedStatement.setBigDecimal(1, new BigDecimal(tempAccountNumber));
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				int check = preparedStatement.executeUpdate();
				
				if(check==1) {
					result = true;
				}
			}
		} catch (Exception exception) {
			//handle exception
		}
		return result;
	}
}
