package edu.si.trellis.cassandra;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.DropwizardTestSupport;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.trellisldp.app.TrellisApplication;
import org.trellisldp.app.config.TrellisConfiguration;
import org.trellisldp.test.LdpTests;

public class TrellisCassandraLdpIT extends LdpTests {

    private static final String BUILD_DIR = System.getProperty("project.build.directory", "");

    private static final DropwizardTestSupport<TrellisConfiguration> APP = new DropwizardTestSupport<TrellisConfiguration>(
                    TrellisApplication.class, BUILD_DIR + "/test-classes/trellis-config.yml");

    @BeforeAll
    public static void initialize() throws Exception {
        APP.before();
        setClient(new JerseyClientBuilder(APP.getEnvironment()).build("test client"));
        setBaseUrl("http://localhost:" + APP.getLocalPort() + "/");

        setUp();
    }

    @Override
    protected String getResourcePath(String path) {
        return new File(BUILD_DIR + "/test-classes/" + path).getAbsolutePath();
    }

    @AfterAll
    public static void cleanup() throws Exception {
        APP.after();
        tearDown();
    }
}
