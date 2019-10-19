package com.cg.ibs.im.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.crypto.IllegalBlockSizeException;

import com.cg.ibs.bean.AccountHolder;
import com.cg.ibs.bean.AccountType;
import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.ApplicantBean.Gender;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.exception.IBSException;
import com.cg.ibs.im.util.OracleConnection;
import com.cg.ibs.im.util.QueryMap;

import oracle.net.aso.s;

public class ApplicantDaoImpl implements ApplicantDao {

	private ApplicantBean newApplicant = new ApplicantBean();

	@Override
	public boolean saveApplicant(ApplicantBean applicant) throws IBSCustomException {
		boolean result = false;
		if (applicant != null) {
			Connection connection = OracleConnection.callConnection();
			try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.INSERT_APPLICANT_DETAILS);) {

				preparedStatement.setLong(1, applicant.getApplicantId());

				preparedStatement.setString(2, applicant.getFirstName());
				preparedStatement.setString(3, applicant.getLastName());
				preparedStatement.setString(4, applicant.getFatherName());
				preparedStatement.setString(5, applicant.getMotherName());
				LocalDate dob = applicant.getDob();
				java.sql.Date date = java.sql.Date.valueOf(dob);
				preparedStatement.setDate(6, date);
				preparedStatement.setString(7, applicant.getGender().toString());
				preparedStatement.setString(8, applicant.getMobileNumber());
				preparedStatement.setString(9, applicant.getAlternateMobileNumber());
				preparedStatement.setString(10, applicant.getEmailId());
				preparedStatement.setString(11, applicant.getAadharNumber());
				preparedStatement.setString(12, applicant.getPanNumber());
				preparedStatement.setString(13, applicant.getAccountType().toString());
				preparedStatement.setString(14, applicant.getAccountHolder().toString());
				preparedStatement.setString(15, applicant.getApplicantStatus().toString());
				LocalDate applicationDate = applicant.getApplicationDate();
				java.sql.Date dateOfApplication = java.sql.Date.valueOf(applicationDate);
				preparedStatement.setDate(16, dateOfApplication);

				preparedStatement.setLong(17, applicant.getLinkedApplication());
//				preparedStatement.setBoolean(18, applicant.is);

				System.out.println("cjhelc!!!!");
				int check = preparedStatement.executeUpdate();
				if (check > 0) {
					result = true;

				}
			} catch (SQLException exception) {
				exception.printStackTrace();
				throw new IBSCustomException(IBSException.SQLError);
			}
		}

		return result;
	}

	@Override
	public boolean updateStatusToApproved(ApplicantBean applicant) throws IBSCustomException {
		boolean result = false;
		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.UPDATE_APPLICANT_STATUS);) {
			preparedStatement.setLong(1, applicant.getApplicantId());

			int check = preparedStatement.executeUpdate();
			if (check == 1) {
				result = true;
			}
		} catch (SQLException exception) {
			throw new IBSCustomException(IBSException.SQLError);
		}
		return result;
	}

	@Override
	public boolean updateLinkedApplication(ApplicantBean applicant) throws IBSCustomException{
		boolean result = false;
		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.UPDATE_LINKED_APPLICATION);) {
			preparedStatement.setLong(1, applicant.getApplicantId());

			int check = preparedStatement.executeUpdate();
			if (check == 1) {
				result = true;
			}
		} catch (SQLException exception) {
			throw new IBSCustomException(IBSException.SQLError);
		}
		return result;
	}

	@Override
	public Set<Long> getAllApplicants() throws IBSCustomException{
		Set<Long> applicantSet = new HashSet<Long>();

		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement statement = connection.prepareStatement(QueryMap.GET_ALL_APPLICATIONS);) {
			try (ResultSet resultSet = statement.executeQuery();) {
				{
					int index = 1;

					while (resultSet.next()) {
						applicantSet.add(resultSet.getLong(index));
						index++;

					}
				}
			}
		} catch (SQLException exception) {
			throw new IBSCustomException(IBSException.SQLError);
		}
		return applicantSet;
	}

	@Override
	public boolean isApplicantPresent(long applicantId) throws IBSCustomException{
		boolean result = false;

		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement statement = connection.prepareStatement(QueryMap.GET_APPLICANT_DETAILS);) {
			statement.setLong(1, applicantId);
			int updateCheck = statement.executeUpdate();
			try (ResultSet resultSet = statement.executeQuery();) {
				if (updateCheck == 1) {
					result = true;
				}
			}

		} catch (SQLException exception) {
			throw new IBSCustomException(IBSException.SQLError);
		}
		return result;
	}

	@Override
	public ApplicantBean getApplicantDetails(long applicantId) throws IBSCustomException{
		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement statement = connection.prepareStatement(QueryMap.GET_APPLICANT_DETAILS);) {
			statement.setLong(1, applicantId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					newApplicant.setApplicantId(applicantId);
					newApplicant.setFirstName(resultSet.getString(2));
					newApplicant.setLastName(resultSet.getString(3));
					newApplicant.setFatherName(resultSet.getString(4));
					newApplicant.setMotherName(resultSet.getString(5));
					java.sql.Date date = resultSet.getDate(6);
					LocalDate dob = date.toLocalDate();
					newApplicant.setDob(dob);

					String gender = resultSet.getString(7);
					if (gender.toUpperCase().equals("MALE")) {
						newApplicant.setGender(Gender.MALE);
					} else if (gender.toUpperCase().equals("FEMALE")) {
						newApplicant.setGender(Gender.FEMALE);
					} else if (gender.toUpperCase().equals("OTHERS")) {
						newApplicant.setGender(Gender.OTHERS);
					}
					newApplicant.setMobileNumber(resultSet.getString(8));
					newApplicant.setAlternateMobileNumber(resultSet.getString(9));
					newApplicant.setEmailId(resultSet.getString(10));
					newApplicant.setAadharNumber(resultSet.getString(11));
					newApplicant.setPanNumber(resultSet.getString(12));

					String accountType = resultSet.getString(13);
					if (accountType.toUpperCase().equals("JOINT")) {
						newApplicant.setAccountType(AccountType.JOINT);
					} else if (accountType.toUpperCase().equals("INDIVIDUAL")) {
						newApplicant.setAccountType(AccountType.INDIVIDUAL);
					}

					String accountHolder = resultSet.getString(14);
					if (accountHolder.toUpperCase().equals("PRIMARY")) {
						newApplicant.setAccountHolder(AccountHolder.PRIMARY);
					} else if (accountHolder.toUpperCase().equals("SECONDARY")) {
						newApplicant.setAccountHolder(AccountHolder.SECONDARY);
					}

					String applicantStatus = resultSet.getString(15);
					if (applicantStatus.toUpperCase().equals("PENDING")) {
						newApplicant.setApplicantStatus(ApplicantStatus.PENDING);
					} else if (applicantStatus.toUpperCase().equals("APPROVED")) {
						newApplicant.setApplicantStatus(ApplicantStatus.APPROVED);
					} else if (applicantStatus.toUpperCase().equals("DENIED")) {
						newApplicant.setApplicantStatus(ApplicantStatus.DENIED);
					}

					java.sql.Date applicationDate = resultSet.getDate(16);
					LocalDate dateOfApplication = applicationDate.toLocalDate();
					newApplicant.setApplicationDate(dateOfApplication);

					newApplicant.setLinkedApplication(resultSet.getLong(17));
					newApplicant.setExistingCustomer(resultSet.getBoolean(18));
				}
			}
		} catch (SQLException exception) {
			throw new IBSCustomException(IBSException.SQLError);
		}
		return newApplicant;
	}

	@Override
	public Set<Long> getApplicantsByStatus(ApplicantStatus applicantStatus) throws IBSCustomException{
		Set<Long> applicantSet = new HashSet<Long>();
		Connection connection = OracleConnection.callConnection();

		try (PreparedStatement statement = connection.prepareStatement(QueryMap.GET_ALL_APPLICATIONS_BY_STATUS);) {
			statement.setString(1, applicantStatus.toString().toUpperCase());
			try (ResultSet resultSet = statement.executeQuery();) {

				while (resultSet.next()) {
					long temp = resultSet.getLong("applicant_id");
					applicantSet.add(temp);
				}
			}
		} catch (SQLException exception) {
			throw new IBSCustomException(IBSException.SQLError);
		}

		return applicantSet;
	}

	@Override
	public boolean validateBankLogin(String userId, String password) throws IBSCustomException{
		boolean status = false;
		Connection connection = OracleConnection.callConnection();
		if (checkIfBankerExists(userId)) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.BANKER_DETAILS);) {
				preparedStatement.setString(1, userId);
				try (ResultSet resultSet = preparedStatement.executeQuery();) {
					if (resultSet.next()) {
						String tempPassword = resultSet.getString("PASSWORD");
						if (password.equals(tempPassword)) {
							status = true;
						}
					}
				}
			} catch (SQLException exception) {
				throw new IBSCustomException(IBSException.SQLError);
			}
		}

		return status;
	}

	public boolean checkIfBankerExists(String userId) throws IBSCustomException{
		boolean status = false;
		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.BANKER_DETAILS);) {
			preparedStatement.setString(1, userId);

			int index = preparedStatement.executeUpdate();
			if (index == 1) {
				status = true;
			}
		} catch (SQLException exception) {
			throw new IBSCustomException(IBSException.SQLError);
		}
		return status;
	}

}
