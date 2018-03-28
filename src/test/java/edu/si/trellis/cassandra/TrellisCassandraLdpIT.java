package edu.si.trellis.cassandra;

import static org.glassfish.jersey.client.ClientProperties.CONNECT_TIMEOUT;
import static org.glassfish.jersey.client.ClientProperties.READ_TIMEOUT;

import java.io.File;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.DropwizardTestSupport;
import javax.ws.rs.client.Client;

import org.trellisldp.app.TrellisApplication;
import org.trellisldp.app.config.TrellisConfiguration;
import org.trellisldp.test.AbstractApplicationLdpTests;


public class TrellisCassandraLdpIT extends AbstractApplicationLdpTests {

    private static final String BUILD_DIR = System.getProperty("project.build.directory", "");

    private static final DropwizardTestSupport<TrellisConfiguration> APP = new DropwizardTestSupport<TrellisConfiguration>(
                    TrellisApplication.class, BUILD_DIR + "/test-classes/trellis-config.yml");

    // @BeforeAll
    // public static void initialize() throws Exception {
    //     APP.before();
    //
    //     setUp();
    // }

    public String getBaseURL() {
        return "http://localhost:" + APP.getLocalPort() + "/";
    }

    public Client getClient() {
        return new JerseyClientBuilder(APP.getEnvironment()).build("test client");
    }

    //@Override
    protected String getResourcePath(String path) {
        return new File(BUILD_DIR + "/test-classes/" + path).getAbsolutePath();
    }

    // @AfterAll
    // public static void cleanup() throws Exception {
    //     APP.after();
    //     tearDown();
    // }
}
