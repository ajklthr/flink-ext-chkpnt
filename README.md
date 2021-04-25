# flink-ext-chkpnt

## Keyspace Notification

The Redis keyspace notification system uses a redis async event listener that runs a callback whenever a key is changed or added to the Redis database. 

### Building and Running

The easiest way to run the module is to compile, make, and run using CLion. All dependencies are local to the module so paths need not be changed within the makefile. Simply hit run within main.cpp to make and run the code pertaining to the keyspace listener. 

To enable keyspace notifications within the redis database, make sure a redis-server instance is active. Then, run the following command to enable notifications. 

```redis-cli config set notify-keyspace-events KEA```



