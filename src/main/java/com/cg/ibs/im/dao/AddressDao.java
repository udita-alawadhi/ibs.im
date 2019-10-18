package com.cg.ibs.im.dao;

import com.cg.ibs.bean.AddressBean;

public interface AddressDao {

	boolean savePermanentAddress(long applicantId, AddressBean address);

	boolean saveCurrentAddress(long applicantId, AddressBean address);

	AddressBean getPermanentAddress(long applicantId);

	AddressBean getCurrentAddress(long applicantId);

}
