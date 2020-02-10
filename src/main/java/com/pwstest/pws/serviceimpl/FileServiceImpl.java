package com.pwstest.pws.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pwstest.pws.entity.User;
import com.pwstest.pws.exception.BankOnUsException;
import com.pwstest.pws.repository.UserRepository;
import com.pwstest.pws.service.FileService;
/**
 * @author Sudhakar.k
 *
 */
@Service
public class FileServiceImpl implements FileService {

	@Value("${app.upload.dir}")
	public String uploadDir;

	@Autowired
	UserRepository userRepo;

	@Transactional
	public String uploadFile(MultipartFile file) throws BankOnUsException, IOException {

		
			Path copyLocation = Paths
					.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			int count = 0;
			try {

				br = new BufferedReader(
						new FileReader(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename())));
				while ((line = br.readLine()) != null) {
					count++;
					// use comma as separator
					String[] userDetails = line.split(cvsSplitBy);
					User user = new User();
					if (userDetails[0].length() > 0 && userDetails[0].length() <= 10 && isNum(userDetails[0], 0)) {
						user.setUserId(Integer.parseInt(userDetails[0]));
					} else {
						throw new BankOnUsException("User ID Invalid at Line " + count + " and User ID is  " + userDetails[0]);
					}

					if (isNum(userDetails[1], 1)) {
						user.setUserCoins(Integer.parseInt(userDetails[1]));
					} else {
						throw new BankOnUsException("User coins  Invalid at Line " + count + " User Coins is " + userDetails[1]
								+ " is not a whole number");
					}

					user.setName(userDetails[2]);

					userRepo.save(user);

				}
				return "You successfully uploaded " + file.getOriginalFilename() + "!";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BankOnUsException("Could not store file . Please try again!");
//		}
		return "You successfully uploaded " + file.getOriginalFilename() + "!";
	}

	private boolean isNum(String str, int whole) {
		boolean numeric = true;
		try {
			Integer num = Integer.parseInt(str);
			if (whole == 1 && num <= 0) {
				numeric = false;
			}
		} catch (NumberFormatException e) {
			numeric = false;
		}
		return numeric;
	}
}
