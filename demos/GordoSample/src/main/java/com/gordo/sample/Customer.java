package com.gordo.sample;

import com.gordo.propeditor.PhoneNumber;

public interface Customer {
	public String getFirstName();

	public void setFirstName(String firstName);

	public String getLastName();

	public void setLastName(String lastName);
	
	public PhoneNumber getPhoneNumber();

	public void setPhoneNumber(PhoneNumber pn);


}
