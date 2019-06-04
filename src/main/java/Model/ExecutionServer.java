package Model;

/**
 * Model of ACTICO Execution Server.
 */
public class ExecutionServer {

    private String url="";
    private String username="";
    private String password="";
    private String userpassword="";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserpassword() {
        return userpassword;
    }

    /**
     * Build with this.<b>getUsername</b> and this.<b>getPassword</b>.
     */
    public void setUserpassword() {
        this.userpassword = this.getUsername()+":"+this.getPassword();
    }
}
