package br.com.climb.climbquery;

import java.util.ArrayList;
import java.util.List;

public class ClimbQuery {

    private List<Object> query = new ArrayList<>();

    public ClimbQuery distinct(Column column) {

        query.add("distinct ");
        query.add(column);

        return this;
    }

    public ClimbQuery select() {

        query.add("select ");

        return this;
    }

    public ClimbQuery select(Column column) {

        query.add("select ");
        query.add(column);

        return this;
    }

    public ClimbQuery from(String tableName) {
        query.add("from ");
        query.add(tableName + " ");
        return this;
    }

    public ClimbQuery where(Condition condition) {
        query.add("where ");
        query.add(condition);
        return this;
    }

    public ClimbQuery and(Condition condition) {
        query.add("and ");
        query.add(condition);
        return this;
    }

    public ClimbQuery or(Condition condition) {
        query.add("or ");
        query.add(condition);
        return this;
    }

    public ClimbQuery orderBy(Column column) {
        query.add("order by ");
        query.add(column);
        return this;
    }

    public ClimbQuery mim(String column) {
        query.add("min(" + column +")");
        return this;
    }

    public ClimbQuery count(String column) {
        query.add("min(" + column +")");
        return this;
    }

    public ClimbQuery avg(String column) {
        query.add("avg(" + column +")");
        return this;
    }

    public ClimbQuery sum(String column) {
        query.add("sum(" + column +")");
        return this;
    }

    public ClimbQuery asc() {
        query.add("asc ");
        return this;
    }

    public ClimbQuery desc() {
        query.add("desc ");
        return this;
    }

    String getQuery() {
        StringBuilder builder = new StringBuilder();
        query.forEach(value -> builder.append(value));
        return builder.toString();
    }

    public static void main(String[] args) {
        ClimbQuery climbQuery = new ClimbQuery();
        climbQuery
                .select()
                .distinct(new Column("nome", "idade"))
                .from("cliente")
                .where(new Condition("nome").eq("pedro"))
                .and(new Condition("idade").eq(7))
                .or(new Condition("idade").eq(45))
                .orderBy(new Column("nome", "idade"))
                .asc()
                .getQuery();
        System.out.println(climbQuery.getQuery());
    }

}
