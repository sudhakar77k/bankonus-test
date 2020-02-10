package com.pwstest.pws.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwstest.pws.entity.UserPhone;
import com.pwstest.pws.exception.BankOnUsException;
import com.pwstest.pws.repository.UserPhoneRepository;
import com.pwstest.pws.repository.UserRepository;
import com.pwstest.pws.service.UserPhoneService;

/**
 * @author Sudhakar.k
 *
 */
@Service
public class UserPhoneServiceImpl implements UserPhoneService {

	@Autowired
	UserPhoneRepository userPhoneRepository;

	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<String> getAllPhoneNumber() {
		// TODO Auto-generated method stub
		return userPhoneRepository.findAllPhone();
	}

	@Override
	public List<String> getAllPhoneByUserId(Integer id) throws BankOnUsException{
		// TODO Auto-generated method stub
		if(!userRepository.findById(id).isPresent()) {
			throw new BankOnUsException("User not valid");
		}
		
		return userPhoneRepository.findAllByUserId(id);
	}

	@Override
	public boolean activateNumber(String phoneNumber) throws BankOnUsException {
		// TODO Auto-generated method stub
		Optional<UserPhone> upOpt = userPhoneRepository.getUniqPhone(phoneNumber);
		if (upOpt.isPresent()) {
			upOpt.get().setIsActive(true);
			userPhoneRepository.save(upOpt.get());
			return true;
		} else {
			throw new BankOnUsException("Either phonenumber activated / Phonenumber not found");
		}
	}

}
