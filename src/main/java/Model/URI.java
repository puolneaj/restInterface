package Model;

import ACTICO.ActicoInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Model URI which feeds the classes {@link ActicoInterface}
 */
public class URI {
    final Logger logger= LogManager.getLogger(ActicoInterface.class);

    private String ruleService = "";
    private String version = "";
    private String rule = "";

    public String getRuleService() {
        return ruleService;
    }

    public void setRuleService(String ruleService) {
        this.ruleService = ruleService;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * Build the URL address to which the request of type {@link Input} is addressed.
     * @return URL address on <b>Actico Server</b>, i.e. server 172.26.161.170.
     */
    @Override
    public String toString() {
//        option 1: set the URI for remote desk
 /*      logger.debug("http://172.26.161.170:8087/executionserver/rest/1/ruleServices/" +
                ruleService + "/versions/" + version + "/rules/" + rule + "/executions");
        return "http://172.26.161.170:8087/executionserver/rest/1/ruleServices/" +
                ruleService + "/versions/" + version + "/rules/" + rule + "/executions";*/
//        option 2: set the URI for the local machine
        logger.debug("http://localhost:8087/executionserver/rest/1/ruleServices/" +
                ruleService + "/versions/" + version + "/rules/" + rule + "/executions");
        return "http://localhost:8087/executionserver/rest/1/ruleServices/" +
                this.ruleService + "/versions/" + this.version + "/rules/" + this.rule + "/executions";
    }
}