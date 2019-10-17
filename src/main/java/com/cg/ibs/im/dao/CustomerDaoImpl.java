package com.cg.ibs.im.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.cg.ibs.bean.AccountBean;
import com.cg.ibs.bean.AccountHolder;
import com.cg.ibs.bean.AccountType;
import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.ApplicantBean.Gender;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.exception.IBSException;
import com.cg.ibs.im.util.OracleConnection;
import com.cg.ibs.im.util.QueryMap;

public class CustomerDaoImpl implements CustomerDao {

	private static Map<BigInteger, CustomerBean> customerDao = new HashMap<BigInteger, CustomerBean>();
	public static final String UPLOADS_LOC = "./uploads";
	ApplicantBean newApplicant = new ApplicantBean();
	CustomerBean newCustomer = new CustomerBean();

	@Override
	public boolean saveCustomer(CustomerBean newCustomer) {
		if (newCustomer != null) {
			System.out.println("check if this works");
			boolean result = false;
			Connection connection = OracleConnection.callConnection();
			try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.insertCustomersDetails);) {

				System.out.println("Prepare statement linked to co");
				preparedStatement.setBigDecimal(1, new BigDecimal(newCustomer.getUci()));
				preparedStatement.setString(2, newCustomer.getUserId());
				preparedStatement.setString(3, newCustomer.getPassword());
				newApplicant = newCustomer.getApplicant();
				System.out.println("Storing approved applicant details");
				preparedStatement.setLong(4, newApplicant.getApplicantId());
				preparedStatement.setString(5, newApplicant.getFirstName());
				preparedStatement.setString(6, newApplicant.getLastName());
				preparedStatement.setString(7, newApplicant.getFatherName());
				preparedStatement.setString(8, newApplicant.getMotherName());

				LocalDate dob = newApplicant.getDob();
				java.sql.Date date = java.sql.Date.valueOf(dob);
				preparedStatement.setDate(9, date);

				preparedStatement.setString(10, newApplicant.getEmailId());
				preparedStatement.setString(11, newApplicant.getGender().toString());
				preparedStatement.setString(12, newApplicant.getMobileNumber());
				preparedStatement.setString(13, newApplicant.getAlternateMobileNumber());
				preparedStatement.setString(14, newApplicant.getAadharNumber());
				preparedStatement.setString(15, newApplicant.getPanNumber());
				System.out.println("Specified the account type");
				preparedStatement.setString(16, newApplicant.getAccountType().toString());
				preparedStatement.setString(17, newApplicant.getAccountHolder().toString());
				preparedStatement.setInt(18, 0);
				System.out.println("everythintg is saved!!!!!!!!!");
				
				int check = preparedStatement.executeUpdate();
				if (check > 0) {
					result = true;
				}
				return result;
			} catch (SQLException exception) {
				System.out.println(exception.getMessage());
			}

		}
		return false;

	}

	@Override
	public CustomerBean getCustomerDetails(String uci) {   //why cusomer???check!!
		CustomerBean newCustomer2 = new CustomerBean();
		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.getCustomerDetails);) {
			preparedStatement.setString(1, uci);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {

				
				while (resultSet.next()) {
					newCustomer2.setUci(new BigInteger(resultSet.getBigDecimal(1).toString()));
					newCustomer2.setUserId(resultSet.getString(2));
					newCustomer2.setPassword(resultSet.getString(3));
					newApplicant.setApplicantId(resultSet.getLong(4));
					newApplicant.setFirstName(resultSet.getString(5));
					newApplicant.setLastName(resultSet.getString(6));
					newApplicant.setFatherName(resultSet.getString(7));
					newApplicant.setMotherName(resultSet.getString(8));
					
					java.sql.Date date = resultSet.getDate(9);
					LocalDate dob = date.toLocalDate();
					newApplicant.setDob(dob);
					newApplicant.setEmailId(resultSet.getString(10));

					String gender = resultSet.getString(11);
					if (gender.toUpperCase().equals("MALE")) {
						newApplicant.setGender(Gender.MALE);
					} else if (gender.toUpperCase().equals("FEMALE")) {
						newApplicant.setGender(Gender.FEMALE);
					} else if (gender.toUpperCase().equals("OTHERS")) {
						newApplicant.setGender(Gender.OTHERS);
					}

					newApplicant.setMobileNumber(resultSet.getString(12));
					newApplicant.setAlternateMobileNumber(resultSet.getString(13));
					newApplicant.setAadharNumber(resultSet.getString(14));
					newApplicant.setPanNumber(resultSet.getString(15));

					String accountType = resultSet.getString(16);
					if (accountType.toUpperCase().equals("INDIVIDUAL")) {
						newApplicant.setAccountType(AccountType.INDIVIDUAL);
					} else if (accountType.toUpperCase().equals("JOINT")) {
						newApplicant.setAccountType(AccountType.JOINT);
					}

					String accountHolder = resultSet.getString(17);
					if (accountHolder.toUpperCase().equals("PRIMARY")) {
						newApplicant.setAccountHolder(AccountHolder.PRIMARY);
					} else if (accountHolder.toUpperCase().equals("SECONDARY")) {
						newApplicant.setAccountHolder(AccountHolder.SECONDARY);
					}
					newApplicant.setExistingCustomer(true);
					newApplicant.setApplicantStatus(ApplicantStatus.APPROVED);
					//linkedApplication??
					newCustomer2.setApplicant(newApplicant);
				}
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return newCustomer2;
	}

	@Override
	public Set<BigInteger> getAllCustomers() {
		Set<BigInteger> customerSet = new HashSet<BigInteger>();

		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement statement = connection.prepareStatement(QueryMap.getAllCustomers);) {
			try (ResultSet resultSet = statement.executeQuery();) {
				{
					int index = 1;

					while (resultSet.next()) {
						BigInteger uci = new BigInteger(resultSet.getBigDecimal("uci").toString());
						customerSet.add(uci);
						index++;

					}
				}
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return customerSet;
	}

	public CustomerBean getCustomerByApplicantId(long applicantId) {
		
		Connection connection = OracleConnection.callConnection();
		try(PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.getCustomerDetailsByApplicantId);) {
			preparedStatement.setLong(1, applicantId);
			try(ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					newCustomer.setUci(new BigInteger(resultSet.getBigDecimal(1).toString()));
					newCustomer.setUserId(resultSet.getString(2));
					newCustomer.setPassword(resultSet.getString(3));
					newApplicant.setApplicantId(resultSet.getLong(4));
					newApplicant.setFirstName(resultSet.getString(5));
					newApplicant.setLastName(resultSet.getString(6));
					newApplicant.setFatherName(resultSet.getString(7));
					newApplicant.setMotherName(resultSet.getString(8));
					
					java.sql.Date date = resultSet.getDate(9);
					LocalDate dob = date.toLocalDate();
					newApplicant.setDob(dob);
					newApplicant.setEmailId(resultSet.getString(10));

					String gender = resultSet.getString(11);
					if (gender.toUpperCase().equals("MALE")) {
						newApplicant.setGender(Gender.MALE);
					} else if (gender.toUpperCase().equals("FEMALE")) {
						newApplicant.setGender(Gender.FEMALE);
					} else if (gender.toUpperCase().equals("OTHERS")) {
						newApplicant.setGender(Gender.OTHERS);
					}

					newApplicant.setMobileNumber(resultSet.getString(12));
					newApplicant.setAlternateMobileNumber(resultSet.getString(13));
					newApplicant.setAadharNumber(resultSet.getString(14));
					newApplicant.setPanNumber(resultSet.getString(15));

					String accountType = resultSet.getString(16);
					if (accountType.toUpperCase().equals("INDIVIDUAL")) {
						newApplicant.setAccountType(AccountType.INDIVIDUAL);
					} else if (accountType.toUpperCase().equals("JOINT")) {
						newApplicant.setAccountType(AccountType.JOINT);
					}

					String accountHolder = resultSet.getString(17);
					if (accountHolder.toUpperCase().equals("PRIMARY")) {
						newApplicant.setAccountHolder(AccountHolder.PRIMARY);
					} else if (accountHolder.toUpperCase().equals("SECONDARY")) {
						newApplicant.setAccountHolder(AccountHolder.SECONDARY);
					}
					
					newApplicant.setExistingCustomer(true);
					newApplicant.setApplicantStatus(ApplicantStatus.APPROVED);
					//linkedApplication??
					newCustomer.setApplicant(newApplicant);
				}
			}
		} catch(Exception exception) {
			System.out.println(exception.getMessage());
		}
		return newCustomer;
	}

	@Override
	public boolean copy(String srcPath, String destPath) {
		boolean isDone = false;
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);

		if (srcFile.exists()) {
			try (FileInputStream fin = new FileInputStream(srcFile);
					FileOutputStream fout = new FileOutputStream(destFile)) {
				byte[] data = new byte[1024];
				while (fin.read(data) > -1) {
					fout.write(data);
				}
				isDone = true;
			} catch (IOException exception) {
				// raise a user defiend exception
			}
		} else {
			// throw your exception
		}
		return isDone;
	}
}