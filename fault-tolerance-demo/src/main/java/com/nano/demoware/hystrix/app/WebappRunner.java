package com.nano.demoware.hystrix.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * This class starts Jetty and directly injects Spring's
 * {@link DispatcherServlet}.
 */
public class WebappRunner {
	
	private static Logger log = LoggerFactory.getLogger(WebappRunner.class);

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        new WebappRunner().startJetty(PORT);
    }

    private void startJetty(int port) throws Exception {
        Server server = new Server(port);

        // context in the Jetty sense...
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");

        // context in the Spring sense...
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfiguration.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        contextHandler.addServlet(servletHolder, "/*");
        contextHandler.addEventListener(new ContextLoaderListener(context));

        server.setHandler(contextHandler);
        server.start();
        
        dumpInstructions();

        server.join();
        
    }

	private void dumpInstructions() {
		log.info("To demonstrate a call that works normally, navigate to the following url:");
		log.info("http://localhost:8080/fast");
		log.info("To demonstrate a call that times out, navigate to the following url");
		log.info("http://localhost:8080/slow");
		log.info("To demonstrate a call that fails, navigate to the following url");
		log.info("http://localhost:8080/oops");
	}
}
