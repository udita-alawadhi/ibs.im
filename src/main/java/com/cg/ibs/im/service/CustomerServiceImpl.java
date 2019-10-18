package com.cg.ibs.im.service;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.dao.AddressDao;
import com.cg.ibs.im.dao.AddressDaoImpl;
import com.cg.ibs.im.dao.ApplicantDao;
import com.cg.ibs.im.dao.ApplicantDaoImpl;
import com.cg.ibs.im.dao.CustomerDao;
import com.cg.ibs.im.dao.CustomerDaoImpl;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.exception.IBSException;

public class CustomerServiceImpl implements CustomerService {
	private ApplicantBean applicant = new ApplicantBean();
	private ApplicantDao applicantDao = new ApplicantDaoImpl();
	private CustomerBean customer = new CustomerBean();
	private CustomerDao customerDao = new CustomerDaoImpl();
	private AddressDao addressDao = new AddressDaoImpl();
	static long applicationId = 10000; // 5 digit applicant ID

	public static long generateApplicantId() {
		return ++applicationId;
	}

	@Override
	public long generatedApplicantId() {
		return generateApplicantId();
	}

	@Override
	public boolean verifyName(String name) {
		return name.matches("[a-zA-z ]+");
	}

	@Override
	public boolean verifyDob(LocalDate ld) {
		boolean result = false;
		LocalDate date = LocalDate.now();
		Period age = Period.between(ld, date);
		int agen = age.getYears();

		if (agen >= 18 && agen < 110) {
			result = true;
		}
		return result;

	}

	@Override
	public boolean verifyMobileNumber(String mobileNumber) {
		Pattern pattern = Pattern.compile("[6-9][0-9]{9}");
		Matcher matcher = pattern.matcher(mobileNumber);
		return (matcher.find() && matcher.group().equals(mobileNumber));
	}

	@Override
	public boolean verifyPincode(String pinCode) {
		return pinCode.matches("[0-9]{6}");
	}

	@Override
	public boolean verifyAadharNumber(String aadharNumber) {
		return aadharNumber.matches("[0-9]{12}") || aadharNumber.matches("[0-9]{4} [0-9]{4} [0-9]{4}");
	}

	@Override
	public boolean verifyPanNumber(String panNumber) {
		Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
		Matcher matcher = pattern.matcher(panNumber);
		return (matcher.find() && matcher.group().equals(panNumber));
	}

	@Override
	public boolean verifyEmailId(String emailId) {
		return matchesAtLeastOneRegex(emailId);

	}

	public boolean matchesAtLeastOneRegex(String input) {
		Pattern pattern1 = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+]+.[a-z]+$");
		Pattern pattern2 = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+]+.[a-z]+.[a-z]+$");

		Matcher matcher1 = pattern1.matcher("");
		Matcher matcher2 = pattern2.matcher("");
		return matcher1.reset(input).matches() || matcher2.reset(input).matches();
	}

	@Override
	public boolean verifyMobileNumbers(String mobile1, String mobile2) {
		boolean result = false;
		if (mobile1.equals(mobile2)) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean verifyApplicantId(long applicantId) throws IBSCustomException {
		boolean result = false;
		if (applicantDao.getApplicantDetails(applicantId).getApplicantId() == applicantId) {
			result = true;
		} else {
			throw new IBSCustomException(IBSException.invalidApplicantId);
		}
		return result;
	}

	@Override
	public ApplicantStatus checkStatus(long applicantId) throws IBSCustomException {
	
		applicant = applicantDao.getApplicantDetails(applicantId);
		return applicant.getApplicantStatus();
	}

	@Override
	public boolean login(String uci, String password) throws IBSCustomException {
		boolean result = false;

		customer = getCustomerDetails(uci);
		
		if (customer != null) {
			if (customer.getUci().toString().equals(uci)) {
				if (customer.getPassword().equals(password)) {
					result = true;
				} else {
					throw new IBSCustomException(IBSException.incorrectPassword);
				}
			} else {
				throw new IBSCustomException(IBSException.incorrectUsername);
			}
		}
		return result;
	}

	@Override
	public boolean checkCustomerDetails(String confirm1, String confirm2) {
		boolean result = false;
		if (confirm1.equals(confirm2)) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean updateUserId(CustomerBean customer, String userId) throws IBSCustomException {
		boolean result = false;
		if (customer != null) {
			customer.setUserId(userId);
			result = true;
		} else {
			throw new IBSCustomException(IBSException.customerNotPresent);
		}
		return result;

	}

	@Override
	public boolean updatePassword(CustomerBean customer, String password) throws IBSCustomException {
		boolean result = false;
		if (customer != null) {
			customerDao.updatePassword(customer, password);
			result = true;
		} else {
			throw new IBSCustomException(IBSException.customerNotPresent);
		}
		return result;
	}
	
	@Override
	public boolean updateLoginCount(CustomerBean customer) throws IBSCustomException {
		boolean result = false;
		if (customer != null) {
			if(customer.getLogin()==0)
				customerDao.updateLoginCount(customer);
			result = true;
		} else {
			throw new IBSCustomException(IBSException.customerNotPresent);
		}
		return result;
	}

	@Override
	public boolean saveApplicantDetails(ApplicantBean applicant) throws IBSCustomException, SQLException {

		boolean result = false;
		if (applicant.getApplicantId() == 0) {
			long applicantId = generateApplicantId();
			while (applicantDao.isApplicantPresent(applicantId)) {
				applicantId = generateApplicantId();
			}
			applicant.setApplicantId(applicantId);
		}
		if (applicant != null) {

			applicantDao.saveApplicant(applicant);
			result = true;
		} else {
			throw new IBSCustomException(IBSException.applicantNotFound);
		}
		return result;
	}

	@Override
	public boolean storeCustomerDetails(CustomerBean customerBean) throws IBSCustomException {
		boolean result = false;
		result = customerDao.saveCustomer(customerBean);
		return result;
	}

	@Override
	public CustomerBean getCustomerDetails(String uci) throws IBSCustomException {
		customer = customerDao.getCustomerDetails(uci);
		return customer;
	}

	@Override
	public boolean saveCurrentAddress(long applicantId, AddressBean address) {
		boolean result = false;
		if(address!=null) {
			addressDao.saveCurrentAddress(applicantId, address);
			result = true;
		}
		return result;
	}
	
	@Override
	public boolean savePermanentAddress(long applicantId, AddressBean address) {
		boolean result = false;
		if(address!=null) {
			addressDao.savePermanentAddress(applicantId, address);
			result = true;
		}
		return result;
	}
	
	@Override
	public boolean isCustomerValid(String uci) throws IBSCustomException {
		boolean result = false;
		customer = customerDao.getCustomerDetails(uci);
		if (customer != null) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean firstLogin(String userUci) throws IBSCustomException {
		boolean result = false;
		customer = getCustomerDetails(userUci);
		if (customer.getLogin() == 0) {
			result = true;
		}
		return result;
	}

	@Override
	public ApplicantBean getApplicantDetails(long applicantId) throws IBSCustomException {
		applicant = applicantDao.getApplicantDetails(applicantId);
		return applicant;
	}

	@Override
	public boolean upload(String srcPath) {
		File updLoc = new File(CustomerDaoImpl.UPLOADS_LOC);
		while (!updLoc.exists()) {
			updLoc.mkdir();
		}
		File srcFile = new File(srcPath);
		String fileName = srcFile.getName();
		String destPath = CustomerDaoImpl.UPLOADS_LOC + "/" + fileName;
		return customerDao.copy(srcPath, destPath);
	}

	@Override
	public boolean updateApplicantStatusToApproved(ApplicantBean applicant) {
		boolean result = false;

		if (applicantDao.updateStatusToApproved(applicant)) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean updateLinkedApplication(ApplicantBean applicant) {
		boolean result = false;

		if (applicantDao.updateLinkedApplication(applicant)) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean storeApplicantDetails(ApplicantBean applicant) throws SQLException {
		boolean result = false;
		if (applicant != null) {
			applicantDao.saveApplicant(applicant);
			result = true;
		}
		return result;

	}

	@Override
	public CustomerBean getCustomerByApplicantId(long applicantId) {
		System.out.println("reached getCustomerByApplicantId");
		CustomerBean customer = customerDao.getCustomerByApplicantId(applicantId);
		return customer;
	}

}
