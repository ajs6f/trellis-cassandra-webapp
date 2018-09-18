package edu.si.trellis.cassandra;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.tamaya.inject.api.Config;
import org.trellisldp.http.CacheControlFilter;

public class InjectedCacheControlFilter {

    @Inject
    @Config
    private Integer cacheAge;

    @Inject
    @Config
    private Boolean revalidate;

    @Inject
    @Config
    private Boolean noCache;

    private CacheControlFilter cacheControlFilter;

    @PostConstruct
    public void initFilter() {
        this.cacheControlFilter = new CacheControlFilter(cacheAge, revalidate, noCache);
    }

    @Produces
    @ApplicationScoped
    public CacheControlFilter cacheControlFilter() {
        return cacheControlFilter;
    }
}
