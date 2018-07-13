package fr.inria.diverse.mobileprivacyprofilerserver;

import fr.inria.diverse.mobileprivacyprofilerserver.datamodel.MobilePrivacyProfilerDBHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Test0 {


    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = "2015-07-16";
        try {

            Date date = formatter.parse(dateInString);

            System.out.println(date.getTime());
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }//end main
}//end class
