package com.cg.ibs.im.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.cg.ibs.bean.AccountBean;
import com.cg.ibs.bean.AccountType;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.exception.IBSException;
import com.cg.ibs.im.util.OracleConnection;
import com.cg.ibs.im.util.QueryMap;

public class AccountDaoImpl implements AccountDao {
	private CustomerBean newCustomer = new CustomerBean();
	AccountBean account = new AccountBean();

	@Override
	public boolean saveAccount(BigInteger uci, AccountBean account) throws IBSCustomException { // CHECK !!!!!!!!!
		boolean result = false;
		System.out.println("reached here");
		if (account != null) {
			Connection connection = OracleConnection.callConnection();
			try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.SAVE_ACCOUNT_DETAILS);) {

				preparedStatement.setBigDecimal(1, new BigDecimal(account.getAccountNumber()));
				System.out.println("account no. set");
				preparedStatement.setBigDecimal(2, new BigDecimal(uci));
				System.out.println("uci");
				preparedStatement.setBigDecimal(3, account.getCurrentBalance());
				System.out.println("balance");
				preparedStatement.setString(4, account.getTransactionPassword());
				LocalDate accCreationDate = account.getAccountCreationDate();
				java.sql.Date date = java.sql.Date.valueOf(accCreationDate);
				preparedStatement.setDate(5, date);
				preparedStatement.setString(6, account.getAccountType().toString());
				preparedStatement.setInt(7, account.getTransId());
				System.out.println("done!");

				int index = preparedStatement.executeUpdate();
				
				if(index>0)
					result = true;
			} catch (SQLException exception1) {
				throw new IBSCustomException(IBSException.SQLError);
			}
		}
		return result;
	}

	@Override
	public AccountBean getAccountDetails(BigInteger uci)  throws IBSCustomException {// CHECK!!!!!!! for UCI

		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.GET_ACCOUNT_DETAILS);) {
			preparedStatement.setBigDecimal(2, new BigDecimal(uci));
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					account.setAccountNumber(new BigInteger(resultSet.getBigDecimal(1).toString()));
					// accountBean.setAccountHolders(resultSet.get);
					account.setCurrentBalance(resultSet.getBigDecimal(3));
					account.setTransactionPassword(resultSet.getString(4));
					java.sql.Date date = resultSet.getDate(5);
					LocalDate accCreationDate = date.toLocalDate();
					account.setAccountCreationDate(accCreationDate);
					String accountType = resultSet.getString(6);
					if (accountType.toUpperCase().equals("INDIVIDUAL")) {
						account.setAccountType(AccountType.INDIVIDUAL);
					} else if (accountType.toUpperCase().equals("JOINT")) {
						account.setAccountType(AccountType.JOINT);
					}
				}
			}
		} catch (SQLException exception) {
			throw new IBSCustomException(IBSException.SQLError);
		}
		return account;
	}

	@Override
	public boolean checkAccountExists(BigInteger accountNumber) throws IBSCustomException {
		boolean result = false;
		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.CHECK_ACCOUNT_NUMBER);) {
			String tempAccountNumber = accountNumber.toString();
			preparedStatement.setBigDecimal(1, new BigDecimal(tempAccountNumber));
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				int check = preparedStatement.executeUpdate();

				if (check == 1) {
					result = true;
				}
			}
		} catch (SQLException exception) {
			throw new IBSCustomException(IBSException.SQLError);
		}
		return result;
	}
}
