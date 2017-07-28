package com.sp.plat.utility.restServiceConfig;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class RestServiceConfig extends ResourceConfig{

	public RestServiceConfig(){
		packages("com.sp.plat.rest");
        register(RequestContextFilter.class);
		register(JacksonFeature.class);
		register(MultiPartFeature.class);
	}
}
