/*  */
package fr.inria.diverse.mobileprivacyprofilerserver.database.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.sql.SQLException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Start of user code additional import for OtherCellData
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "otherCellData")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = OtherCellData.class)
public class OtherCellData implements DbClass {

	public static Log log = LogFactory.getLog(OtherCellData.class);

	public static final String XML_OTHERCELLDATA = "OTHERCELLDATA";
	public static final String XML_ATT_ID = "_id";
	public static final String XML_ATT_ANDROID_ID = "android_id";
	public static final String XML_ATT_LACTAC = "lacTac";
	public static final String XML_ATT_TYPE = "type";
	public static final String XML_ATT_USERID = "userId";
	public static final String XML_ATT_MCC = "mcc";
	public static final String XML_ATT_MNC = "mnc";
	public static final String XML_REF_IDENTITY = "identity";
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
	public boolean identity_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected int lacTac;

	@DatabaseField
	protected java.lang.String type;

	@DatabaseField
	protected java.lang.String userId;

	@DatabaseField
	protected java.lang.String mcc;

	@DatabaseField
	protected java.lang.String mnc;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="cell_othercelldata")
	protected Cell identity;

	@DatabaseField
	protected int android_id;

	// Start of user code OtherCellData additional user properties
	// End of user code
	
	public OtherCellData() {} // needed by ormlite
	public OtherCellData(int lacTac, java.lang.String type, java.lang.String userId, java.lang.String mcc, java.lang.String mnc) {
		super();
		this.lacTac = lacTac;
		this.type = type;
		this.userId = userId;
		this.mcc = mcc;
		this.mnc = mnc;
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

	public int getLacTac() {
		return this.lacTac;
	}
	@JsonProperty
	public void setLacTac(int lacTac) {
		this.lacTac = lacTac;
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
	public java.lang.String getMcc() {
		return this.mcc;
	}
	@JsonProperty
	public void setMcc(java.lang.String mcc) {
		this.mcc = mcc;
	}
	public java.lang.String getMnc() {
		return this.mnc;
	}
	@JsonProperty
	public void setMnc(java.lang.String mnc) {
		this.mnc = mnc;
	}

	public Cell getIdentity() {
		try {
			if(identity_mayNeedDBRefresh && _contextDB != null){
				_contextDB.cellDao.refresh(this.identity);
				identity_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.identity == null){
			log.warn("OtherCellData may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.identity;
	}
	@JsonProperty
	public void setIdentity(Cell identity) {
		this.identity = identity;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_OTHERCELLDATA);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
		sb.append(XML_ATT_ANDROID_ID);
		sb.append("=\"");
		sb.append(this.android_id);
		sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LACTAC);
    	sb.append("=\"");
		sb.append(this.lacTac);
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
		sb.append(" ");
    	sb.append(XML_ATT_MCC);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.mcc));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_MNC);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.mnc));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_OTHERCELLDATA+">");
		return sb.toString();
	}
}
