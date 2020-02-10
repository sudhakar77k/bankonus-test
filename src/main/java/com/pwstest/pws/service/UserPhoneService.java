package com.pwstest.pws.service;

import java.util.List;

import com.pwstest.pws.exception.BankOnUsException;
/**
 * @author Sudhakar.k
 *
 */
public interface UserPhoneService {

	List<String> getAllPhoneNumber();

	List<String> getAllPhoneByUserId(Integer id) throws BankOnUsException;

	boolean activateNumber(String phoneNumber) throws BankOnUsException;

}
