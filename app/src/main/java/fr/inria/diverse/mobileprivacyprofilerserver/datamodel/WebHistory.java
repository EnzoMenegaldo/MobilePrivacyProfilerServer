/*  */
package fr.inria.diverse.mobileprivacyprofilerserver.datamodel;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Start of user code additional import for WebHistory
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "webHistory")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = WebHistory.class)
public class WebHistory {

	public static Log log = LogFactory.getLog(WebHistory.class);

	public static final String XML_WEBHISTORY = "WEBHISTORY";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_URL = "url";
	public static final String XML_ATT_DATE = "date";
	public static final String XML_ATT_APPLICATION = "application";
	public static final String XML_REF_USERMETADATA = "userMetaData";
	// id is generated by the database and set on the object automagically
	@DatabaseField(generatedId = true)
	protected int _id;

	/**
     * dbHelper used to autorefresh values and doing queries
     * must be set other wise most getter will return proxy that will need to be refreshed
	 */
	@JsonIgnore
	protected MobilePrivacyProfilerDBHelper _contextDB = null;

	/**
	 * object created from DB may need to be updated from the DB for being fully navigable
	 */
	@JsonIgnore
	public boolean userMetaData_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected java.lang.String url;

	@DatabaseField
	protected java.lang.String date;

	/** application that requested the url */ 
	@DatabaseField
	protected java.lang.String application;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="webhistory_mobileprivacyprofilerdb_metadata")
	protected MobilePrivacyProfilerDB_metadata userMetaData;

	// Start of user code WebHistory additional user properties
	// End of user code
	
	public WebHistory() {} // needed by ormlite
	public WebHistory(java.lang.String url, java.lang.String date, java.lang.String application) {
		super();
		this.url = url;
		this.date = date;
		this.application = application;
	} 

	public int getId() {
		return _id;
	}
	@JsonProperty
	public void setId(int id) {
		this._id = id;
	}

	public MobilePrivacyProfilerDBHelper getContextDB(){
		return _contextDB;
	}
	@JsonIgnore
	public void setContextDB(MobilePrivacyProfilerDBHelper contextDB){
		this._contextDB = contextDB;
	}

	public java.lang.String getUrl() {
		return this.url;
	}
	@JsonProperty
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	public java.lang.String getDate() {
		return this.date;
	}
	@JsonProperty
	public void setDate(java.lang.String date) {
		this.date = date;
	}
	public java.lang.String getApplication() {
		return this.application;
	}
	@JsonProperty
	public void setApplication(java.lang.String application) {
		this.application = application;
	}

	public MobilePrivacyProfilerDB_metadata getUserMetaData() {
		try {
			if(userMetaData_mayNeedDBRefresh && _contextDB != null){
				_contextDB.mobilePrivacyProfilerDB_metadataDao.refresh(this.userMetaData);
				userMetaData_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userMetaData == null){
			log.warn("WebHistory may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userMetaData;
	}
	@JsonProperty
	public void setUserMetaData(MobilePrivacyProfilerDB_metadata userMetaData) {
		this.userMetaData = userMetaData;
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


		if(this.userMetaData!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERMETADATA+">");
			sb.append(this.userMetaData.getId());
	    	sb.append("</"+XML_REF_USERMETADATA+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_WEBHISTORY+">");
		return sb.toString();
	}
}
