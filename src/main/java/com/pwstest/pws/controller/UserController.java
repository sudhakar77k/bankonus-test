package com.pwstest.pws.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pwstest.pws.ApiSuccess;
import com.pwstest.pws.entity.TemperatureConversion;
import com.pwstest.pws.entity.UserPhone;
import com.pwstest.pws.exception.BankOnUsException;
import com.pwstest.pws.service.ConvertTempratureService;
import com.pwstest.pws.service.FileService;
import com.pwstest.pws.service.UserPhoneService;
import com.pwstest.pws.utility.CommonUtils;

@Controller
public class UserController {

	@Autowired
	FileService fileService;

	@Autowired
	ConvertTempratureService convertTempratureService;

	@Autowired
	UserPhoneService userPhoneService;

	// inject via application.properties
	@Value("${welcome.message}")
	private String message;

	/**
	 * 
	 * @param model welcome page
	 * @return String
	 */

	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("message", message);

		return "welcome"; // view
	}

	/**
	 * 
	 * @return String view page
	 */
	@GetMapping("/upload")
	public String showUploadForm() {
		return "upload-file";
	}

	/**
	 * 
	 * @return convert temp view page
	 */
	@GetMapping("/convert")
	public String showConvertTempForm() {
		return "convert-temp";
	}

	/**
	 * Method to upload file and save to DB / throw msg if failed
	 * 
	 * @param file
	 * @param model
	 * @return String
	 * @throws BankOnUsException
	 * @Transactional
	 * @PostMapping
	 */
	@Transactional
	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, Model model)
			throws BankOnUsException, IOException {
		String response = null;

		response = fileService.uploadFile(file);

		model.addAttribute("message", response);

		return "upload-file";
	}

	/**
	 * 
	 * method to convert Temprature using third party API WS
	 * 
	 * @param type
	 * @param value
	 * @param redirectAttributes
	 * @return String
	 * @throws BankOnUsException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@PostMapping("/convertTemprature")
	public String convertTemprature(@RequestParam("type") String type, @RequestParam("value") Double value,
			RedirectAttributes redirectAttributes) throws BankOnUsException, InterruptedException, ExecutionException {

		CompletableFuture<TemperatureConversion> temperatureConversion = convertTempratureService
				.convertTemprature(type, value);
		CompletableFuture.allOf(temperatureConversion).join();
		redirectAttributes.addFlashAttribute("temperatureConversion", temperatureConversion.get());

		return "redirect:/convert";
	}

	/**
	 * 
	 * API to get all Phone numbers
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping("/phone")
	public ResponseEntity<Object> getConstantMasterList() {

		List<String> userPhone = userPhoneService.getAllPhoneNumber();
		return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, userPhone));
	}

	/**
	 * 
	 * API to get all Phone numbers by user id
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping("/phone/UserId/{id}")
	public ResponseEntity<Object> getPhoneByUserId(@PathVariable Integer id)throws BankOnUsException {

		List<String> userPhone = userPhoneService.getAllPhoneByUserId(id);
		return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, userPhone));
	}

	/**
	 * 
	 * API to activate a given Phone numbers
	 * 
	 * @return ResponseEntity
	 */
	@PostMapping("/phone/activate")
	public ResponseEntity<Object> activatePhone(@RequestBody UserPhone userPhone) throws BankOnUsException {

		boolean activated = userPhoneService.activateNumber(userPhone.getPhoneNumber());
		return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, activated));
	}

	/**
	 * 
	 * Exception handler for custom exception
	 */
	@ExceptionHandler(value = { BankOnUsException.class })
	public ModelAndView handleAllException(BankOnUsException ex, HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		model.addObject("exception", ex.toString());
		model.addObject("url", req.getRequestURL());
		model.addObject("errMsg", "We are sorry. " + "Your request cannot be done, please try again later! ");
		model.addObject("exceptionmessage", ex.getMsg());
		model.setViewName("/error");
		return model;
	}

	/**
	 * 
	 * Exception handler for custom exception
	 */
	@ExceptionHandler(value = { Exception.class })
	public ModelAndView handleException(Exception ex, HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		model.addObject("exception", ex.toString());
		model.addObject("url", req.getRequestURL());
		model.addObject("errMsg", "We are sorry. " + "Your request cannot be done, please try again later! ");
		model.addObject("exceptionmessage", ex.getMessage());
		model.setViewName("/error");
		return model;
	}

}