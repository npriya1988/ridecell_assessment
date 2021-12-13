package com.django.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class UtilityFunctions {

	Logger log = Logger.getLogger(UtilityFunctions.class);

	/**
	 * Util fucntion for get the path for working directory
	 * 
	 * @return
	 */
	public String getRootDirectory() {
		return System.getProperty("user.dir");
	}

	/***
	 * Util function for create the folder for given path
	 * 
	 * @param file(path for create the directory)
	 * @return
	 */
	public boolean createDirectory(File file) {
		boolean isDirCreated = false;
		try {

			if (!file.exists()) {

				file.mkdir();
				isDirCreated = true;
			}
		} catch (Exception e) {
			log.error("Error creating the directory  " + e.getMessage());
		}

		return isDirCreated;

	}

	/***
	 * Util fucntion for copy the file from one place to another
	 * 
	 * @param from
	 * @param to
	 */
	public void copyFile(File from, File to) {

		try {
			FileUtils.copyFile(from, to);
		} catch (IOException e) {
			log.error("Error in coy file method  " + e.getMessage());
		}
	}

	public void processOfCpoyingReports() {
		File from = new File(getRootDirectory() + Constants.FROM_PATH);
		String fileName = Constants.TEST_REPORTS + new LocalTime().toString().replace(".", ":").replace(":", "_");

		File destination = new File(
				getRootDirectory() + Constants.REPORTS + LocalDate.now() + "\\" + fileName + ".html");

		copyFile(from, destination);
	}

	/***
	 * Create the Reports for the specific run
	 */
	public void createTestReports() {

		try {
			File file = new File(getRootDirectory() + Constants.REPORTS + LocalDate.now());

			if (!file.exists()) {
				if (createDirectory(file)) {
					processOfCpoyingReports();
				} else {
					log.error("Directory is not created or error in creating directory");
				}
			} else {
				processOfCpoyingReports();
			}

		} catch (Exception e) {
			log.error("Error creating the directory  " + e.getMessage());
		}
	}

}
