package edu.si.trellis.cassandra;

import static com.datastax.driver.core.TypeCodec.bigint;
import static edu.si.trellis.cassandra.DatasetCodec.datasetCodec;
import static edu.si.trellis.cassandra.IRICodec.iriCodec;
import static org.apache.tamaya.ConfigurationProvider.createConfiguration;
import static org.apache.tamaya.ConfigurationProvider.getConfiguration;
import static org.apache.tamaya.ConfigurationProvider.setConfiguration;
import static org.apache.tamaya.format.ConfigurationFormats.createPropertySource;
import static org.slf4j.LoggerFactory.getLogger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.Session;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import org.apache.tamaya.Configuration;
import org.apache.tamaya.ConfigurationProvider;
import org.apache.tamaya.cdi.TamayaCDIInjectionExtension;
import org.apache.tamaya.format.ConfigurationFormats;
import org.apache.tamaya.inject.api.Config;
import org.apache.tamaya.spi.ConfigurationContext;
import org.apache.tamaya.spi.PropertySource;
import org.apache.tamaya.yaml.YAMLFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CassandraSession implements Closeable {

    private Cluster cluster;

    private Session session;

    //@Inject
    //TamayaCDIInjectionExtension j;

    private static final Logger log = getLogger(CassandraSession.class);

    static {

        // try {
        // URL configResource = CassandraSession.class.getClassLoader().getResource("trellis-config.yml");
        // PropertySource config = createPropertySource(configResource, new YAMLFormat());
        // ConfigurationContext context = getConfiguration().getContext();
        // context = context.toBuilder().addPropertySources(config).build();
        // setConfiguration(createConfiguration(context));
        // log.debug("Installed YAML configuration to Tamaya.");
        // } catch (IOException e) {
        // throw new UncheckedIOException(e);
        // }
    }

    public CassandraSession() {
        //log.debug("Found TamayaCDIInjectionExtension: {}", j);
        //log.debug("System properties:");
        //System.getProperties().forEach((k,v)->log.debug("{} -> {}", k, v));
        String cassandraAddress = "localhost";
        int port = Integer.getInteger("cassandra.nativeTransportPort");
        log.info("Using cassandra node address: {} and port: {}", cassandraAddress, port);
        
        this.cluster = Cluster.builder().withoutJMXReporting().withoutMetrics().addContactPoint(cassandraAddress)
                        .withPort(port).build();
        QueryLogger queryLogger = QueryLogger.builder().build();
        this.cluster.register(queryLogger);
        this.cluster.getConfiguration().getCodecRegistry().register(iriCodec, datasetCodec, bigint(), InstantCodec.instance);
        this.session = cluster.connect("Trellis");
    }

    /**
     * @return a {@link Session} for use with {@link CassandraResourceService} (and {@link CassandraBinaryService})
     */
    @Produces
    @ApplicationScoped
    public Session getSession() {
        return session;
    }

    @PreDestroy
    @Override
    public void close() {
        session.close();
        cluster.close();
    }
}
