package Model;

import ACTICO.ActicoInterface;

/**
 * Model URI which feeds the classes {@link ACTICO.ActicoServer} and {@link ActicoInterface}
 */
public class URI {

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
     * Build the URL address to which the {@link Request} is addressed.
     * @return URL address on <b>Actico Server</b>, i.e. localhost:8087.
     */
    @Override
    public String toString() {
        return "http://localhost:8087/executionserver/rest/1/ruleServices/" +
                ruleService + "/versions/" + version + "/rules/" + rule + "/executions";
    }
}