/*  */
package fr.inria.diverse.mobileprivacyprofilerserver.datamodel;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;


//Start of user code additional import for MobilePrivacyProfilerDBHelper
import com.j256.ormlite.dao.CloseableIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//End of user code
/**
 * Context class used to simplify the access to the different DAOs of the application
 */
public class MobilePrivacyProfilerDBHelper {
	//Start of user code additional variables for MobilePrivacyProfilerDBHelper
	// Initialisation de la Gestion des Log
	public static Log log = LogFactory.getLog(MobilePrivacyProfilerDBHelper.class);
	//End of user code

	public Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao;
	//public RuntimeExceptionDao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao;
	public Dao<ApplicationHistory, Integer> applicationHistoryDao;
	//public RuntimeExceptionDao<ApplicationHistory, Integer> applicationHistoryDao;
	public Dao<ApplicationUsageStats, Integer> applicationUsageStatsDao;
	//public RuntimeExceptionDao<ApplicationUsageStats, Integer> applicationUsageStatsDao;
	public Dao<Authentification, Integer> authentificationDao;
	//public RuntimeExceptionDao<Authentification, Integer> authentificationDao;
	public Dao<Contact, Integer> contactDao;
	//public RuntimeExceptionDao<Contact, Integer> contactDao;
	public Dao<ContactOrganisation, Integer> contactOrganisationDao;
	//public RuntimeExceptionDao<ContactOrganisation, Integer> contactOrganisationDao;
	public Dao<ContactIM, Integer> contactIMDao;
	//public RuntimeExceptionDao<ContactIM, Integer> contactIMDao;
	public Dao<ContactEvent, Integer> contactEventDao;
	//public RuntimeExceptionDao<ContactEvent, Integer> contactEventDao;
	public Dao<ContactPhoneNumber, Integer> contactPhoneNumberDao;
	//public RuntimeExceptionDao<ContactPhoneNumber, Integer> contactPhoneNumberDao;
	public Dao<ContactPhysicalAddress, Integer> contactPhysicalAddressDao;
	//public RuntimeExceptionDao<ContactPhysicalAddress, Integer> contactPhysicalAddressDao;
	public Dao<ContactEmail, Integer> contactEmailDao;
	//public RuntimeExceptionDao<ContactEmail, Integer> contactEmailDao;
	public Dao<KnownWifi, Integer> knownWifiDao;
	//public RuntimeExceptionDao<KnownWifi, Integer> knownWifiDao;
	public Dao<DetectedWifi, Integer> detectedWifiDao;
	//public RuntimeExceptionDao<DetectedWifi, Integer> detectedWifiDao;
	public Dao<Geolocation, Integer> geolocationDao;
	//public RuntimeExceptionDao<Geolocation, Integer> geolocationDao;
	public Dao<CalendarEvent, Integer> calendarEventDao;
	//public RuntimeExceptionDao<CalendarEvent, Integer> calendarEventDao;
	public Dao<PhoneCallLog, Integer> phoneCallLogDao;
	//public RuntimeExceptionDao<PhoneCallLog, Integer> phoneCallLogDao;
	public Dao<Cell, Integer> cellDao;
	//public RuntimeExceptionDao<Cell, Integer> cellDao;
	public Dao<OtherCellData, Integer> otherCellDataDao;
	//public RuntimeExceptionDao<OtherCellData, Integer> otherCellDataDao;
	public Dao<CdmaCellData, Integer> cdmaCellDataDao;
	//public RuntimeExceptionDao<CdmaCellData, Integer> cdmaCellDataDao;
	public Dao<NeighboringCellHistory, Integer> neighboringCellHistoryDao;
	//public RuntimeExceptionDao<NeighboringCellHistory, Integer> neighboringCellHistoryDao;
	public Dao<BluetoothDevice, Integer> bluetoothDeviceDao;
	//public RuntimeExceptionDao<BluetoothDevice, Integer> bluetoothDeviceDao;
	public Dao<BluetoothLog, Integer> bluetoothLogDao;
	//public RuntimeExceptionDao<BluetoothLog, Integer> bluetoothLogDao;
	public Dao<SMS, Integer> sMSDao;
	//public RuntimeExceptionDao<SMS, Integer> sMSDao;
	public Dao<BatteryUsage, Integer> batteryUsageDao;
	//public RuntimeExceptionDao<BatteryUsage, Integer> batteryUsageDao;
	public Dao<WebHistory, Integer> webHistoryDao;
	//public RuntimeExceptionDao<WebHistory, Integer> webHistoryDao;

	
	public MobilePrivacyProfilerDBHelper(){
	}

	public MobilePrivacyProfilerDBHelper(
		Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao,
		Dao<ApplicationHistory, Integer> applicationHistoryDao,
		Dao<ApplicationUsageStats, Integer> applicationUsageStatsDao,
		Dao<Authentification, Integer> authentificationDao,
		Dao<Contact, Integer> contactDao,
		Dao<ContactOrganisation, Integer> contactOrganisationDao,
		Dao<ContactIM, Integer> contactIMDao,
		Dao<ContactEvent, Integer> contactEventDao,
		Dao<ContactPhoneNumber, Integer> contactPhoneNumberDao,
		Dao<ContactPhysicalAddress, Integer> contactPhysicalAddressDao,
		Dao<ContactEmail, Integer> contactEmailDao,
		Dao<KnownWifi, Integer> knownWifiDao,
		Dao<DetectedWifi, Integer> detectedWifiDao,
		Dao<Geolocation, Integer> geolocationDao,
		Dao<CalendarEvent, Integer> calendarEventDao,
		Dao<PhoneCallLog, Integer> phoneCallLogDao,
		Dao<Cell, Integer> cellDao,
		Dao<OtherCellData, Integer> otherCellDataDao,
		Dao<CdmaCellData, Integer> cdmaCellDataDao,
		Dao<NeighboringCellHistory, Integer> neighboringCellHistoryDao,
		Dao<BluetoothDevice, Integer> bluetoothDeviceDao,
		Dao<BluetoothLog, Integer> bluetoothLogDao,
		Dao<SMS, Integer> sMSDao,
		Dao<BatteryUsage, Integer> batteryUsageDao,
		Dao<WebHistory, Integer> webHistoryDao        
	){
		this.mobilePrivacyProfilerDB_metadataDao = mobilePrivacyProfilerDB_metadataDao;
		this.applicationHistoryDao = applicationHistoryDao;
		this.applicationUsageStatsDao = applicationUsageStatsDao;
		this.authentificationDao = authentificationDao;
		this.contactDao = contactDao;
		this.contactOrganisationDao = contactOrganisationDao;
		this.contactIMDao = contactIMDao;
		this.contactEventDao = contactEventDao;
		this.contactPhoneNumberDao = contactPhoneNumberDao;
		this.contactPhysicalAddressDao = contactPhysicalAddressDao;
		this.contactEmailDao = contactEmailDao;
		this.knownWifiDao = knownWifiDao;
		this.detectedWifiDao = detectedWifiDao;
		this.geolocationDao = geolocationDao;
		this.calendarEventDao = calendarEventDao;
		this.phoneCallLogDao = phoneCallLogDao;
		this.cellDao = cellDao;
		this.otherCellDataDao = otherCellDataDao;
		this.cdmaCellDataDao = cdmaCellDataDao;
		this.neighboringCellHistoryDao = neighboringCellHistoryDao;
		this.bluetoothDeviceDao = bluetoothDeviceDao;
		this.bluetoothLogDao = bluetoothLogDao;
		this.sMSDao = sMSDao;
		this.batteryUsageDao = batteryUsageDao;
		this.webHistoryDao = webHistoryDao;
	}

	//Start of user code additional methods for MobilePrivacyProfilerDBHelper

	/** find ApplicationHistory in the base using appName
	 * @param appName
	 * @return
	 */
	public ApplicationHistory queryApplicationHistoryByAppName(String appName) {
		try {
			ApplicationHistory queryApplicationHistory = new ApplicationHistory();
			queryApplicationHistory.setAppName(appName);
			List<ApplicationHistory> fichesDeLaBase = this.applicationHistoryDao.queryForMatching(queryApplicationHistory);
			if(fichesDeLaBase.size() != 1){
				log.debug("Application with appName "+queryApplicationHistory.getAppName()+ " doesn't exist in the base");
				return null;
			}
			return fichesDeLaBase.get(0);
		} catch (SQLException e) {
			log.error("error while querying application with appName "+appName+ " in the base", e);
		}
		return null;
	}

	/** find ApplicationHistory in the base using appName
	 * @param packageName
	 * @return
	 */
	public ApplicationHistory queryApplicationHistoryByPackageName(String packageName) {
		try {
			ApplicationHistory queryApplicationHistory = new ApplicationHistory();
			queryApplicationHistory.setPackageName(packageName);
			List<ApplicationHistory> fichesDeLaBase = this.applicationHistoryDao.queryForMatching(queryApplicationHistory);
			if(fichesDeLaBase.size() != 1){
			//	Log.d(TAG,"Application with packageName "+queryApplicationHistory.getPackageName()+ " doesn't exist in the base");
				return null;
			}
			return fichesDeLaBase.get(0);
		} catch (SQLException e) {
			log.error("error while querying application with packageName "+packageName+ " in the base", e);
		}
		return null;
	}

	/** find ApplicationUsageStats in the base using refereed ApplicationHistory
	 * @param applicationHistory
	 * @return
	 */
	public List<ApplicationUsageStats> queryApplicationUsageStatsByApplicationHistory(ApplicationHistory applicationHistory) {
		try {
			ApplicationUsageStats queryApplicationUsageStats = new ApplicationUsageStats();
			queryApplicationUsageStats.setApplication(applicationHistory);
			List<ApplicationUsageStats> fichesDeLaBase = this.applicationUsageStatsDao.queryForMatching(queryApplicationUsageStats);
			if(0==fichesDeLaBase.size()){
				log.debug("ApplicationUsageStats doesn't exist in the base for this Application :"+applicationHistory.getPackageName());
				return null;
			}
			return fichesDeLaBase;
		} catch (SQLException e) { log.error("error while querying applicationUsageStats with application from package :"+applicationHistory.packageName+ " in the base", e); }
		return null;
	}

	/** find Cell in the base using cellId
	 * @param cellId
	 * @return Cell with the cellId Identity
	 */
	public Cell queryCellByCellId(int cellId){
		//Log.d(TAG,"queryCellByCellId with parameter : "+ cellId);
		Cell queryCell = new Cell();
		queryCell.setCellId(cellId);
		log.debug("queryCellByCellId with "+queryCell.getCellId()+" as cellId");
		try {
			List<Cell> queryOutput = this.cellDao.queryForMatching(queryCell);
			if(1 != queryOutput.size()){
				log.debug("Cell with cellId "+queryCell.getCellId()+ " doesn't exist in the base");
				return null;
			}
			return queryOutput.get(0);
		} catch (SQLException e) {
			log.error("error while querying cell with cellId "+cellId+ " in the base", e);
		}
		return null;
	}

	/** find Cell in the base using cellId
	 * @param
	 * @return A list of String types
	 */
	public List<String> queryAllAuthentificationType(){
		List<String> result=new ArrayList<String>();

		Authentification queryAuth = new Authentification();
		List<Authentification> queryOutput=new ArrayList<>();
		try {
			queryOutput = this.authentificationDao.queryForMatching(queryAuth);
		} catch (SQLException e) {
			log.error("error while querying Authentification types in the base", e);
			}

		for(Authentification auth :queryOutput){
			result.add(auth.getType());
		}

		return result;
	}

	/** find Cell in the base using cellId
	 * @param eventID
	 * @return the corresponding CalendarEvent or null if not found
	 */
	public CalendarEvent queryCalendarEvent(long eventID){
		CalendarEvent queryCalendarEvent = new CalendarEvent();
		queryCalendarEvent.setEventId(eventID);
		List<CalendarEvent> queryOutput= null;
		try {
			queryOutput = this.calendarEventDao.queryForMatching(queryCalendarEvent);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(0!=queryOutput.size()) {return queryOutput.get(0);}
		else { return null; }
	}

	/**
	 * On the device this table contains a single entry
	 * @return
	 */
	public MobilePrivacyProfilerDB_metadata getDeviceDBMetadata() {
		MobilePrivacyProfilerDB_metadata metadata=null;
		List<MobilePrivacyProfilerDB_metadata> it = new ArrayList<>();
        try {
            it= this.mobilePrivacyProfilerDB_metadataDao.queryForAll();

        if(!it.isEmpty()){
			metadata = it.get(0);
		} else {
			metadata = new MobilePrivacyProfilerDB_metadata();
			metadata.setUserId(UUID.randomUUID().toString());
				this.mobilePrivacyProfilerDB_metadataDao.create(metadata);
		}
        } catch (SQLException e) { log.error("error while getting MobilePrivacyProfilerDB_metadata", e);}

		return metadata;
	}

	/**
	 * Get a list of all entries in ApplicationHistory
	 * @return List<ApplicationHistory>
	 */
	public List<ApplicationHistory> getAllApplicationHistory() throws SQLException {
		List<ApplicationHistory> result=null;
		List<ApplicationHistory> it = this.applicationHistoryDao.queryForAll();

		return it;
	}

	/**
	 *
	 * @param object
	 * @param type
	 * @param dao
	 * @return true is the argument is an entry from DB
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public boolean isRegisteredDbClassObject(DbClass object, Class type, Dao dao) throws IllegalAccessException, InstantiationException {

		DbClass query = (DbClass) type.newInstance();
		query.set_id(object.get_id());
		query.setUserId(object.getUserId());
		List queryOutput = null;
		try {
			queryOutput = dao.queryForMatching(type.cast(query));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return !queryOutput.isEmpty();
	}

	/**
	 * Update the DB with a new version of data from the client
	 * @param object
	 * @param type
	 * @param dao
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */

	public void updateObjectFromDB(DbClass object, Class type, Dao dao) throws IllegalAccessException, InstantiationException {
		DbClass query = (DbClass) type.newInstance();
		query.set_id(object.get_id());
		query.setUserId(object.getUserId());
		List queryOutput = null;
		try {
			queryOutput = dao.queryForMatching(type.cast(query));
		} catch (SQLException e) { e.printStackTrace(); }

		if (queryOutput.size()==1) {
			int id =((DbClass) queryOutput.get(0)).getId();
			object.setId(id);
			try {dao.createOrUpdate(type.cast(object));
				/*dao.delete(queryOutput);
				dao.create(type.cast(object));*/
			} catch (SQLException e) { e.printStackTrace(); }
			log.info("Updated an "+type.getSimpleName()+" entry in the DB");
		}
		else{
			log.info("An update of an "+type.getSimpleName()+" entry from DB went wrong");
		}
	}

	/**
	 * drop all data from Contact, ContactPhoneNumber, ContactIM, ContactOrganisation, ContactEvent,
	 * ContactEmail, ContactPhysicalAddress
	 */
	public void flushContactDataSet(){

		List<ContactPhoneNumber> queryContactPhoneNumberOutput=new ArrayList<>();
		try {
			queryContactPhoneNumberOutput = this.contactPhoneNumberDao.queryForAll();
			this.contactPhoneNumberDao.delete(queryContactPhoneNumberOutput);
		} catch (SQLException e) { log.error("error while flushing ContactPhoneNumbers from previous scan the base", e); }

		List<ContactIM> queryContactIMOutput=new ArrayList<>();
		try {
			queryContactIMOutput = this.contactIMDao.queryForAll();
			this.contactIMDao.delete(queryContactIMOutput);
		} catch (SQLException e) { log.error("error while flushing ContactInstantMessengers from previous scan the base", e); }

		List<ContactOrganisation> queryContactOrganisationOutput=new ArrayList<>();
		try {
			queryContactOrganisationOutput = this.contactOrganisationDao.queryForAll();
			this.contactOrganisationDao.delete(queryContactOrganisationOutput);
		}catch (SQLException e) { log.error("error while flushing ContactOrganisations from previous scan the base", e); }

		List<ContactEvent> queryContactEventOutput=new ArrayList<>();
		try {
			queryContactEventOutput = this.contactEventDao.queryForAll();
			this.contactEventDao.delete(queryContactEventOutput);
		} catch (SQLException e) { log.error("error while flushing ContactEvents from previous scan the base", e); }

		List<ContactEmail> queryContactEmailOutput=new ArrayList<>();
		try {
			queryContactEmailOutput = this.contactEmailDao.queryForAll();
			this.contactEmailDao.delete(queryContactEmailOutput);
		} catch (SQLException e) { log.error("error while flushing ContactEmails from previous scan the base", e); }

		List<ContactPhysicalAddress> queryContactPhysicalAddressOutput=new ArrayList<>();
		try {
			queryContactPhysicalAddressOutput = this.contactPhysicalAddressDao.queryForAll();
			this.contactPhysicalAddressDao.delete(queryContactPhysicalAddressOutput);
		} catch (SQLException e) { log.error("error while flushing ContactPhysicalAddresses from previous scan the base", e); }

		List<Contact> queryContactOutput=new ArrayList<>();
		try {
			queryContactOutput = this.contactDao.queryForAll();
			this.contactDao.delete(queryContactOutput);
		} catch (SQLException e) { log.error("error while flushing Contacts from previous scan the base", e); }

	}

	//End of user code

}
