package edu.si.trellis.cassandra;

import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.collect.ImmutableSet;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.tamaya.inject.api.Config;
import org.slf4j.Logger;
import org.trellisldp.http.AgentAuthorizationFilter;
import org.trellisldp.http.CacheControlFilter;
import org.trellisldp.http.LdpResource;

/**
 * Basic JAX-RS {@link Application} to deploy Trellis with a Cassandra persistence implementation.
 *
 */
@ApplicationPath("/*")
@ApplicationScoped
public class CassandraApplication extends Application {

    private static final Logger log = getLogger(CassandraApplication.class);

    @Inject
    private InjectedServiceBundler services;
 
    //@Inject
    //@Config
    private List<String> adminUsers = Collections.emptyList();

    @Inject
    private InjectedCacheControlFilter cacheControlFilter;

    @Override
    public Set<Object> getSingletons() {
        final AgentAuthorizationFilter agentFilter = new AgentAuthorizationFilter(services.getAgentService());
        agentFilter.setAdminUsers(adminUsers);

        return ImmutableSet.of(new LdpResource(services), agentFilter, cacheControlFilter);
    }
}
