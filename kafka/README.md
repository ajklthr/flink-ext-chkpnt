# Kafka

## Accessing the Cluster 

Node ips are accessible at the following locations: 
```
172.22.158.225
172.22.94.225
172.22.156.228
172.22.158.226
172.22.94.226 
```

##Deployment Script

If a node is wiped, we must reinstall kafka. 

Run the `kafka-deployment-script.sh` to download and install kafka. Then follow the instruction within the script printout to start the kafka server. 

##Deployment instruction for live ndoe 

When the node is turned on, it will automatically start zookeeper. Run `kafka/bin/kafka-server-start.sh kafka/config/server.properties` to start kafka on the node. 
If an error about invalid logs occurs, run `rm -rf /tmp/zookeeper` and `rm -rf /tmp/kafka-logs`. Then rerun the kafka server command. 




