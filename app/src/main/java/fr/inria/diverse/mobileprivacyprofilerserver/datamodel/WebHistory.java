/*  */
package fr.inria.diverse.mobileprivacyprofilerserver.datamodel;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.inria.diverse.mobileprivacyprofilerserver.datamodel.associations.DetectedWifi_AccessPoint;
// Start of user code additional import for WebHistory
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "webHistory")
public class WebHistory {

	public static Log log = LogFactory.getLog(WebHistory.class);

	public static final String XML_WEBHISTORY = "WEBHISTORY";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_URL = "url";
	public static final String XML_ATT_DATE = "date";
	public static final String XML_ATT_APPLICATION = "application";
	// id is generated by the database and set on the object automagically
	@DatabaseField(generatedId = true)
	protected int _id;

	/**
     * dbHelper used to autorefresh values and doing queries
     * must be set other wise most getter will return proxy that will need to be refreshed
	 */
	protected MobilePrivacyProfilerDBHelper _contextDB = null;

	/**
	 * object created from DB may need to be updated from the DB for being fully navigable
	 */
	

	@DatabaseField
	protected String url;

	@DatabaseField
	protected String date;

	/** application that requested the url */ 
	@DatabaseField
	protected String application;
	

	// Start of user code WebHistory additional user properties
	// End of user code
	
	public WebHistory() {} // needed by ormlite
	public WebHistory(String url, String date, String application) {
		super();
		this.url = url;
		this.date = date;
		this.application = application;
	} 

	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}

	public MobilePrivacyProfilerDBHelper getContextDB(){
		return _contextDB;
	}
	public void setContextDB(MobilePrivacyProfilerDBHelper contextDB){
		this._contextDB = contextDB;
	}

	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDate() {
		return this.date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getApplication() {
		return this.application;
	}
	public void setApplication(String application) {
		this.application = application;
	}




	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_WEBHISTORY);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_URL);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.url));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DATE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.date));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_APPLICATION);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.application));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_WEBHISTORY+">");
		return sb.toString();
	}
}
