package com.actico.restInterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * My <b> REST Interface</b>
 * <p>Build the REST architecture on the localhost:8080 from which ACTICO RuleServices are called.</p>
 *
 * Creating our own REST on a different port than the one used by Actico (i.e. port 8087)
 * gives us the possibility to taylor the endpoints to our  needs.
 * Moreover, Actico server provides endpoints which are specific to the executions of
 * the RuleServices {@link RequestResource}
 * <p>Those endpoints can be accessed on the Help Section of Actico Server WebPage.</p>
 */
@SpringBootApplication
public class RestinterfaceApplication {
	/**
	 * <b>Main method of the project</b>
	 * <p>Run the localhost:8080 and make the REST endpoints accessible</p>
	 * @param args this method doesn't require argument
	 */
	public static void main(String[] args) {
		SpringApplication.run(RestinterfaceApplication.class, args);
	}

}
