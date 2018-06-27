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

// Start of user code additional import for SMS
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "sMS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = SMS.class)
public class SMS implements DbClass {

	public static Log log = LogFactory.getLog(SMS.class);

	public static final String XML_SMS = "SMS";
	public static final String XML_ATT_ID = "_id";
	public static final String XML_ATT_ANDROID_ID = "android_id";
	public static final String XML_ATT_DATE = "date";
	public static final String XML_ATT_PHONENUMBER = "phoneNumber";
	public static final String XML_ATT_TYPE = "type";
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
	

	@DatabaseField
	protected java.lang.String date;

	@DatabaseField
	protected java.lang.String phoneNumber;

	/** incoming, outgoing */ 
	@DatabaseField
	protected java.lang.String type;

	@DatabaseField
	protected java.lang.String userId;
	

	@DatabaseField
	protected int android_id;

	// Start of user code SMS additional user properties
	// End of user code
	
	public SMS() {} // needed by ormlite
	public SMS(java.lang.String date, java.lang.String phoneNumber, java.lang.String type, java.lang.String userId) {
		super();
		this.date = date;
		this.phoneNumber = phoneNumber;
		this.type = type;
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

	public java.lang.String getDate() {
		return this.date;
	}
	@JsonProperty
	public void setDate(java.lang.String date) {
		this.date = date;
	}
	public java.lang.String getPhoneNumber() {
		return this.phoneNumber;
	}
	@JsonProperty
	public void setPhoneNumber(java.lang.String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public java.lang.String getType() {
		return this.type;
	}
	@JsonProperty
	public void setType(java.lang.String type) {
		this.type = type;
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
    	sb.append(XML_SMS);
		sb.append(" "+XML_ATT_ANDROID_ID+"=\"");
		sb.append(this.android_id);
    	sb.append("\" ");
		sb.append(" ");
		sb.append(XML_ATT_ANDROID_ID);
		sb.append("=\"");
		sb.append(this.android_id);
		sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DATE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.date));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PHONENUMBER);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.phoneNumber));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_TYPE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.type));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_SMS+">");
		return sb.toString();
	}
}
