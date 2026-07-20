package day5.q47;

public class DataSource {

    private final String url;
    private final String username;

    public DataSource(String url, String username) {
        this.url = url;
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "DataSource{url='" + url + "', username='" + username + "'}";
    }
}
