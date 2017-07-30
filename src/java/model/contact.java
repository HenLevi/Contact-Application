package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author chen
 */
public class contact {

    private String id;
    private String name;
    private String job;
    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public contact() {
        this.id = "";
        this.name = "";
        this.job = "";
        this.img = "";

    }

    public contact(String id, String name, String job, String img) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.img = img;
    }

    public contact(String jsonStr) {
        JsonObject o = (JsonObject) new JsonParser().parse(jsonStr);
        this.id = o.get("id").getAsString();
        this.name = o.get("name").getAsString();
        this.job = o.get("job").getAsString();
        this.img = o.get("img").getAsString();
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}


