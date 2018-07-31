FROM java
ADD classes/artifacts/mobileprivacyprofiler_data_server_main_jar/mobileprivacyprofiler-data-server_main.jar /opt/main.jar
ADD classes/artifacts/mobileprivacyprofiler_data_server_main_jar/database /opt/database
ADD classes/artifacts/mobileprivacyprofiler_data_server_main_jar/credentials /opt/credentials
CMD java -jar /opt/main.jar
