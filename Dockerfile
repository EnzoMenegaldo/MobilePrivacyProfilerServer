FROM java
ADD classes/artifacts/mobileprivacyprofiler_data_server_main_jar/mobileprivacyprofiler-data-server_main.jar /opt/main.jar
ADD classes/artifacts/mobileprivacyprofiler_data_server_main_jar/data /opt/data
CMD java -jar /opt/main.jar
