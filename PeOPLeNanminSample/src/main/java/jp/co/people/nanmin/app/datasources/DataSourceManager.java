package jp.co.people.nanmin.app.datasources;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Class managing the data source
 */
@Component
public class DataSourceManager {
	// hashmap for getting data source from DB name
	private HashMap<String, DataSource> dataSourceFromName;

	/**
	 * Constructor * @ param core core DB data source @ param health health DB data
	 * source @ param personal Personal DB data source
	 */
	public DataSourceManager(@Qualifier("core") DataSource core, @Qualifier("health") DataSource health,
			@Qualifier("personal") DataSource personal) {
		this.dataSourceFromName = new HashMap<String, DataSource>();
		this.dataSourceFromName.put("core", core);
		this.dataSourceFromName.put("health", health);
		this.dataSourceFromName.put("personal", personal);
	}

	/**
	 * NamedParameterJdbcTemplate return
	 * 
	 * @param dbName
	 *            DB Name
	 * @return Corresponding NamedParameterJdbcTemplate. If the corresponding DB
	 *         does not exist, null is returned.
	 */
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(String dbName) {
		return new NamedParameterJdbcTemplate(dataSourceFromName.get(dbName));
	}
}
