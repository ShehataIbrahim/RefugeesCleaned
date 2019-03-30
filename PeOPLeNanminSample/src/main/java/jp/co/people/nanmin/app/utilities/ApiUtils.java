package jp.co.people.nanmin.app.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.people.nanmin.app.config.ApiConfig;

/**
* Objects related to API calls
 */
@Component
public class ApiUtils {

/** Instance related to API setting */
	@Autowired
	ApiConfig apiConfig;

	private Logger logger = LogManager.getLogger();
	private String resJsonString = ""; 	// return value from API
	
	/**
	 * return value from API
	 * @return return value from API
	 */
	public String getResJsonString() {
		return resJsonString;
	}

	/**
	 * @param url API URL
	 * @param reqParam Request parameters of JSON type
	 * @return API processing result
	 * @throws Exception
	 */
	public int callAPI(String strUrl, String reqParam) throws Exception {

		HttpURLConnection urlCon = null;
		try {
			// Clear the return value from the API being held
			resJsonString = ""; 
			// Set the connection URL
			URL url = null;
			url = new URL(apiConfig.getServername() + strUrl);
			// Obtain a connection to a URL
			urlCon = (HttpURLConnection) url.openConnection();

			// Perform connection setup
			// Set connection timeout.
			urlCon.setConnectTimeout(0);
			// Set the response data reading timeout.
			urlCon.setReadTimeout(0);
			// Set Content-Type in header
			urlCon.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
			// Set HTTP method to POST.
			urlCon.setRequestMethod("POST");
			// Allow request body to be sent
			urlCon.setDoOutput(true);
			//Allow response body reception
			urlCon.setDoInput(true);

			//Open a connection
			urlCon.connect();
			// Write out the request body.
			PrintStream ps = new PrintStream(urlCon.getOutputStream());
			ps.print(reqParam);
			ps.close();
			// Reads the response body.
			int statusCode = urlCon.getResponseCode();
			InputStream inStm = null;
			// When the status code from the API is 200
			if (statusCode == 200) {
				//normal. Using InputStream
				inStm = urlCon.getInputStream();
			} else {
				// Otherwise, it is Exception. Use ErrorStream.
				inStm = urlCon.getErrorStream();
				logger.error("An error occurred in PeOPLeCore {} {}", strUrl, statusCode);
			}
			StringBuffer sb = new StringBuffer();
			String line = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(inStm, "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			//Close input stream
			inStm.close();

			// Store the created character string
			resJsonString = sb.toString();

			if (statusCode == 200) {
				return 0;	// normal
			} else {
				// Output the return value to the log
				logger.info("Return value of PeOPLeCore{}", resJsonString);
				return -1;	// Exception
			}
		} catch (Exception e) {
			logger.fatal("An error occurred in PeOPLeCore {} {}", strUrl, e.toString());
			logger.info("Exception occurrence：{}", e);
			return -9;
		} finally {
			if (urlCon != null) {
				urlCon.disconnect();
			}
		}
	}
}