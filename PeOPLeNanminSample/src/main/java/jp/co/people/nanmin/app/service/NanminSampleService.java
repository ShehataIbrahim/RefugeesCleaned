package jp.co.people.nanmin.app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.people.nanmin.app.service.model.ResultSearch;
import jp.co.people.nanmin.app.service.model.apiEntity.EntShuyakuRes;
import jp.co.people.nanmin.app.service.model.form.SampleForm;
import jp.co.people.nanmin.app.service.nanminSample.FindPatientKey;
import jp.co.people.nanmin.app.service.nanminSample.FindNanminInfo;
import jp.co.people.nanmin.app.service.nanminSample.Aggregator;
import jp.co.people.nanmin.app.service.nanminSample.Accumulator;

/**
 * Sample screen service
 */
@Service
public class NanminSampleService {
	/** Personal information resource key acquisition */
	@Autowired
	private FindPatientKey findKojinKey;
	/** Aggregation processing */
	@Autowired
	private Aggregator shuyaku;
	/** Accumulation processing */
	@Autowired
	private Accumulator accumulator;
	/** Refugee Information Acquisition Process */
	@Autowired
	private FindNanminInfo findNanminInfo;

	private Logger logger = LogManager.getLogger();

	/**
	 * Acquire personal information resource key from patient ID (refugee ID) and
	 * aggregate it to DB @ param form Information from screen form (for one person)
	 * 
	 * @return Messages to be displayed on the screen (execution result)
	 */
	public String addService(SampleForm form) {

		// Variable initialization
		EntShuyakuRes resrcRes = new EntShuyakuRes(); // Aggregate API response
		String msg = "Error";
		String personalKey = ""; // Personal Information Resource Key

		try {
			// Retrieve personal information resource key
			personalKey = findKojinKey.callGetPatientKey(form.getPatient_id());
			// Aggregation processing
			resrcRes = shuyaku.callConsolidate(personalKey, form);
			// Success when the message ID is null character
			if (resrcRes.getMessage_id().equals("")) {
				msg = "Success";
			} else {
				// If something is in it, processing has failed
				msg = "Error";
			}
			// Accumulation processing
			accumulator.storeData();

		} catch (Exception e) {
			logger.fatal("An error occurred during processing{}", e);
		}
		return msg;
	}

	/**
	 * Acquire personal information from patient ID (refugee ID) @ param form
	 * Information from screen form (for one person)
	 * 
	 * @return Messages to be displayed on the screen (execution result)
	 */
	public ResultSearch findService(SampleForm form) {
		// Variable initialization
		ResultSearch result = new ResultSearch(); // Acquisition processing result objectト
		String personalKey = ""; // Personal Information Resource Key

		// First set key date, patient ID (refugee ID), message (processing failure)
		result.setIfa_date(form.getIfa_date());
		result.setPatient_id(form.getPatient_id());
		result.setMsg("Error");

		try {
			// Personal Information Resource Key find
			personalKey = findKojinKey.callGetPatientKey(form.getPatient_id());
			// If there is no Personal Information Resource Key
			if (personalKey.equals("")) {
				// do nothing
			} else {
				// Obtain refugee information (one person) from Personal Information Resource
				// Key
				result = findNanminInfo.find(form.getIfa_date(), personalKey, result);
			}
		} catch (Exception e) {
			logger.fatal("An error occurred during processing{}", e);
		}
		return result;
	}
}
