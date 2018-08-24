package edu.si.trellis.cassandra;

import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.collect.ImmutableSet;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.trellisldp.http.AgentAuthorizationFilter;
import org.trellisldp.http.CacheControlFilter;
import org.trellisldp.http.LdpResource;

@ApplicationPath("/*")
public class CassandraApplication extends Application {

    private static final Logger log = getLogger(CassandraApplication.class);

    @Inject
    private InjectedServiceBundler services;

    @Override
    public Set<Object> getSingletons() {
        final AgentAuthorizationFilter agentFilter = new AgentAuthorizationFilter(services.getAgentService());
        agentFilter.setAdminUsers(Collections.emptyList());

        return ImmutableSet.of(new LdpResource(services), agentFilter,
                        new CacheControlFilter(Integer.MAX_VALUE, true, true));
    }
}
