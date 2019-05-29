package Model;

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

    @Override
    public String toString() {
        return "http://localhost:8087/executionserver/rest/1/ruleServices/" +
                ruleService + "/versions/" + version + "/rules/" + rule + "/executions";
    }
}