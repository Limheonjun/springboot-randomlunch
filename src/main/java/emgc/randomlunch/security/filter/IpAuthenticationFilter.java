package emgc.randomlunch.security.filter;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import emgc.randomlunch.util.HttpReqResUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import java.net.InetAddress;

@Slf4j
public class IpAuthenticationFilter implements Filter {

    private DatabaseReader databaseReader;

    public IpAuthenticationFilter(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ipAddress = HttpReqResUtils.getClientIpAddressIfServletRequestExist();
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        String country = null;
        try {
            country = databaseReader.country(inetAddress).getCountry().getName();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }
        if(country == null || !country.equals("South Korea")){
            log.info("Access Rejected : {}, {}", ipAddress, country);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("IP Authentication Filter Init..");
    }

    @Override
    public void destroy() {
        log.info("IP Authentication Filter Destroy..");
    }
}
