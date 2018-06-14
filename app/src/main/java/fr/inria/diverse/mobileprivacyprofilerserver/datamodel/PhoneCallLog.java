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

// Start of user code additional import for PhoneCallLog
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "phoneCallLog")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = PhoneCallLog.class)
public class PhoneCallLog implements DbClass {

	public static Log log = LogFactory.getLog(PhoneCallLog.class);

	public static final String XML_PHONECALLLOG = "PHONECALLLOG";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_CLIENT_ID = "client_id";
	public static final String XML_ATT_PHONENUMBER = "phoneNumber";
	public static final String XML_ATT_DATE = "date";
	public static final String XML_ATT_DURATION = "duration";
	public static final String XML_ATT_CALLTYPE = "callType";
	public static final String XML_ATT_USERID = "userId";
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
	

	/** phone number or unknown */ 
	@DatabaseField
	protected java.lang.String phoneNumber;

	/**  */ 
	@DatabaseField
	protected java.util.Date date;

	/** duration of the call in s */ 
	@DatabaseField
	protected long duration;

	/** missed, rejected, outgoing, incoming, blocked, voicemail */ 
	@DatabaseField
	protected java.lang.String callType;

	@DatabaseField
	protected java.lang.String userId;
	

	@DatabaseField
	protected int client_id;

	// Start of user code PhoneCallLog additional user properties
	// End of user code
	
	public PhoneCallLog() {} // needed by ormlite
	public PhoneCallLog(java.lang.String phoneNumber, java.util.Date date, long duration, java.lang.String callType, java.lang.String userId) {
		super();
		this.phoneNumber = phoneNumber;
		this.date = date;
		this.duration = duration;
		this.callType = callType;
		this.userId = userId;
	} 

	/**
	 * raw_id from client converted as part of a primary key in combination of user UUID
	 */
	public int get_id() {
		return client_id;
	}
	@JsonProperty
	public void set_id(int id) {
		this.client_id = id;
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

	public java.lang.String getPhoneNumber() {
		return this.phoneNumber;
	}
	@JsonProperty
	public void setPhoneNumber(java.lang.String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public java.util.Date getDate() {
		return this.date;
	}
	@JsonProperty
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public long getDuration() {
		return this.duration;
	}
	@JsonProperty
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public java.lang.String getCallType() {
		return this.callType;
	}
	@JsonProperty
	public void setCallType(java.lang.String callType) {
		this.callType = callType;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	@JsonProperty
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}




	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_PHONECALLLOG);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
		sb.append(XML_ATT_CLIENT_ID);
		sb.append("=\"");
		sb.append(this.client_id);
		sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PHONENUMBER);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.phoneNumber));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DATE);
    	sb.append("=\"");
		sb.append(this.date);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DURATION);
    	sb.append("=\"");
		sb.append(this.duration);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_CALLTYPE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.callType));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_PHONECALLLOG+">");
		return sb.toString();
	}
}
