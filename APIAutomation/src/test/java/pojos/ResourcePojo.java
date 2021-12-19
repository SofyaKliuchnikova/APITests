package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourcePojo {
    private int userId;
    private int id;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

}
