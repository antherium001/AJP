package day5.q49;

import org.springframework.stereotype.Repository;

@Repository
public class ConfigRepo {

    public String fetchData() {
        return "Data fetched from ConfigRepo using @Repository";
    }
}
