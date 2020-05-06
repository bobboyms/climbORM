package br.com.climb.climbquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Condition {

    private List<String> query = new ArrayList<>();

    public Condition(String field) {
        query.add(field + " ");
    }

    public Condition eq(Object field) {
        query.add("= " + field + " ");
        return this;
    }

    public Condition like(Object field) {
        query.add("like " + field + " ");
        return this;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        query.forEach(value -> {
            builder.append(value);
        });
        return builder.toString();
    }
}
