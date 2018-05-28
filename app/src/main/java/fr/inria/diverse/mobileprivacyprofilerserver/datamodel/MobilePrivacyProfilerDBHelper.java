/*  */
package fr.inria.diverse.mobileprivacyprofilerserver.datamodel;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

import fr.inria.diverse.mobileprivacyprofilerserver.datamodel.associations.DetectedWifi_AccessPoint;

//Start of user code additional import for MobilePrivacyProfilerDBHelper
import com.j256.ormlite.dao.CloseableIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.List;
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
	public Dao<ContactPhoneNumber, Integer> contactPhoneNumberDao;
	//public RuntimeExceptionDao<ContactPhoneNumber, Integer> contactPhoneNumberDao;
	public Dao<ContactPhysicalAddress, Integer> contactPhysicalAddressDao;
	//public RuntimeExceptionDao<ContactPhysicalAddress, Integer> contactPhysicalAddressDao;
	public Dao<ContactEmail, Integer> contactEmailDao;
	//public RuntimeExceptionDao<ContactEmail, Integer> contactEmailDao;
	public Dao<KnownWifi, Integer> knownWifiDao;
	//public RuntimeExceptionDao<KnownWifi, Integer> knownWifiDao;
	public Dao<WifiAccessPoint, Integer> wifiAccessPointDao;
	//public RuntimeExceptionDao<WifiAccessPoint, Integer> wifiAccessPointDao;
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
	public Dao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointDao;
	//public RuntimeExceptionDao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointDao;

	
	public MobilePrivacyProfilerDBHelper(){
	}

	public MobilePrivacyProfilerDBHelper(
		Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao,
		Dao<ApplicationHistory, Integer> applicationHistoryDao,
		Dao<ApplicationUsageStats, Integer> applicationUsageStatsDao,
		Dao<Authentification, Integer> authentificationDao,
		Dao<Contact, Integer> contactDao,
		Dao<ContactPhoneNumber, Integer> contactPhoneNumberDao,
		Dao<ContactPhysicalAddress, Integer> contactPhysicalAddressDao,
		Dao<ContactEmail, Integer> contactEmailDao,
		Dao<KnownWifi, Integer> knownWifiDao,
		Dao<WifiAccessPoint, Integer> wifiAccessPointDao,
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
		Dao<WebHistory, Integer> webHistoryDao,
        Dao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointDao
	){
		this.mobilePrivacyProfilerDB_metadataDao = mobilePrivacyProfilerDB_metadataDao;
		this.applicationHistoryDao = applicationHistoryDao;
		this.applicationUsageStatsDao = applicationUsageStatsDao;
		this.authentificationDao = authentificationDao;
		this.contactDao = contactDao;
		this.contactPhoneNumberDao = contactPhoneNumberDao;
		this.contactPhysicalAddressDao = contactPhysicalAddressDao;
		this.contactEmailDao = contactEmailDao;
		this.knownWifiDao = knownWifiDao;
		this.wifiAccessPointDao = wifiAccessPointDao;
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
		this.detectedWifi_AccessPointDao = detectedWifi_AccessPointDao;
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

	/** find Cell in the base using cellId
	 * @param cellId
	 * @return Cell with the cellId Identity
	 */
	public Cell queryCellByCellId(int cellId){
		try {
			Cell queryCell = new Cell();
			queryCell.setId(cellId);
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
		List<String> result=null;

		Authentification queryAuth = new Authentification();
		List<Authentification> queryOutput=null;
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

		if(null!=queryOutput) {return queryOutput.get(0);}
		else { return null; }
	}

	/**
	 * On the device this table contains a single entry
	 * @return
	 */
	public MobilePrivacyProfilerDB_metadata getDeviceDBMetadata() {
		MobilePrivacyProfilerDB_metadata metadata;
		CloseableIterator<MobilePrivacyProfilerDB_metadata> it = this.mobilePrivacyProfilerDB_metadataDao.iterator();
		if(it.hasNext()){
			metadata = it.next();
			it.closeQuietly();
		} else {
			metadata = new MobilePrivacyProfilerDB_metadata();
			try {
				this.mobilePrivacyProfilerDB_metadataDao.create(metadata);
			} catch (SQLException e) {
				log.error("error while creating MobilePrivacyProfilerDB_metadata", e);
			}
		}
		return metadata;
	}

	/**
	 * Get a list of all entries in ApplicationHistory
	 * @return List<ApplicationHistory>
	 */
	public List<ApplicationHistory> getAllApplicationHistory() {

		ApplicationHistory queryApplicationHistory = new ApplicationHistory();
		List<ApplicationHistory> queryOutput = null;
		try {
			queryOutput = this.applicationHistoryDao.queryForMatching(queryApplicationHistory);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryOutput;
	}

	/**
	 *
	 * @return true is the argument is an entry from DB
	 */
	public boolean isRegistredApplicationHistory(ApplicationHistory appHist) {

		ApplicationHistory query = new ApplicationHistory();
		query.setPackageName(appHist.getPackageName());
		query.set_id(appHist.get_id());
		List<ApplicationHistory> queryOutput = null;
		try {
			queryOutput = this.applicationHistoryDao.queryForMatching(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return !queryOutput.isEmpty();
	}

	//End of user code

}
