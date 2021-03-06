/*  */
package fr.inria.diverse.mobileprivacyprofilerserver.database.data;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Start of user code additional import for ApplicationHistory
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "applicationHistory")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = ApplicationHistory.class)
public class ApplicationHistory implements DbClass {

	public static Log log = LogFactory.getLog(ApplicationHistory.class);

	public static final String XML_APPLICATIONHISTORY = "APPLICATIONHISTORY";
	public static final String XML_ATT_ID = "_id";
	public static final String XML_ATT_ANDROID_ID = "android_id";
	public static final String XML_ATT_APPNAME = "appName";
	public static final String XML_ATT_PACKAGENAME = "packageName";
	public static final String XML_ATT_USERID = "userId";
	public static final String XML_REF_USAGESTATS = "usageStats";
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
	

	@DatabaseField
	protected java.lang.String appName;

	@DatabaseField
	protected java.lang.String packageName;

	@DatabaseField
	protected java.lang.String userId;
	

	@ForeignCollectionField(eager = false, foreignFieldName = "application")
	@JsonIgnore
	protected ForeignCollection<ApplicationUsageStats> usageStats;

	@DatabaseField
	protected int android_id;

	// Start of user code ApplicationHistory additional user properties
	// End of user code
	
	public ApplicationHistory() {} // needed by ormlite
	public ApplicationHistory(java.lang.String appName, java.lang.String packageName, java.lang.String userId) {
		super();
		this.appName = appName;
		this.packageName = packageName;
		this.userId = userId;
	} 

	/**
	 * raw_id from client converted as part of a primary key in combination of user UUID
	 */
	public int get_id() {
		return _id;
	}
	@JsonProperty
	public void set_id(int id) {
		this._id = id;
	}

	public int getAndroid_id() {
		return android_id;
	}
	@JsonProperty
	public void setAndroid_id(int id) {
		this.android_id = id;
	}

	public MobilePrivacyProfilerDBHelper getContextDB(){
		return _contextDB;
	}
	@JsonIgnore
	public void setContextDB(MobilePrivacyProfilerDBHelper contextDB){
		this._contextDB = contextDB;
	}

	public java.lang.String getAppName() {
		return this.appName;
	}
	@JsonProperty
	public void setAppName(java.lang.String appName) {
		this.appName = appName;
	}
	public java.lang.String getPackageName() {
		return this.packageName;
	}
	@JsonProperty
	public void setPackageName(java.lang.String packageName) {
		this.packageName = packageName;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	@JsonProperty
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public List	<ApplicationUsageStats> getUsageStats() {
		if(null==this.usageStats){return null;}
		return new ArrayList<ApplicationUsageStats>(usageStats);
	}
	

			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_APPLICATIONHISTORY);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
		sb.append(XML_ATT_ANDROID_ID);
		sb.append("=\"");
		sb.append(this.android_id);
		sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_APPNAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.appName));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PACKAGENAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.packageName));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		sb.append("\n"+indent+"\t<"+XML_REF_USAGESTATS+">");
		if(this.usageStats != null){
			for(ApplicationUsageStats ref : this.usageStats){
				sb.append("\n"+ref.toXML(indent+"\t\t", contextDB));
	    	}
		}
		sb.append("</"+XML_REF_USAGESTATS+">");		
		// TODO deal with other case

		sb.append("</"+XML_APPLICATIONHISTORY+">");
		return sb.toString();
	}
}
