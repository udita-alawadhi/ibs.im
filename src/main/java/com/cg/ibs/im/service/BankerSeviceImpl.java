package com.cg.ibs.im.service;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cg.ibs.bean.AccountBean;
import com.cg.ibs.bean.AccountHolder;
import com.cg.ibs.bean.AccountType;
import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.dao.AccountDao;
import com.cg.ibs.im.dao.AccountDaoImpl;
import com.cg.ibs.im.dao.AddressDao;
import com.cg.ibs.im.dao.AddressDaoImpl;
import com.cg.ibs.im.dao.ApplicantDao;
import com.cg.ibs.im.dao.ApplicantDaoImpl;
import com.cg.ibs.im.dao.CustomerDao;
import com.cg.ibs.im.dao.CustomerDaoImpl;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.exception.IBSException;

public class BankerSeviceImpl implements BankerService {
	CustomerDao customerDao = new CustomerDaoImpl();
	ApplicantDao applicantDao = new ApplicantDaoImpl();
	CustomerService customerService = new CustomerServiceImpl();
	CustomerBean customer = new CustomerBean();
	AccountBean account = new AccountBean();
	AddressDao addressDao = new AddressDaoImpl();
	AccountDao accountDao = new AccountDaoImpl();

	private static BigInteger uciConstant = new BigInteger("1111222210000000");
	private static BigInteger accountVariable = new BigInteger("55010010000");

	public static BigInteger generateUci() {
		uciConstant = uciConstant.add(new BigInteger("1"));
		return uciConstant;
	}

	public static BigInteger generateAccountNumber() {
		accountVariable = accountVariable.add(new BigInteger("1"));
		return accountVariable;
	}

	@Override
	public boolean verifyLogin(String user, String password) {
		boolean result = false;
		result = applicantDao.validateBankLogin(user, password);
		return result;
	}

	@Override
	public Set<Long> viewPendingApplications() {
		return applicantDao.getApplicantsByStatus(ApplicantStatus.PENDING);
	}

	@Override
	public Set<Long> viewApprovedApplications() {
		return applicantDao.getApplicantsByStatus(ApplicantStatus.APPROVED);
	}

	@Override
	public Set<Long> viewDeniedApplications() {
		return applicantDao.getApplicantsByStatus(ApplicantStatus.DENIED);
	}

	@Override
	public boolean updateStatus(long applicantId, ApplicantStatus applicantStatus) throws IBSCustomException {
		boolean result = false;
		if (applicantDao.getApplicantDetails(applicantId) != null) {
			applicantDao.getApplicantDetails(applicantId).setApplicantStatus(applicantStatus);
			result = true;
		} else {
			throw new IBSCustomException(IBSException.invalidApplicantId);
		}
		return result;
	}

	@Override
	public String generatePassword(long applicantId) {
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder stringBuffer = new StringBuilder(8);

		for (int i = 0; i < 8; i++) {
			int index = (int) (alphaNumeric.length() * Math.random());
			stringBuffer.append(alphaNumeric.charAt(index));
		}
		return stringBuffer.toString();
	}

	@Override
	public String generateUsername(long applicantId) throws IBSCustomException {
		ApplicantBean applicant = applicantDao.getApplicantDetails(applicantId);

		String username = applicant.getFirstName().charAt(0) + applicant.getLastName();
		System.out.println(username);
		if (username.length() > 12) {
			username = username.substring(0, 10);
		}
		int index = 1;
		while (!checkUsernameIsUnique(username)) {
			username = username.concat(String.valueOf(index));
			if (username.length() > 12) {
				username = username.substring(0, 11);
			}
			index++;
		}
		return username;
	}

	public boolean checkUsernameIsUnique(String username) {
		boolean result = true;
		try {
			if (customerDao.checkCustomerByUsernameExists(username)) {
				result = false;
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return result;
	}

	@Override
	public CustomerBean createNewCustomer(ApplicantBean applicant) throws IBSCustomException, SQLException {
		customer = new CustomerBean();
		long applicantId = applicant.getApplicantId();
		// check uci is not present
		BigInteger customerUci = generateUci();
		while (customerDao.checkCustomerExists(customerUci)) {
			customerUci = generateUci();
			// change this
		}
		customer.setUci(customerUci);
		customer.setApplicant(applicant);
		customer.setUserId(generateUsername(applicantId));
		customer.setPassword(generatePassword(applicantId));
		Set<AccountBean> accounts = new HashSet<AccountBean>();
		if (applicant.getAccountHolder() != AccountHolder.SECONDARY) {
			account = createNewAccount(applicant);
			account.setAccountType(AccountType.JOINT);
			accounts.add(account);
			customer.setAccounts(accounts);
		} else {
			CustomerBean customer2 = customerService.getCustomerByApplicantId(applicant.getLinkedApplication());
			customer.setAccounts(customer2.getAccounts());// get account number of linkedApplicantId
		}
		applicant.setExistingCustomer(true);
		applicant.setApplicantStatus(ApplicantStatus.APPROVED);
		customerService.updateApplicantStatusToApproved(applicant);
		// customerService.saveApplicantDetails(applicant);
		customer.setApplicant(applicant);

		boolean result = customerDao.saveCustomer(customer);
		if (result == true) {
			return customer;
		} else
			throw new IBSCustomException(IBSException.customerNotPresent);
	}

	@Override
	public AccountBean createNewAccount(ApplicantBean newApplicant) {
		BigInteger accountNumber = generateAccountNumber();
		while (accountDao.checkAccountExists(accountNumber)) {
			accountNumber = generateAccountNumber();
		}
		account.setAccountNumber(accountNumber);
		account.setCurrentBalance(new BigDecimal("0.00"));
		account.setAccountType(newApplicant.getAccountType());
		account.setAccountCreationDate(LocalDate.now());
		// set other details
		return account;
	}

	@Override
	public AccountBean createNewAccount(CustomerBean newCustomer) {
		account.setAccountNumber(generateAccountNumber());
		account.setCurrentBalance(new BigDecimal("0.00"));
		// set other details
		return account;
	}

	@Override
	public ApplicantBean displayDetails(long applicantId) throws IBSCustomException {
		ApplicantBean applicant = applicantDao.getApplicantDetails(applicantId);
		applicant.setPermanentAddress(getPermanentAddress(applicantId));
		applicant.setCurrentAddress(getCurrentAddress(applicantId));
		return applicant;

	}

	public AddressBean getPermanentAddress(long applicantId) {
		AddressBean address = new AddressBean();
		if (applicantId != 0) {
			address = addressDao.getPermanentAddress(applicantId);
		}
		return address;
	}

	public AddressBean getCurrentAddress(long applicantId) {
		AddressBean address = new AddressBean();
		if (applicantId != 0) {
			address = addressDao.getCurrentAddress(applicantId);
		}
		return address;
	}

	@Override
	public List<String> getFilesAvialable() {
		List<String> files = new ArrayList<>();
		File upLoc = new File(CustomerDaoImpl.UPLOADS_LOC);
		for (File f : upLoc.listFiles()) {
			files.add(f.getName());
		}
		return files;
	}

	@Override
	public boolean download(String destPath, String fileName) {
		String srcPath = CustomerDaoImpl.UPLOADS_LOC + "/" + fileName;
		destPath += "/" + fileName;
		return customerDao.copy(srcPath, destPath);
	}

	@Override
	public boolean isApplicantPresent(long applicantId) {
		boolean result = false;
		if (applicantId != 0) {
			result = applicantDao.isApplicantPresent(applicantId);
		}

		return result;
	}

	@Override
	public boolean isApplicantPresentInPendingList(long applicantId) {
		boolean result = false;
		if (applicantId != 0) {
			Set<Long> pendingApplicants = applicantDao.getApplicantsByStatus(ApplicantStatus.PENDING);
			Iterator<Long> it = pendingApplicants.iterator();
			while (it.hasNext()) {
				if (it.next() == applicantId) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

}
