A TrellisLDP application using Trellis/Cassandra for persistence.

`mvn clean install` to build, see Maven profiles in `pom.xml` for packaging options.

To configure, add the location and port of a node in your Cassandra cluster to your Trellis configuration, e.g.

```
cassandraAddress: my.cassandra.node
cassandraPort: 9042
```

See [Trellis-Cassandra](https://github.com/ajs6f/trellis-cassandra) and [Trellis](https://github.com/trellis-ldp/trellis).
