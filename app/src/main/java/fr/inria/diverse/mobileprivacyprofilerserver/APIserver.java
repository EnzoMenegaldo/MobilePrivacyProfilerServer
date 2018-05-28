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
    // Initialisation de la Gestion des Log
    private static Log log = LogFactory.getLog(Test.class);

    public static void main(String[] args) {


    get("/hello", (request, response) -> "Hello World");

    post("/ApplicationHistory",(request, response)->{

    String requestBody = request.body();

        ObjectMapper mapper = new ObjectMapper();
        List<ApplicationHistory> applicationHistories = null;
        try {
            applicationHistories = mapper.readValue(requestBody, mapper.getTypeFactory().constructCollectionType(List.class, ApplicationHistory.class));
        } catch (IOException e) {
            e.printStackTrace(); response.status(400);return "Internal error on Json parse";
        }

        try {
            for(ApplicationHistory appHist : applicationHistories){
                boolean redundant = getDBHelper().isRegistredApplicationHistory(appHist);
                log.info("----> Add ApplicationHistory : redundant? :"+redundant);
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
