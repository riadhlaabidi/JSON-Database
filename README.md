# JSON-Database
client-server application that allows clients to store data on the server in JSON format.

#### Stack and topics covered:
* JSON (Gson)
* Sockets
* Multithreading (threads, executors, synchronization, shared data, locks)
* Collections framework (list, set and map interfaces)
* Design patterns (command, singleton)
* Reading and writing files
* Input and output streams
* JCommander
* Maven 

### Build
to build the project 

```shell
mvn clean package
```

this should generate two jars in the `target` folder:
* `server-jar-with-dependencies.jar`
* `client-jar-with-dependencies.jar`

### Run
first run the server

```shell
java -jar target/server-jar-with-dependencies.jar
```

then run the client

```shell
java -jar target/client-jar-with-dependencies.jar -t set -k "some key" -v "some value"
```

the output should be something like

```json
Sent: {
  "type": "set",
  "key": "some key",
  "value": "some value"
}
Received: {
  "response": "OK",
}
```

### Usage
Options:
* -t, --type
    * Type of the request
* -k, --key 
    * Record key
* -v, --value
    * Value to add
* -in, --input-file
    * File containing the request as json