package jp.co.people.nanmin.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 *   * Configuration object related to API connection  
 */
@Component
@ConfigurationProperties(prefix = "api-config")
@Data
public class ApiConfig {
	private String servername; // server name
	private String urlPersonal; // URL of personal information resource key acquisition API
	private String urlResource; // URL of the aggregation processing API
	private String authKey; // authentication key

}