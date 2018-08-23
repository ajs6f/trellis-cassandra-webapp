package edu.si.trellis.cassandra;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.enterprise.inject.spi.CDI;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

public class BasicOperationIT {

    private static final Logger log = getLogger(BasicOperationIT.class);

    private static final CloseableHttpClient client = HttpClients.createMinimal();

    private static final Integer port = Integer.parseInt(System.getProperty("trellis.port"));

    private static final String trellisUri = "http://localhost:" + port + "/trellis-cassandra-app/";

    @Test
    public void test() throws MalformedURLException, IOException {
        String id = trellisUri + "example";
        HttpPut req = new HttpPut(id);
        req.setHeader("Content-Type", "text/turtle");
        req.setEntity(new StringEntity("<> a <http://example.com/example> ."));
        try (CloseableHttpResponse res = client.execute(req);
                        InputStream url = res.getEntity().getContent()) {
            // println();
            // res.getEntity().writeTo(System.out);
            // println();
            // waitformax();
            assertEquals(SC_CREATED, res.getStatusLine().getStatusCode());
        }
        HttpGet get = new HttpGet(id);
        try (CloseableHttpResponse res = client.execute(get);
                        InputStream url = res.getEntity().getContent()) {
            assertEquals(SC_OK, res.getStatusLine().getStatusCode());
            assertTrue(EntityUtils.toString(res.getEntity()).contains("http://example.com/example"));
        }

    }

    private void waitformax() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void println() {
        System.out.println("-------------");
    }

}
