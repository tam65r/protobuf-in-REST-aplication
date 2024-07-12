# Using Protocol Buffers (Protobuf) in REST Applications

## Context

Protobuf is a mechanism to serialize/deserialize data developed by Google. It is commonly used with Google Remote Procedure Call (gRPC), but it can also be used in REST applications.  

The objective of this project is to analyze the possible advantages of using Protobuf in REST applications instead of JSON.  
To analyze these possible advantages, a project that used REST with JSON was migrated to Protobuf. Using a test software, in this case JMeter, both projects were subjected to performance tests.


## Repository

This repository is composed of:

* Two projects:
  * **json-project** - the initial project.
  * **protobuf-project** - the migrated project that uses Protocol Buffers as the main data serialization format.
* The **docs** folder, which contains the main document [PESTA Report](docs/PESTA2023-24-1210924-TiagoRibeiro-ProtobufInRest-Relatorio.pdf).
* The **JMeter** folder, which has the *.jmx* file with JMeter test configurations.
* The **Data** folder, containing all test results.
* The **scripts** folder, which includes a Python script that makes a call to the REST API using Protocol Buffers for the body serialization.
