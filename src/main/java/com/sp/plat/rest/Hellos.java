package com.sp.plat.rest;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Controller
@Path("/he")
public class Hellos {
		@Path("/wo")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public String sayHello(@DefaultValue("Just a test!") @QueryParam("str") String str,@Context HttpServletResponse response) {
			System.out.println(str);
	        return "{\"功能\":[\"JSON美化\",\"JSON数据类型显示\",\"JSON数组显示角标\",\"高亮显示\",\"错误提示\"]}";
	    }

		@GET
		@Path("/{param}")
		@Produces(MediaType.APPLICATION_JSON)
		public String HelloToUTF8(@PathParam("param") String username) {
			return "Hello " + username+"大王叫我来巡山";
		}
		@GET
		@Path("/{param}/{aaaaa}")
		@Produces(MediaType.APPLICATION_JSON)
		public String sayHelloToUTF8(@PathParam("param") String username,@PathParam("aaaaa") String aaa) {
			return "Hello " + username+"大王叫我来巡山"+aaa;
		}
}
