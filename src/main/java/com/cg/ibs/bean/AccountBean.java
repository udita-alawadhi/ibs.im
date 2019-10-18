package com.cg.ibs.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class AccountBean {
	private BigInteger accountNumber;
	private Set<BigInteger> accountHolders;
	private BigDecimal currentBalance;
	private AccountType accountType;
	private String transactionPassword;
	private LocalDate accountCreationDate;

	public AccountBean() {
		super();
	}

	
	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public BigInteger getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Set<BigInteger> getAccountHolders() {
		return accountHolders;
	}

	public void setAccountHolders(Set<BigInteger> accountHolders) {
		this.accountHolders = accountHolders;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountBean [accountNumber=");
		builder.append(accountNumber);
		builder.append(", currentBalance=");
		builder.append(currentBalance);
		builder.append(", Account Type: ");
		builder.append(accountType);
		builder.append("]");
		return builder.toString();
	}


	public String getTransactionPassword() {
		return transactionPassword;
	}


	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}


	public LocalDate getAccountCreationDate() {
		return accountCreationDate;
	}


	public void setAccountCreationDate(LocalDate accountCreationDate) {
		this.accountCreationDate = accountCreationDate;
	}

}
