package com.pwstest.pws.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.pwstest.pws.exception.BankOnUsException;
/**
 * @author Sudhakar.k
 *
 */
public interface FileService {

	public String uploadFile(MultipartFile file) throws BankOnUsException, IOException;
}
