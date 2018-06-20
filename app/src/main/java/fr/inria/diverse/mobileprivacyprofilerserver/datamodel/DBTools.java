/*  */
package fr.inria.diverse.mobileprivacyprofilerserver.datamodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DBTools {
	
	// Initialisation de la Gestion des Log 
	public static Log log = LogFactory.getLog(DBTools.class);
	
	// Constructeur
	public DBTools(){
		
	}
	
	public void initializeSQLite(String url) throws ClassNotFoundException, SQLException{
		
		Class.forName("org.sqlite.JDBC");		
		Connection c = DriverManager.getConnection(url);
		log.debug("Opened database successfully");
		
		Statement  stmt = c.createStatement();
		String sql = "CREATE TABLE \"android_metadata\" (\"locale\" TEXT DEFAULT 'en_US')"; 
		stmt.executeUpdate(sql);
		stmt.close();
		
		stmt = c.createStatement();
		sql = "    INSERT INTO \"android_metadata\" VALUES ('en_US')"; 
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();

	}
	
	/**
	 * Setup our database and DAOs
	 */
	public MobilePrivacyProfilerDBHelper setupDatabase(ConnectionSource connectionSource)
			throws Exception {
		log.debug("setupDatabase() - Start");
		
		MobilePrivacyProfilerDBHelper dbContext = null;
		
		dbContext = new MobilePrivacyProfilerDBHelper();
		
		dbContext.mobilePrivacyProfilerDB_metadataDao = DaoManager.createDao(connectionSource, MobilePrivacyProfilerDB_metadata.class);
		dbContext.applicationHistoryDao = DaoManager.createDao(connectionSource, ApplicationHistory.class);
		dbContext.applicationUsageStatsDao = DaoManager.createDao(connectionSource, ApplicationUsageStats.class);
		dbContext.authentificationDao = DaoManager.createDao(connectionSource, Authentification.class);
		dbContext.contactDao = DaoManager.createDao(connectionSource, Contact.class);
		dbContext.contactOrganisationDao = DaoManager.createDao(connectionSource, ContactOrganisation.class);
		dbContext.contactIMDao = DaoManager.createDao(connectionSource, ContactIM.class);
		dbContext.contactEventDao = DaoManager.createDao(connectionSource, ContactEvent.class);
		dbContext.contactPhoneNumberDao = DaoManager.createDao(connectionSource, ContactPhoneNumber.class);
		dbContext.contactPhysicalAddressDao = DaoManager.createDao(connectionSource, ContactPhysicalAddress.class);
		dbContext.contactEmailDao = DaoManager.createDao(connectionSource, ContactEmail.class);
		dbContext.knownWifiDao = DaoManager.createDao(connectionSource, KnownWifi.class);
		dbContext.logsWifiDao = DaoManager.createDao(connectionSource, LogsWifi.class);
		dbContext.geolocationDao = DaoManager.createDao(connectionSource, Geolocation.class);
		dbContext.calendarEventDao = DaoManager.createDao(connectionSource, CalendarEvent.class);
		dbContext.phoneCallLogDao = DaoManager.createDao(connectionSource, PhoneCallLog.class);
		dbContext.cellDao = DaoManager.createDao(connectionSource, Cell.class);
		dbContext.otherCellDataDao = DaoManager.createDao(connectionSource, OtherCellData.class);
		dbContext.cdmaCellDataDao = DaoManager.createDao(connectionSource, CdmaCellData.class);
		dbContext.neighboringCellHistoryDao = DaoManager.createDao(connectionSource, NeighboringCellHistory.class);
		dbContext.bluetoothDeviceDao = DaoManager.createDao(connectionSource, BluetoothDevice.class);
		dbContext.bluetoothLogDao = DaoManager.createDao(connectionSource, BluetoothLog.class);
		dbContext.sMSDao = DaoManager.createDao(connectionSource, SMS.class);
		dbContext.batteryUsageDao = DaoManager.createDao(connectionSource, BatteryUsage.class);
		dbContext.webHistoryDao = DaoManager.createDao(connectionSource, WebHistory.class);

		return dbContext;
	}
		
	/**
	 *  Tables creation
	 */
	public void databaseInitialisation(ConnectionSource connectionSource)
			throws Exception {
		log.debug("databaseInitialisation() - Start");
		
		// if you need to create the table
		TableUtils.createTable(connectionSource, MobilePrivacyProfilerDB_metadata.class);
		TableUtils.createTable(connectionSource, ApplicationHistory.class);
		TableUtils.createTable(connectionSource, ApplicationUsageStats.class);
		TableUtils.createTable(connectionSource, Authentification.class);
		TableUtils.createTable(connectionSource, Contact.class);
		TableUtils.createTable(connectionSource, ContactOrganisation.class);
		TableUtils.createTable(connectionSource, ContactIM.class);
		TableUtils.createTable(connectionSource, ContactEvent.class);
		TableUtils.createTable(connectionSource, ContactPhoneNumber.class);
		TableUtils.createTable(connectionSource, ContactPhysicalAddress.class);
		TableUtils.createTable(connectionSource, ContactEmail.class);
		TableUtils.createTable(connectionSource, KnownWifi.class);
		TableUtils.createTable(connectionSource, LogsWifi.class);
		TableUtils.createTable(connectionSource, Geolocation.class);
		TableUtils.createTable(connectionSource, CalendarEvent.class);
		TableUtils.createTable(connectionSource, PhoneCallLog.class);
		TableUtils.createTable(connectionSource, Cell.class);
		TableUtils.createTable(connectionSource, OtherCellData.class);
		TableUtils.createTable(connectionSource, CdmaCellData.class);
		TableUtils.createTable(connectionSource, NeighboringCellHistory.class);
		TableUtils.createTable(connectionSource, BluetoothDevice.class);
		TableUtils.createTable(connectionSource, BluetoothLog.class);
		TableUtils.createTable(connectionSource, SMS.class);
		TableUtils.createTable(connectionSource, BatteryUsage.class);
		TableUtils.createTable(connectionSource, WebHistory.class);

		
		log.debug("databaseInitialisation() - End");
	}
	
}

