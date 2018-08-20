Server Part of the project : Mobile Privacy Profiler
==============
The server gathers all the data sent by all the mobiles running the associated android application.
It manages the user authentication as well when the user starts the mobile application.

Accessing server :
--------------

The server is running on the machine fox2 ( 10.0.0.3 ) listening on the port 4567. However, fox2 is not accessible from outside.
To access it, the mobile applications send their requests to the machine 10.0.0.2 ( the only one accessible in Diverse Team ).

We use NGinx to redirect the requests  from 10.0.0.2 to 10.0.0.3. Thus, we only work on fox2.
The purpose is to keep safe what's running on 10.0.0.2. 

The server's url is https://userprivacydataserver.diverse-team.fr/

Starting server :
--------------
The server runs in a docker container.

To run the container, use the following command on fox2 : docker run -t -i --mount source=profile_volume,target=/opt -w /opt -p 9001:4567 profile/mobileprivacyprofiler_server

To deploy it : replace -t -i by -d

Known ubuntu issue

We have to change the dns server associated to the docker because it is not able to access the default one. 

Otherwise, the server won't be able to send email to the users within their credentials. 

**Solution 1** :

Before running the container, you have to create a file daemon.json in /etc/docker/ if it does not exist and add 

{
    "dns": ["131.254.130.100", "8.8.8.8"]
}

Then you must restart the docker service using the command : sudo service docker restart

Source :
https://stackoverflow.com/questions/24151129/docker-network-calls-fail-during-image-build-on-corporate-network

**Solution 2** :

If it's not possible to restart the docker service, run the following command :

docker run -t -i --dns=131.254.130.100 --mount source=profile_volume,target=/opt -w /opt -p 9001:4567 profile/mobileprivacyprofiler_server

Database :
--------------

The server manages two databases. 
One containing the users and their credentials. 
A second to store the mobile data.

**Mobile User :**

To use the mobile application, a user must be registered in the User Database managed by the server. For now, when the server is started, it will create credentials for all the email addresses written in the file email.txt and then it will send them by email.

If a new user must be registered, the following request must be sent to the server using the admin's credentials:

curl -X POST -H 'Content-Type: application/json' -d {"username":"admin","password":"thepassword","email":"new_user_email"}' https://userprivacydataserver.diverse-team.fr/User

**Data collection and storage :**

The server uses Spark ( http://sparkjava.com/ ) to handle the data collection.

It uses OrmLite ( http://ormlite.com/ ) to manage the SQL databases. 

