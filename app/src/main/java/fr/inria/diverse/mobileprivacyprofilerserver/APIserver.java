package fr.inria.diverse.mobileprivacyprofilerserver;


import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonView;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import fr.inria.diverse.mobileprivacyprofilerserver.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofilerserver.datamodel.DBConstants;
import fr.inria.diverse.mobileprivacyprofilerserver.datamodel.DBTools;
import fr.inria.diverse.mobileprivacyprofilerserver.datamodel.MobilePrivacyProfilerDBHelper;
import com.j256.ormlite.logger.Log4jLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.http.HttpField;
import org.sqlite.JDBC;
import spark.Response;

import javax.servlet.Filter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;


public class APIserver {
    private static MobilePrivacyProfilerDBHelper  dbHelper;
    // Initialising log management
    private static Log log = LogFactory.getLog(Test.class);

    public static void main(String[] args) {


    get("/hello", (request, response) -> "Hello World");

    post("/ApplicationHistory",(request, response)->{

        //convert the request.body() into a List<ApplicationHistory>
        List<ApplicationHistory> applicationHistories = deserializeList(request.body(),ApplicationHistory.class,response);

        //flush the objects into the DB
        try {
            for(ApplicationHistory appHist : applicationHistories){
                boolean redundant = getDBHelper().isRegistredApplicationHistory(appHist);
                log.info("----> Add ApplicationHistory : redundant? : "+redundant);
                if(!redundant){ getDBHelper().applicationHistoryDao.create(appHist);}
            }
        } catch (SQLException e) {
            e.printStackTrace();response.status(400);return "Internal error on data storing";
        }

        response.status(201);
    return "Request processed";
    });

    //TODO Add a method to handle others tables

    }//end main


    /**
     * Use Jackson to deserialize a String into List<deserialisationClassObject>
     * @param jsonArg
     * @param deserialisationClass
     * @return a List<deserialisationClassObject>
     */

    private static List deserializeList (String jsonArg, Class deserialisationClass, Response response) {
        //convert the body into a List<jsonString>
        List<String> list =null;

        try {
            list= parser(jsonArg);
        } catch (Exception e) {
            e.printStackTrace(); response.status(400);
        }
        //convert List<jsonString> into List<ApplicationHistory>
        List outPutList = new ArrayList();

        for (String json:list){
            outPutList.add(deserialisationClass.cast(deserialize(json,deserialisationClass,response)));
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
        try {
            deserializedObject = mapper.readValue(jsonArg, deserialisationClass);
        } catch (IOException e) {
            e.printStackTrace();response.status(400);return "Internal error on Json parse";
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

    private static MobilePrivacyProfilerDBHelper getDBHelper(){
        if(dbHelper == null){
            // - - - Data Base - - -
            DBTools dBTools = new DBTools();
            try {
                File file = new File ("./run/database/MobilePrivacyProfilerDB.db");
                log.info("Does \"./run/database/MobilePrivacyProfilerDB.db\" exist :"+file.exists());

                JdbcConnectionSource connectionSource = null;
                connectionSource = new JdbcConnectionSource(DBConstants.DATABASE_URL);

                if(!file.exists()){
                    log.info("Start database initialisation");
                    dBTools.initializeSQLite(DBConstants.DATABASE_URL);
                    dBTools.databaseInitialisation(connectionSource);
                }


                dbHelper = dBTools.setupDatabase(connectionSource);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dbHelper;
    }


}//end APIserver
