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

// Start of user code additional import for DetectedWifi
// End of user code

/** 
  * log entry for a given detected wifi 
  */ 
@DatabaseTable(tableName = "detectedWifi")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = DetectedWifi.class)
public class DetectedWifi implements DbClass{

	public static Log log = LogFactory.getLog(DetectedWifi.class);

	public static final String XML_DETECTEDWIFI = "DETECTEDWIFI";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_STARTDETECTIONDATE = "startDetectionDate";
	public static final String XML_ATT_ENDDETECTIONDATE = "endDetectionDate";
	public static final String XML_ATT_HASCONNECTED = "hasConnected";
	public static final String XML_ATT_SSID = "ssid";
	public static final String XML_ATT_USERID = "userId";
	public static final String XML_REF_KNOWNWIFI = "knownWifi";
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
	public boolean knownWifi_mayNeedDBRefresh = true;
	

	/** approximate date where the WifiAccessPoint has been detected */ 
	@DatabaseField
	protected java.lang.String startDetectionDate;

	/** if reached, 
approximate time where the WifiAccessPoint was not received anymore */ 
	@DatabaseField
	protected java.lang.String endDetectionDate;

	/** indicates wether the device succefuly connect to the wifi during this session */ 
	@DatabaseField
	protected int hasConnected;

	/** SSID detected during the session
(note: should we need to check what happen if several network conflicts by using the same SSID / man in the middle attack ) */ 
	@DatabaseField
	protected java.lang.String ssid;

	@DatabaseField
	protected java.lang.String userId;


	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="knownwifi_detectedwifi")
	protected KnownWifi knownWifi;

	// Start of user code DetectedWifi additional user properties
	// End of user code
	
	public DetectedWifi() {} // needed by ormlite
	public DetectedWifi(java.lang.String startDetectionDate, java.lang.String endDetectionDate, int hasConnected, java.lang.String ssid, java.lang.String userId) {
		super();
		this.startDetectionDate = startDetectionDate;
		this.endDetectionDate = endDetectionDate;
		this.hasConnected = hasConnected;
		this.ssid = ssid;
		this.userId = userId;
	}

	public int get_id() {
		return _id;
	}
	@JsonProperty
	public void set_id(int id) {
		this._id = id;
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

	public java.lang.String getStartDetectionDate() {
		return this.startDetectionDate;
	}
	@JsonProperty
	public void setStartDetectionDate(java.lang.String startDetectionDate) {
		this.startDetectionDate = startDetectionDate;
	}
	public java.lang.String getEndDetectionDate() {
		return this.endDetectionDate;
	}
	@JsonProperty
	public void setEndDetectionDate(java.lang.String endDetectionDate) {
		this.endDetectionDate = endDetectionDate;
	}
	public int getHasConnected() {
		return this.hasConnected;
	}
	@JsonProperty
	public void setHasConnected(int hasConnected) {
		this.hasConnected = hasConnected;
	}
	public java.lang.String getSsid() {
		return this.ssid;
	}
	@JsonProperty
	public void setSsid(java.lang.String ssid) {
		this.ssid = ssid;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	@JsonProperty
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public KnownWifi getKnownWifi() {
		try {
			if(knownWifi_mayNeedDBRefresh && _contextDB != null){
				_contextDB.knownWifiDao.refresh(this.knownWifi);
				knownWifi_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.knownWifi == null){
			log.warn("DetectedWifi may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.knownWifi;
	}
	@JsonProperty
	public void setKnownWifi(KnownWifi knownWifi) {
		this.knownWifi = knownWifi;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_DETECTEDWIFI);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_STARTDETECTIONDATE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.startDetectionDate));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_ENDDETECTIONDATE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.endDetectionDate));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_HASCONNECTED);
    	sb.append("=\"");
		sb.append(this.hasConnected);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_SSID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.ssid));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		if(this.knownWifi!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_KNOWNWIFI+">");
			sb.append(this.knownWifi.getId());
	    	sb.append("</"+XML_REF_KNOWNWIFI+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_DETECTEDWIFI+">");
		return sb.toString();
	}
}
