package edu.si.trellis.cassandra;

import static org.glassfish.jersey.client.ClientProperties.CONNECT_TIMEOUT;
import static org.glassfish.jersey.client.ClientProperties.READ_TIMEOUT;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.DropwizardTestSupport;

import javax.ws.rs.client.Client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.trellisldp.app.TrellisApplication;
import org.trellisldp.app.config.TrellisConfiguration;
import org.trellisldp.test.AbstractApplicationLdpTests;


@RunWith(JUnitPlatform.class)
public class TrellisCassandraLdpIT extends AbstractApplicationLdpTests {

    private static final String BUILD_DIR = System.getProperty("project.build.directory", "");

    private static final DropwizardTestSupport<TrellisConfiguration> APP = new DropwizardTestSupport<TrellisConfiguration>(
                    TrellisApplication.class, BUILD_DIR + "/test-classes/trellis-config.yml");

    private static Client CLIENT;

    @Override
    public Boolean supportWebAnnotationProfile() {
        return false;
    }

    @BeforeAll
    public static void initialize() throws Exception {
        APP.before();
        CLIENT = new JerseyClientBuilder(APP.getEnvironment()).build("test client");
        CLIENT.property(CONNECT_TIMEOUT, 5000);
        CLIENT.property(READ_TIMEOUT, 5000);
    }

    @AfterAll
    public static void cleanup() throws Exception {
        APP.after();
    }

    @Override
    public Client getClient() {
        return CLIENT;
    }

    @Override
    public String getBaseURL() {
        return "http://localhost:" + APP.getLocalPort() + "/";
    }
}
