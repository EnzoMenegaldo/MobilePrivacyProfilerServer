package fr.inria.diverse.mobileprivacyprofilerserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.inria.diverse.mobileprivacyprofilerserver.datamodel.ApplicationHistory;
import fr.inria.diverse.mobileprivacyprofilerserver.datamodel.MobilePrivacyProfilerDBHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    private static MobilePrivacyProfilerDBHelper dbHelper;
    // Initialisation de la Gestion des Log
    public static Log log = LogFactory.getLog(Test.class);

    public static void main(String[] args) {



        ApplicationHistory a = new ApplicationHistory();
        a.setAppName("appName : a");
        a.setPackageName("packageName : a");
        a.set_id(1);
        ApplicationHistory b = new ApplicationHistory();
        b.setAppName("appName : b");
        b.setPackageName("packageName : b");
        b.set_id(2);
        ApplicationHistory c = new ApplicationHistory();
        c.setAppName("appName : c");
        c.setPackageName("packageName : c");
        c.set_id(3);

        List<ApplicationHistory> appHistToExport = new ArrayList<ApplicationHistory>();
        appHistToExport.add(a);
        appHistToExport.add(b);
        appHistToExport.add(c);

        //translation of the collection into Json
        ObjectMapper mapper = new ObjectMapper();
        String jsonList ="";
        try {
            jsonList = mapper.writeValueAsString(appHistToExport);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(jsonList);

        // Convert JSON string to single Object
        String jsonInString = "{\"_id\":1,\"appName\":\"appName : a\",\"packageName\":\"packageName : a\",\"usageStats\":null,\"contextDB\":null}";
        ApplicationHistory appHist = null;
        try {
            appHist = mapper.readValue(jsonInString, ApplicationHistory.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(appHist.toString());

        try {
            jsonList = mapper.writeValueAsString(appHist);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(jsonList);

        // Convert JSON string to a List of Object
        String jsonStringSample = "[{\"_id\":1,\"appName\":\"appName : a\",\"packageName\":\"packageName : a\",\"usageStats\":null,\"contextDB\":null},{\"_id\":2,\"appName\":\"appName : b\",\"packageName\":\"packageName : b\",\"usageStats\":null,\"contextDB\":null},{\"_id\":3,\"appName\":\"appName : c\",\"packageName\":\"packageName : c\",\"usageStats\":null,\"contextDB\":null}]";
        List<ApplicationHistory> appHistList = null;
        try {
            appHistList = mapper.readValue(jsonStringSample, mapper.getTypeFactory().constructCollectionType(List.class, ApplicationHistory.class));
            /*ApplicationHistory[] appHistTemp = mapper.readValue(jsonStringSample, ApplicationHistory[].class);
            appHistList = new ArrayList<>(Arrays.asList(appHistTemp));*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jsonList = mapper.writeValueAsString(appHistList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(jsonList);



    }//end main
}//end class
