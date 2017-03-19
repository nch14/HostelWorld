package cn.chenhaonee.hostelWorld.domain;

/**
 * Created by nichenhao on 2017/3/20.
 */
public class InnForClient {
    private String id;
    private String name;

    public InnForClient(String id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
