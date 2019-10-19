package com.cg.ibs.im.dao;

import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.im.exception.IBSCustomException;

public interface AddressDao {

	boolean savePermanentAddress(long applicantId, AddressBean address) throws IBSCustomException;

	boolean saveCurrentAddress(long applicantId, AddressBean address) throws IBSCustomException;

	AddressBean getPermanentAddress(long applicantId) throws IBSCustomException;

	AddressBean getCurrentAddress(long applicantId) throws IBSCustomException;

}
