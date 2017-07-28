package com.sp.plat.utility.restServiceConfig;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Controller
@Path("/hello")
public class HelloResource {
		@Path("/hh")
	    @GET
	    @Produces(MediaType.TEXT_PLAIN)
		@Consumes(MediaType.MEDIA_TYPE_WILDCARD)
	    public String sayHello(@QueryParam("str") String string,@Context HttpServletResponse response) {
			response.setCharacterEncoding("UTF-8");
	        System.out.println(string);
			return "张三:123,李四:456";
	    }
	}
