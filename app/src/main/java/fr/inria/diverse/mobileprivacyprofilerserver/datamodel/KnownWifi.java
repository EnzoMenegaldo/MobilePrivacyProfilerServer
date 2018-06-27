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

// Start of user code additional import for KnownWifi
// End of user code

/** 
  * Registered Wifi connexion on the device.
Ie. the device has registered it and was connected to it 
  */ 
@DatabaseTable(tableName = "knownWifi")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = KnownWifi.class)
public class KnownWifi implements DbClass {

	public static Log log = LogFactory.getLog(KnownWifi.class);

	public static final String XML_KNOWNWIFI = "KNOWNWIFI";
	public static final String XML_ATT_ID = "_id";
	public static final String XML_ATT_ANDROID_ID = "android_id";
	public static final String XML_ATT_SSID = "ssid";
	public static final String XML_ATT_BSSID = "bssid";
	public static final String XML_ATT_ISCONFIGUREDWIFI = "isConfiguredWifi";
	public static final String XML_ATT_USERID = "userId";
	public static final String XML_REF_LOGSWIFI = "logsWifi";
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
	protected java.lang.String ssid;

	@DatabaseField
	protected java.lang.String bssid;

	@DatabaseField
	protected int isConfiguredWifi;

	@DatabaseField
	protected java.lang.String userId;
	

	@ForeignCollectionField(eager = false, foreignFieldName = "knownWifi")
	@JsonIgnore
	protected ForeignCollection<LogsWifi> logsWifi;

	@DatabaseField
	protected int android_id;

	// Start of user code KnownWifi additional user properties
	// End of user code
	
	public KnownWifi() {} // needed by ormlite
	public KnownWifi(java.lang.String ssid, java.lang.String bssid, int isConfiguredWifi, java.lang.String userId) {
		super();
		this.ssid = ssid;
		this.bssid = bssid;
		this.isConfiguredWifi = isConfiguredWifi;
		this.userId = userId;
	} 

	/**
	 * raw_id from client converted as part of a primary key in combination of user UUID
	 */
	public int getAndroid_id() {
		return android_id;
	}
	@JsonProperty
	public void setAndroid_id(int id) {
		this.android_id = id;
	}

	public int get_id() {
		return _id;
	}
	@JsonProperty
	public void set_id(int id) {
		this._id = id;
	}

	public MobilePrivacyProfilerDBHelper getContextDB(){
		return _contextDB;
	}
	@JsonIgnore
	public void setContextDB(MobilePrivacyProfilerDBHelper contextDB){
		this._contextDB = contextDB;
	}

	public java.lang.String getSsid() {
		return this.ssid;
	}
	@JsonProperty
	public void setSsid(java.lang.String ssid) {
		this.ssid = ssid;
	}
	public java.lang.String getBssid() {
		return this.bssid;
	}
	@JsonProperty
	public void setBssid(java.lang.String bssid) {
		this.bssid = bssid;
	}
	public int getIsConfiguredWifi() {
		return this.isConfiguredWifi;
	}
	@JsonProperty
	public void setIsConfiguredWifi(int isConfiguredWifi) {
		this.isConfiguredWifi = isConfiguredWifi;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	@JsonProperty
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public List	<LogsWifi> getLogsWifi() {
		if(null==this.logsWifi){return null;}
		return new ArrayList<LogsWifi>(logsWifi);
	}
	

			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_KNOWNWIFI);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
		sb.append(XML_ATT_ANDROID_ID);
		sb.append("=\"");
		sb.append(this._id);
		sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_SSID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.ssid));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_BSSID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.bssid));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_ISCONFIGUREDWIFI);
    	sb.append("=\"");
		sb.append(this.isConfiguredWifi);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		sb.append("\n"+indent+"\t<"+XML_REF_LOGSWIFI+">");
		if(this.logsWifi != null){
			for(LogsWifi ref : this.logsWifi){
				sb.append("\n"+ref.toXML(indent+"\t\t", contextDB));
	    	}
		}
		sb.append("</"+XML_REF_LOGSWIFI+">");		
		// TODO deal with other case

		sb.append("</"+XML_KNOWNWIFI+">");
		return sb.toString();
	}
}
