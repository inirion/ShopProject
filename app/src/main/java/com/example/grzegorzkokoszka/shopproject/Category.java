package com.example.grzegorzkokoszka.shopproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grzegorz Kokoszka on 2017-02-22.
 */

public class Category {
    private String id;
    private String Name;

    public Category(String name, String id) {
        Name = name;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
