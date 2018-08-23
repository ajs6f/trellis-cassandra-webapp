package edu.si.trellis.cassandra;

import com.google.common.collect.ImmutableSet;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.trellisldp.http.AgentAuthorizationFilter;
import org.trellisldp.http.CacheControlFilter;
import org.trellisldp.http.LdpResource;

@ApplicationPath("/*")
public class CassandraApplication extends Application {

    @Inject
    private InjectedServiceBundler services;

    @Override
    public Set<Object> getSingletons() {
        final AgentAuthorizationFilter agentFilter = new AgentAuthorizationFilter(services.getAgentService());
        agentFilter.setAdminUsers(Collections.emptyList());

        return ImmutableSet.of(new AllExceptionMapper(), new LdpResource(services), agentFilter,
                        new CacheControlFilter(Integer.MAX_VALUE, true, true));
    }

}
