package edu.si.trellis.cassandra;

import static com.datastax.driver.core.TypeCodec.bigint;
import static edu.si.trellis.cassandra.DatasetCodec.datasetCodec;
import static edu.si.trellis.cassandra.IRICodec.iriCodec;

import org.trellisldp.api.EventService;
import org.trellisldp.api.IdentifierService;
import org.trellisldp.api.MementoService;
import org.trellisldp.api.NamespaceService;
import org.trellisldp.api.ResourceService;
import org.trellisldp.app.TrellisApplication;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;

import java.util.Map;

public class TrellisCassandraApplication extends TrellisApplication {

    @Override
    protected <T extends ResourceService> T buildResourceService(IdentifierService idService,
                    MementoService mementoService, EventService notificationService) {
        Map<String, Object> extraConfig = config.any();
        String cassandraAddress = (String) extraConfig.get("cassandraAddress");
        Integer cassandraPort = (Integer) extraConfig.get("cassandraPort");
        Cluster cluster = Cluster.builder().withoutJMXReporting().withoutMetrics().addContactPoint(cassandraAddress)
                        .withPort(cassandraPort).build();
        cluster.getConfiguration().getCodecRegistry().register(iriCodec, datasetCodec, bigint(), InstantCodec.instance);
        Session session = cluster.connect("Trellis");
        return (T) new CassandraResourceService(session);
    }

    @Override
    protected NamespaceService buildNamespaceService() {
        return new NoopNamespaceService();
    }

    /**
     * The main entry point.
     * 
     * @param args the argument list
     * @throws Exception if something goes horribly awry
     */
    public static void main(final String[] args) throws Exception {
        new TrellisCassandraApplication().run(args);
    }

}
