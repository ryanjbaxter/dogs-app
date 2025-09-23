# Spring Cloud Config Demo

1. Start `docker-compose.yaml`
2. Run `config-server/src/test/java/org/springframework/demo/TestConfigServerApplication.java`

`TestConfigServerApplication.java` starts a LocalStack Docker container.  It also adds the YAML files in `src/test/resources` to a bucket named `dogs`.  In addition is adds 2 secrets to Secrets Manager
at `/secrets/dog-app` and `/secrets/dogs-client`.

The `dog-app` and `dogs-client` both use the configuration from the config server.

1.  The property `dogs.show-medical-conditions` is a flag the `dog-app` uses to determine whether to only show dogs with medical conditions.
2.  The property `dogs.delete-token` is used by bothg the `dog-app` and `dogs-client` to allow the client to delete dogs from the database.
