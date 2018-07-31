package fr.inria.diverse.mobileprivacyprofilerserver;/*  */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.j256.ormlite.dao.Dao;
import fr.inria.diverse.mobileprivacyprofilerserver.database.data.*;
import fr.inria.diverse.mobileprivacyprofilerserver.database.user.UserDataBaseHelper;
import fr.inria.diverse.mobileprivacyprofilerserver.utils.AuthenticationUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import spark.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static spark.Spark.*;

// Start of user code additional import for APIserver :
import static spark.Spark.secure;
// End of user code

/** 
  *  
  */ 

public class APIServer {

private static MobilePrivacyProfilerDBHelper dbHelper;
    // Initialising log management
    private static Log log = LogFactory.getLog(APIServer.class);

	private static final int MAX_THREAD = 8;

// Start of user code additional other code :

// End of user code

    public static void main(String[] args) {

// Start of user code additional main code :
	threadPool(MAX_THREAD);

        try {
            UserDataBaseHelper.INSTANCE.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
// End of user code

    get("/hello", (request, response) -> "Hello World");

    post("/Authentication",(request, response) -> {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeRoot = objectMapper.readTree(request.body());

        String username = jsonNodeRoot.get("username").asText();
        String password = jsonNodeRoot.get("password").asText();
        String device = jsonNodeRoot.get("device").asText();

        String result = UserDataBaseHelper.INSTANCE.checkCredentials(username,password,device);

        response.type("application/json");

        JsonNode jsonBody = JsonNodeFactory.instance.objectNode();

        if(result.equals(UserDataBaseHelper.SUCCESSFUL_AUTHENTICATION)){
            String token = AuthenticationUtil.INSTANCE.createToken(username);
            UserDataBaseHelper.INSTANCE.updateToken(username,token);
            ((ObjectNode) jsonBody).put("result",result);
            ((ObjectNode) jsonBody).put("access_token",token);
            ((ObjectNode) jsonBody).put("token_type","Bearer");
            ((ObjectNode) jsonBody).put("expires_in",86400);

             response.status(200);
        }
        else{
            ((ObjectNode) jsonBody).put("result",result);
            response.status(404);
        }

        response.body(jsonBody.toString());

        return response;

    });

    put("/User",(request, response) -> {
        //TODO Create a user only if the request comes from an admin
        UserDataBaseHelper.INSTANCE.createUser();
        response.status(200);
        return response;
    });

	post("/Metadata",(request, response)->{

        //convert the request.body() into a List of the received object
        List list = deserializeList(request.body(), MobilePrivacyProfilerDB_metadata.class, response);

        //store the objects into the DB
        String stringResponse = storeData(list, MobilePrivacyProfilerDB_metadata.class, MobilePrivacyProfilerDBHelper.INSTANCE.mobilePrivacyProfilerDB_metadataDao, response);
        return stringResponse;
    });

	post("/ApplicationHistory",(request, response)->{
        Class type = ApplicationHistory.class;
        //convert the request.body() into a List of the received object
        List<ApplicationHistory> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.applicationHistoryDao,response);
        return stringResponse;
    });

	post("/ApplicationUsageStats",(request, response)->{
        Class type = ApplicationUsageStats.class;
        //convert the request.body() into a List of the received object
        List<ApplicationUsageStats> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.applicationUsageStatsDao,response);
        return stringResponse;
    });

	post("/Authentification",(request, response)->{
        Class type = Authentification.class;
        //convert the request.body() into a List of the received object
        List<Authentification> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.authentificationDao,response);
        return stringResponse;
    });

	post("/Contact",(request, response)->{
        Class type = Contact.class;
        //convert the request.body() into a List of the received object
        List<Contact> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.contactDao,response);
        return stringResponse;
    });

	post("/ContactOrganisation",(request, response)->{
        Class type = ContactOrganisation.class;
        //convert the request.body() into a List of the received object
        List<ContactOrganisation> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.contactOrganisationDao,response);
        return stringResponse;
    });

	post("/ContactIM",(request, response)->{
        Class type = ContactIM.class;
        //convert the request.body() into a List of the received object
        List<ContactIM> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.contactIMDao,response);
        return stringResponse;
    });

	post("/ContactEvent",(request, response)->{
        Class type = ContactEvent.class;
        //convert the request.body() into a List of the received object
        List<ContactEvent> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.contactEventDao,response);
        return stringResponse;
    });

	post("/ContactPhoneNumber",(request, response)->{
        Class type = ContactPhoneNumber.class;
        //convert the request.body() into a List of the received object
        List<ContactPhoneNumber> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.contactPhoneNumberDao,response);
        return stringResponse;
    });

	post("/ContactPhysicalAddress",(request, response)->{
        Class type = ContactPhysicalAddress.class;
        //convert the request.body() into a List of the received object
        List<ContactPhysicalAddress> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.contactPhysicalAddressDao,response);
        return stringResponse;
    });

	post("/ContactEmail",(request, response)->{
        Class type = ContactEmail.class;
        //convert the request.body() into a List of the received object
        List<ContactEmail> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.contactEmailDao,response);
        return stringResponse;
    });

	post("/KnownWifi",(request, response)->{
        Class type = KnownWifi.class;
        //convert the request.body() into a List of the received object
        List<KnownWifi> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.knownWifiDao,response);
        return stringResponse;
    });

	post("/LogsWifi",(request, response)->{
        Class type = LogsWifi.class;
        //convert the request.body() into a List of the received object
        List<LogsWifi> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.logsWifiDao,response);
        return stringResponse;
    });

	post("/Geolocation",(request, response)->{
        Class type = Geolocation.class;
        //convert the request.body() into a List of the received object
        List<Geolocation> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.geolocationDao,response);
        return stringResponse;
    });

	post("/CalendarEvent",(request, response)->{
        Class type = CalendarEvent.class;
        //convert the request.body() into a List of the received object
        List<CalendarEvent> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.calendarEventDao,response);
        return stringResponse;
    });

	post("/PhoneCallLog",(request, response)->{
        Class type = PhoneCallLog.class;
        //convert the request.body() into a List of the received object
        List<PhoneCallLog> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.phoneCallLogDao,response);
        return stringResponse;
    });

	post("/Cell",(request, response)->{
        Class type = Cell.class;
        //convert the request.body() into a List of the received object
        List<Cell> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.cellDao,response);
        return stringResponse;
    });

	post("/OtherCellData",(request, response)->{
        Class type = OtherCellData.class;
        //convert the request.body() into a List of the received object
        List<OtherCellData> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.otherCellDataDao,response);
        return stringResponse;
    });

	post("/CdmaCellData",(request, response)->{
        Class type = CdmaCellData.class;
        //convert the request.body() into a List of the received object
        List<CdmaCellData> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.cdmaCellDataDao,response);
        return stringResponse;
    });

	post("/NeighboringCellHistory",(request, response)->{
        Class type = NeighboringCellHistory.class;
        //convert the request.body() into a List of the received object
        List<NeighboringCellHistory> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.neighboringCellHistoryDao,response);
        return stringResponse;
    });

	post("/BluetoothDevice",(request, response)->{
        Class type = BluetoothDevice.class;
        //convert the request.body() into a List of the received object
        List<BluetoothDevice> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.bluetoothDeviceDao,response);
        return stringResponse;
    });

	post("/BluetoothLog",(request, response)->{
        Class type = BluetoothLog.class;
        //convert the request.body() into a List of the received object
        List<BluetoothLog> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.bluetoothLogDao,response);
        return stringResponse;
    });

	post("/SMS",(request, response)->{
        Class type = SMS.class;
        //convert the request.body() into a List of the received object
        List<SMS> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.sMSDao,response);
        return stringResponse;
    });

	post("/BatteryUsage",(request, response)->{
        Class type = BatteryUsage.class;
        //convert the request.body() into a List of the received object
        List<BatteryUsage> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.batteryUsageDao,response);
        return stringResponse;
    });

	post("/NetActivity",(request, response)->{
        Class type = NetActivity.class;
        //convert the request.body() into a List of the received object
        List<NetActivity> list = deserializeList(request.body(),type,response);

        //store the objects into the DB
        String stringResponse = storeData(list,type,MobilePrivacyProfilerDBHelper.INSTANCE.netActivityDao,response);
        return stringResponse;
    });

    }//end main

    private static String storeData(List listDbObject, Class type,Dao dao, Response response) {
        Iterator iteratorDbObject =listDbObject.iterator();
            try {
                while (iteratorDbObject.hasNext()) {
                    Object object = iteratorDbObject.next();
                    boolean redundant = MobilePrivacyProfilerDBHelper.INSTANCE.isRegisteredDbClassObject(DbClass.class.cast(object),type,dao);
                    log.info("----> Add "+type.getSimpleName()+" : redundant? : " + redundant);
                    if (!redundant) {
                        dao.create(type.cast(object));
                    }else{
                        //update it in the DB
                        MobilePrivacyProfilerDBHelper.INSTANCE.updateObjectFromDB(DbClass.class.cast(object),type,dao);
                    }
                }// end while
            } catch (SQLException e) {  e.printStackTrace();
                                        response.status(400);
                                        return "Internal error on data storing";
            } catch (IllegalAccessException e) { e.printStackTrace();
            } catch (InstantiationException e) { e.printStackTrace();
            }

        response.status(201);
            return "Request processed : "+type.getSimpleName();

    }//end method

    /**
     * Use Jackson to deserialize a String into List<deserialisationClassObject>
     * @param jsonArg
     * @param deserialisationClass
     * @return a List<deserialisationClassObject>
     */

    private static List deserializeList (String jsonArg, Class deserialisationClass, Response response) {
        //convert the body into a List<jsonString>
        List<String> list =null;
        List outPutList = new ArrayList();
        if(null != jsonArg && "" != jsonArg ) {
            try {
                list = parser(jsonArg);
            } catch (Exception e) {
                e.printStackTrace();
                response.status(400);
            }
            //convert List<jsonString> into List<deserialisationClass>

            for (String json : list) {
                outPutList.add(deserialisationClass.cast(deserialize(json, deserialisationClass, response)));
            }
        }
        return outPutList;
    }

    /**
     * Use Jackson to deserialize a single object of deserialisationClass Type
     * @param jsonArg
     * @param deserialisationClass
     * @return a single Object you need to cast into deserialisationClass Type
     */

    private static Object deserialize (String jsonArg, Class deserialisationClass, Response response) {
        // Convert JSON string to single Object
        ObjectMapper mapper = new ObjectMapper();
        Object deserializedObject = null;
        if(null != jsonArg && "" != jsonArg ) {
            try {
                deserializedObject = mapper.readValue(jsonArg, deserialisationClass);
            } catch (IOException e) {
                e.printStackTrace();
                response.status(400);
                return "Internal error on Json parse";
            }
        }
        log.debug("Deserialized json: " + deserializedObject.toString());

        return deserializedObject;
    }

    /**
     * take in a single json String to parse it into a List<String>
     * @param json
     * @return a List<String>
     */
    public static List<String> parser(String json){

        int length;
        int parseCounter;
        int hookCount=0;
        int parenthesisCount=0;

        length = json.length();
        List<String> result = new ArrayList<>();

        StringBuffer stringBuffer = new StringBuffer();
        for(parseCounter = 0;parseCounter<length;parseCounter++) {
            char testedCar = json.charAt(parseCounter);

            switch(testedCar) {
                case '['    :
                    if(0!=hookCount){
                        stringBuffer.append(testedCar);
                    }
                    hookCount++;
                    break;

                case '{'    :
                    parenthesisCount++;
                    stringBuffer.append(testedCar);
                    break;

                case '}'    :
                    parenthesisCount--;
                    stringBuffer.append(testedCar);
                    break;

                case ']'    :
                    hookCount--;
                    if(0==hookCount){
                        result.add(stringBuffer.toString());
                        stringBuffer= new StringBuffer();
                    }
                    else{
                        stringBuffer.append(testedCar);
                    }
                    break;

                case ','    :
                    if(0==parenthesisCount){
                        result.add(stringBuffer.toString());
                        stringBuffer= new StringBuffer();
                    }else{ stringBuffer.append(testedCar);}
                    break;

                case ' '    :
                    if(0!=parenthesisCount){
                        stringBuffer.append(testedCar);
                    }
                    break;

                default     :       stringBuffer.append(testedCar);
            }//end switch

        }//end for

        return result;
    }


    public static JsonNode parseBody(String body) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(body);
    }

}//end class
