package wifi.hack.com.hacking;

public class WifiModel {

    public String title;
    public String security;
    public String level;
    public String conName;

    public WifiModel(String title, String security, String level, String conName) {
        this.title = title;
        this.security = security;
        this.level = level;
        this.conName=conName;
    }

    public String getTitle() {
        return title;
    }

    public String getSecurity() {
        return security;
    }

    public String getLevel() {
        return level;
    }

    public String getConName() {
        return conName;
    }

    @Override
    public String toString() {
        return title;
    }
}