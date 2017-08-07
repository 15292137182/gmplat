#!/bin/bash
#defined
#TOMCAT_HOME="/opt/webapp/tomcat/apache-tomcat-9.0.0.M22"
TOMCAT_HOME="/opt/webapp/tomcat/apache-tomcat-9.0.0.M22"
TOMCAT_PORT=80
PROJECT="gmplat"
WAR_HOME="/apps/jenkins/war"


cd $WAR_HOME/
mv *.war $PROJECT.war

#shutdown tomcat
"$TOMCAT_HOME"/bin/shutdown.sh &

#check tomcat process
sleep 3
tomcat_pid=`ps -ef|grep apache-tomcat-9.0.0.M22|grep start|grep -v 'grep'|awk '{print $2}'`

while [ -n "$tomcat_pid" ]
do
 kill -9 $tomcat_pid
 sleep 3
 tomcat_pid=`ps -ef|grep apache-tomcat-9.0.0.M22|grep start|grep -v 'grep'|awk '{print $2}'`
 echo "scan tomcat pid:" $tomcat_pid
done

#bak project
echo "scan no tomcat pid.$PROJECT publishing"
tar -czf $WAR_HOME/bak/$PROJECT-bak`date +%y%m%d%H%M`.tar.gz $TOMCAT_HOME/webapps/$PROJECT
sleep 10

rm -rf $TOMCAT_HOME/webapps/$PROJECT $TOMCAT_HOME/webapps/$PROJECT.war

#publish project
cp $WAR_HOME/$PROJECT.war $TOMCAT_HOME/webapps/$PROJECT.war

#remove tmp
rm -rf $WAR_HOME/$PROJECT*.war

#start tomcat
$TOMCAT_HOME/bin/startup.sh &
echo "tomcat is starting,please try to access $PROJECT"