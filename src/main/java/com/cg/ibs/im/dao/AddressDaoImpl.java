package com.cg.ibs.im.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.im.util.OracleConnection;
import com.cg.ibs.im.util.QueryMap;

public class AddressDaoImpl implements AddressDao {

	@Override
	public boolean savePermanentAddress(long applicantId, AddressBean address) {
		boolean result = false;
		if (address != null) {
			Connection connection = OracleConnection.callConnection();
			try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.savePermanentAddress);) {

				preparedStatement.setLong(1, applicantId);

				preparedStatement.setString(2, address.getHouseNumber());
				preparedStatement.setString(3, address.getStreetName());
				preparedStatement.setString(4, address.getArea());
				preparedStatement.setString(5, address.getLandmark());
				preparedStatement.setString(6, address.getCity());
				preparedStatement.setString(7, address.getState());
				preparedStatement.setString(8, address.getCountry());
				preparedStatement.setString(9, address.getPincode());

				int check = preparedStatement.executeUpdate();
				if (check > 0) {
					result = true;
				}
			} catch (Exception exception) {
				System.out.println(exception.getMessage());
				// handle properly
			}
		}
		return result;
	}

	@Override
	public boolean saveCurrentAddress(long applicantId, AddressBean address) {
		boolean result = false;
		if (address != null) {
			Connection connection = OracleConnection.callConnection();
			try (PreparedStatement preparedStatement = connection.prepareStatement(QueryMap.saveCurrentAddress);) {

				preparedStatement.setLong(1, applicantId);
				preparedStatement.setString(2, address.getHouseNumber());
				preparedStatement.setString(3, address.getStreetName());
				preparedStatement.setString(4, address.getArea());
				preparedStatement.setString(5, address.getLandmark());
				preparedStatement.setString(6, address.getCity());
				preparedStatement.setString(7, address.getState());
				preparedStatement.setString(8, address.getCountry());
				preparedStatement.setString(9, address.getPincode());

				int check = preparedStatement.executeUpdate();
				if (check > 0) {
					result = true;
				}
			} catch (Exception exception) {
				System.out.println(exception.getMessage());
				// handle properly
			}
		}
		return result;
	}

	@Override
	public AddressBean getPermanentAddress(long applicantId) {
		AddressBean addressBean = new AddressBean();
		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement statement = connection.prepareStatement(QueryMap.getPermanentAddress);) {
			statement.setLong(1, applicantId);
			try (ResultSet resultSet = statement.executeQuery();) {
				while(resultSet.next()) {
					addressBean.setHouseNumber(resultSet.getString(2));
					addressBean.setStreetName(resultSet.getString(3));
					addressBean.setArea(resultSet.getString(4));
					addressBean.setLandmark(resultSet.getString(5));
					addressBean.setCity(resultSet.getString(6));
					addressBean.setState(resultSet.getString(7));
					addressBean.setCountry(resultSet.getString(8));
					addressBean.setPincode(resultSet.getString(9));
				}
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return addressBean;
	}

	@Override
	public AddressBean getCurrentAddress(long applicantId) {
		AddressBean addressBean1 = new AddressBean();
		Connection connection = OracleConnection.callConnection();
		try (PreparedStatement statement = connection.prepareStatement(QueryMap.getCurrentAddress);) {
			statement.setLong(1, applicantId);
			try (ResultSet resultSet = statement.executeQuery();) {
				while(resultSet.next()) {
					addressBean1.setHouseNumber(resultSet.getString(2));
					addressBean1.setStreetName(resultSet.getString(3));
					addressBean1.setArea(resultSet.getString(4));
					addressBean1.setLandmark(resultSet.getString(5));
					addressBean1.setCity(resultSet.getString(6));
					addressBean1.setState(resultSet.getString(7));
					addressBean1.setCountry(resultSet.getString(8));
					addressBean1.setPincode(resultSet.getString(9));
				}
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return addressBean1;
	}

}
