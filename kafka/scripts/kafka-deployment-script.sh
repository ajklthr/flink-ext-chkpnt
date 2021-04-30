mkdir /home/shared/kafka
cd /home/shared/kafka
curl https://downloads.apache.org/kafka/2.8.0/kafka_2.12-2.8.0.tgz -o kafka.tgz
tar -xvzf kafka.tgz --strip 1
rm kafka.tgz
curl -s -H "Accept:application/vnd.github.v3.raw" https://api.github.com/repos/rbala19/flink-ext-chkpnt/contents/kafka/scripts/server.properties > config/server.properties
curl -s -H "Accept:application/vnd.github.v3.raw" https://api.github.com/repos/rbala19/flink-ext-chkpnt/contents/kafka/scripts/zookeeper.properties > config/zookeeper.properties
cd /etc/systemd/system/
curl -s -H "Accept:application/vnd.github.v3.raw" https://api.github.com/repos/rbala19/flink-ext-chkpnt/contents/kafka/scripts/kafka.service > kafka.service 
curl -s -H "Accept:application/vnd.github.v3.raw" https://api.github.com/repos/rbala19/flink-ext-chkpnt/contents/kafka/scripts/zookeeper.service > zookeeper.service
cd /home/shared/kafka
systemctl enable zookeeper 
systemctl start zookeeper 
echo Setup is almost complete - set a unique broker id within server.properties and run the following command to start kafka: \ kafka/bin/kafka-server-start.sh kafka/config/server.properties
