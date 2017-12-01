package com.zbd.app.http;

import android.app.Application;

import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;

import java.util.ArrayList;
import java.util.Arrays;

import static com.zbd.app.http.Config.COMMON_HOST;


/**
 * Created by fan-gk on 2017/4/24.
 */

public class DnsService {
    private static DnsService instance;


    public static void init(Application context) {
        instance = new DnsService(context);
    }

    public static DnsService getInstance() {
        return instance;
    }

    private final HttpDnsService httpDnsService;

    private DnsService(Application context) {
        httpDnsService = HttpDns.getService(context, "122066");
        httpDnsService.setPreResolveAfterNetworkChanged(true);
        httpDnsService.setExpiredIPEnabled(true);
        httpDnsService.setPreResolveHosts(new ArrayList(Arrays.asList(COMMON_HOST)));
    }

    public String getBaseUrl(String host) {
        String ip = httpDnsService.getIpByHostAsync(host);
        if (ip == null) {
            return Config.getBaseUrl(host);
        } else {
            return Config.getBaseUrl(ip);
        }
    }
}
