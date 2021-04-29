mkdir /home/shared/kafka & cd mkdir /home/shared/kafka
curl https://downloads.apache.org/kafka/2.8.0/kafka-2.8.0-src.tgz -o kafka.tgz
tar -xvzf kafka.tgz --strip 1
rm kafka.tgz
mkdir log
curl -s -H "Accept:application/vnd.github.v3.raw" https://api.github.com/repos/rbala19/flink-ext-chkpnt/contents/kafka/scripts/server.properties > config/server.properties
curl -s -H "Accept:application/vnd.github.v3.raw" https://api.github.com/repos/rbala19/flink-ext-chkpnt/contents/kafka/scripts/zookeeper.properties > config/zookeeper.properties
cd /etc/systemd/system/
curl -s -H "Accept:application/vnd.github.v3.raw" https://api.github.com/repos/rbala19/flink-ext-chkpnt/contents/kafka/scripts/kafka.service > kafka.service 
systemctl enable kafka
systemctl start kafka
