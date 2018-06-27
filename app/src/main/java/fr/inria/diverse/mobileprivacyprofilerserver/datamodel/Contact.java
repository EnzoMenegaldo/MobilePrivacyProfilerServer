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

// Start of user code additional import for Contact
// End of user code

/** 
  * Contact in the device 
  */ 
@DatabaseTable(tableName = "contact")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "android_id",
				  scope = Contact.class)
public class Contact implements DbClass {

	public static Log log = LogFactory.getLog(Contact.class);

	public static final String XML_CONTACT = "CONTACT";
	public static final String XML_ATT_ID = "_id";
	public static final String XML_ATT_ANDROID_ID = "android_id";
	public static final String XML_ATT_SURNAME = "surname";
	public static final String XML_ATT_FIRSTNAME = "firstName";
	public static final String XML_ATT_MIDDLENAME = "middleName";
	public static final String XML_ATT_LASTNAME = "lastName";
	public static final String XML_ATT_NAMEPREFIX = "namePrefix";
	public static final String XML_ATT_DISPLAYNAME = "displayName";
	public static final String XML_ATT_NAMESUFFIX = "nameSuffix";
	public static final String XML_ATT_NICKNAME = "nickname";
	public static final String XML_ATT_RELATION = "relation";
	public static final String XML_ATT_WEBSITE = "website";
	public static final String XML_ATT_SCANTIMESTAMP = "scanTimeStamp";
	public static final String XML_ATT_USERID = "userId";
	public static final String XML_REF_PHONENUMBERS = "phoneNumbers";
	public static final String XML_REF_PHYSICALADDRESSES = "physicalAddresses";
	public static final String XML_REF_EMAILS = "emails";
	public static final String XML_REF_CONTACTORGANISATION = "contactOrganisation";
	public static final String XML_REF_CONTACTIM = "contactIM";
	public static final String XML_REF_CONTACTEVENT = "contactEvent";
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
	public boolean contactOrganisation_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected java.lang.String surname;

	@DatabaseField
	protected java.lang.String firstName;

	@DatabaseField
	protected java.lang.String middleName;

	@DatabaseField
	protected java.lang.String lastName;

	/** Title or
 Mr, M */ 
	@DatabaseField
	protected java.lang.String namePrefix;

	@DatabaseField
	protected java.lang.String displayName;

	@DatabaseField
	protected java.lang.String nameSuffix;

	@DatabaseField
	protected java.lang.String nickname;

	@DatabaseField
	protected java.lang.String relation;

	@DatabaseField
	protected java.lang.String website;

	/** Attribut allowing to discriminate contacts created over different divice scan on server side. */ 
	@DatabaseField
	protected java.util.Date scanTimeStamp;

	@DatabaseField
	protected java.lang.String userId;
	

	@ForeignCollectionField(eager = false, foreignFieldName = "contact")
	@JsonIgnore
	protected ForeignCollection<ContactPhoneNumber> phoneNumbers;

	@ForeignCollectionField(eager = false, foreignFieldName = "contact")
	@JsonIgnore
	protected ForeignCollection<ContactPhysicalAddress> physicalAddresses;

	@ForeignCollectionField(eager = false, foreignFieldName = "contact")
	@JsonIgnore
	protected ForeignCollection<ContactEmail> emails;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="contact_contactorganisation")
	protected ContactOrganisation contactOrganisation;

	@ForeignCollectionField(eager = false, foreignFieldName = "contact")
	@JsonIgnore
	protected ForeignCollection<ContactIM> contactIM;

	@ForeignCollectionField(eager = false, foreignFieldName = "contact")
	@JsonIgnore
	protected ForeignCollection<ContactEvent> contactEvent;

	@DatabaseField
	protected int android_id;

	// Start of user code Contact additional user properties
	// End of user code
	
	public Contact() {} // needed by ormlite
	public Contact(java.lang.String surname, java.lang.String firstName, java.lang.String middleName, java.lang.String lastName, java.lang.String namePrefix, java.lang.String displayName, java.lang.String nameSuffix, java.lang.String nickname, java.lang.String relation, java.lang.String website, java.util.Date scanTimeStamp, java.lang.String userId) {
		super();
		this.surname = surname;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.namePrefix = namePrefix;
		this.displayName = displayName;
		this.nameSuffix = nameSuffix;
		this.nickname = nickname;
		this.relation = relation;
		this.website = website;
		this.scanTimeStamp = scanTimeStamp;
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

	public java.lang.String getSurname() {
		return this.surname;
	}
	@JsonProperty
	public void setSurname(java.lang.String surname) {
		this.surname = surname;
	}
	public java.lang.String getFirstName() {
		return this.firstName;
	}
	@JsonProperty
	public void setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
	}
	public java.lang.String getMiddleName() {
		return this.middleName;
	}
	@JsonProperty
	public void setMiddleName(java.lang.String middleName) {
		this.middleName = middleName;
	}
	public java.lang.String getLastName() {
		return this.lastName;
	}
	@JsonProperty
	public void setLastName(java.lang.String lastName) {
		this.lastName = lastName;
	}
	public java.lang.String getNamePrefix() {
		return this.namePrefix;
	}
	@JsonProperty
	public void setNamePrefix(java.lang.String namePrefix) {
		this.namePrefix = namePrefix;
	}
	public java.lang.String getDisplayName() {
		return this.displayName;
	}
	@JsonProperty
	public void setDisplayName(java.lang.String displayName) {
		this.displayName = displayName;
	}
	public java.lang.String getNameSuffix() {
		return this.nameSuffix;
	}
	@JsonProperty
	public void setNameSuffix(java.lang.String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
	public java.lang.String getNickname() {
		return this.nickname;
	}
	@JsonProperty
	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
	}
	public java.lang.String getRelation() {
		return this.relation;
	}
	@JsonProperty
	public void setRelation(java.lang.String relation) {
		this.relation = relation;
	}
	public java.lang.String getWebsite() {
		return this.website;
	}
	@JsonProperty
	public void setWebsite(java.lang.String website) {
		this.website = website;
	}
	public java.util.Date getScanTimeStamp() {
		return this.scanTimeStamp;
	}
	@JsonProperty
	public void setScanTimeStamp(java.util.Date scanTimeStamp) {
		this.scanTimeStamp = scanTimeStamp;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	@JsonProperty
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public List	<ContactPhoneNumber> getPhoneNumbers() {
		if(null==this.phoneNumbers){return null;}
		return new ArrayList<ContactPhoneNumber>(phoneNumbers);
	}
	

			
	public List	<ContactPhysicalAddress> getPhysicalAddresses() {
		if(null==this.physicalAddresses){return null;}
		return new ArrayList<ContactPhysicalAddress>(physicalAddresses);
	}
	

			
	public List	<ContactEmail> getEmails() {
		if(null==this.emails){return null;}
		return new ArrayList<ContactEmail>(emails);
	}
	

			
	public ContactOrganisation getContactOrganisation() {
		try {
			if(contactOrganisation_mayNeedDBRefresh && _contextDB != null){
				_contextDB.contactOrganisationDao.refresh(this.contactOrganisation);
				contactOrganisation_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.contactOrganisation == null){
			log.warn("Contact may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.contactOrganisation;
	}
	@JsonProperty
	public void setContactOrganisation(ContactOrganisation contactOrganisation) {
		this.contactOrganisation = contactOrganisation;
	}			
	public List	<ContactIM> getContactIM() {
		if(null==this.contactIM){return null;}
		return new ArrayList<ContactIM>(contactIM);
	}
	

			
	public List	<ContactEvent> getContactEvent() {
		if(null==this.contactEvent){return null;}
		return new ArrayList<ContactEvent>(contactEvent);
	}
	

			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_CONTACT);
		sb.append(" "+XML_ATT_ANDROID_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
		sb.append(XML_ATT_ANDROID_ID);
		sb.append("=\"");
		sb.append(this.android_id);
		sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_SURNAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.surname));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_FIRSTNAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.firstName));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_MIDDLENAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.middleName));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LASTNAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.lastName));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_NAMEPREFIX);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.namePrefix));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DISPLAYNAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.displayName));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_NAMESUFFIX);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.nameSuffix));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_NICKNAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.nickname));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_RELATION);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.relation));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_WEBSITE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.website));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_SCANTIMESTAMP);
    	sb.append("=\"");
		sb.append(this.scanTimeStamp);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		sb.append("\n"+indent+"\t<"+XML_REF_PHONENUMBERS+">");
		if(this.phoneNumbers != null){
			for(ContactPhoneNumber ref : this.phoneNumbers){
				sb.append("\n"+ref.toXML(indent+"\t\t", contextDB));
	    	}
		}
		sb.append("</"+XML_REF_PHONENUMBERS+">");		
		sb.append("\n"+indent+"\t<"+XML_REF_PHYSICALADDRESSES+">");
		if(this.physicalAddresses != null){
			for(ContactPhysicalAddress ref : this.physicalAddresses){
				sb.append("\n"+ref.toXML(indent+"\t\t", contextDB));
	    	}
		}
		sb.append("</"+XML_REF_PHYSICALADDRESSES+">");		
		sb.append("\n"+indent+"\t<"+XML_REF_EMAILS+">");
		if(this.emails != null){
			for(ContactEmail ref : this.emails){
				sb.append("\n"+ref.toXML(indent+"\t\t", contextDB));
	    	}
		}
		sb.append("</"+XML_REF_EMAILS+">");		
		if(this.contactOrganisation!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_CONTACTORGANISATION+">");
			sb.append(this.contactOrganisation.get_id());
	    	sb.append("</"+XML_REF_CONTACTORGANISATION+">");
		}
		sb.append("\n"+indent+"\t<"+XML_REF_CONTACTIM+">");
		if(this.contactIM != null){
			for(ContactIM ref : this.contactIM){
				sb.append("\n"+ref.toXML(indent+"\t\t", contextDB));
	    	}
		}
		sb.append("</"+XML_REF_CONTACTIM+">");		
		sb.append("\n"+indent+"\t<"+XML_REF_CONTACTEVENT+">");
		if(this.contactEvent != null){
			for(ContactEvent ref : this.contactEvent){
				sb.append("\n"+ref.toXML(indent+"\t\t", contextDB));
	    	}
		}
		sb.append("</"+XML_REF_CONTACTEVENT+">");		
		// TODO deal with other case

		sb.append("</"+XML_CONTACT+">");
		return sb.toString();
	}
}
