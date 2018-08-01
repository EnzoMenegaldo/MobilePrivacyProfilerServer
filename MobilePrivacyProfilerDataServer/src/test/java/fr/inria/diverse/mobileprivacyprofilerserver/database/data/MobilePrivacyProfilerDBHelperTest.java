package fr.inria.diverse.mobileprivacyprofilerserver.database.data;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil.DBTools;
import fr.inria.diverse.mobileprivacyprofilerserver.database.user.UserDBHelper;
import fr.inria.diverse.mobileprivacyprofilerserver.database.user.UserDBHelperTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class MobilePrivacyProfilerDBHelperTest {

    public final static String DATABASE_FILE = "database/MobilePrivacyProfilerDB.db";
    public static String DATABASE_URL;
    static JdbcConnectionSource connectionSource ;
    static File database_file;

    @Before
    public void before(){
        String database_path = MobilePrivacyProfilerDBHelper.class.getClassLoader().getResource(DATABASE_FILE).getPath();
        database_file = new File(database_path);

        if(database_file.exists()){
            try {
                database_file.delete();
                database_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DATABASE_URL = "jdbc:sqlite:" + database_path;
    }

    @Test
    public void initialize() {
    }

    @Test
    public void queryApplicationHistoryByAppName() {
    }

    @Test
    public void queryApplicationHistoryByPackageName() {
    }

    @Test
    public void queryApplicationUsageStatsByApplicationHistory() {
    }

    @Test
    public void queryCellByCellId() {
    }

    @Test
    public void queryAllAuthentificationType() {
    }

    @Test
    public void queryCalendarEvent() {
    }

    @Test
    public void getDeviceDBMetadata() {
    }

    @Test
    public void getAllApplicationHistory() {
    }

    @Test
    public void isRegisteredDbClassObject() {
    }

    @Test
    public void updateObjectFromDB() {
    }

    @Test
    public void flushContactDataSet() {
    }

    @Test
    public void setupDatabase() {
        try {

            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            DBTools.INSTANCE.initializeSQLite(DATABASE_URL);

            MobilePrivacyProfilerDBHelper.INSTANCE.databaseInitialisation(connectionSource);

            MobilePrivacyProfilerDBHelper.INSTANCE.setupDatabase(connectionSource);

            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.mobilePrivacyProfilerDB_metadataDao.isTableExists(),null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.applicationHistoryDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.applicationUsageStatsDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.authentificationDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.contactDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.contactOrganisationDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.contactIMDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.contactEventDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.contactPhoneNumberDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.contactPhysicalAddressDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.contactEmailDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.knownWifiDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.logsWifiDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.geolocationDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.calendarEventDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.phoneCallLogDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.cellDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.otherCellDataDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.cdmaCellDataDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.neighboringCellHistoryDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.bluetoothDeviceDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.bluetoothLogDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.sMSDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.batteryUsageDao,null);
            assertNotEquals(MobilePrivacyProfilerDBHelper.INSTANCE.netActivityDao,null);

        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void databaseInitialisation() {
        try {

            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            DBTools.INSTANCE.initializeSQLite(DATABASE_URL);

            MobilePrivacyProfilerDBHelper.INSTANCE.databaseInitialisation(connectionSource);
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.mobilePrivacyProfilerDB_metadataDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.applicationHistoryDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.applicationUsageStatsDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.authentificationDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.contactDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.contactOrganisationDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.contactIMDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.contactEventDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.contactPhoneNumberDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.contactPhysicalAddressDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.contactEmailDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.knownWifiDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.logsWifiDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.geolocationDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.calendarEventDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.phoneCallLogDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.cellDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.otherCellDataDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.cdmaCellDataDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.neighboringCellHistoryDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.bluetoothDeviceDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.bluetoothLogDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.sMSDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.batteryUsageDao.isTableExists());
            assertTrue(MobilePrivacyProfilerDBHelper.INSTANCE.netActivityDao.isTableExists());


        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}