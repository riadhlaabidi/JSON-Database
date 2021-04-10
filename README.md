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

then run the client with the request args

```shell
java -jar target/client-jar-with-dependencies.jar -t set -k "some key" -v "some value"
```

or with a file containing the request as json, for example a file `set.json` 
has the following content:

```json
{
  "type": "set",
  "key": "some key",
  "value": "some value"
}
```

```shell
java -jar target/client-jar-with-dependencies.jar -in set.json
```

the output should be something like

```json
Sent: {
  "type": "set",
  "key": "some key",
  "value": "some value"
}
Received: {
  "response": "OK"
}
```

### Usage

|option             |description                             |
|:------------------|:---------------------------------------|
| -t, --type        | Type of the request (set, get, delete) |
| -k, --key         | Record key                             |
| -v, --value       | Value to add                           |
| -in, --input-file | File containing the request as json    |
