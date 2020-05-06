package br.com.climb.climbquery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Column {

    private List<String> cols = new ArrayList<>();

    public Column(String... columns) {

        final int[] index = {1};
        Arrays.asList(columns).forEach(value -> {
            if (index[0] == columns.length) {
                cols.add(value + " ");
            } else {
                cols.add(value + ", ");
            }
            index[0]++;
        });

    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        cols.forEach(value-> builder.append(value));

        return builder.toString();
    }
}
