package br.com.climb.core.sqlengine;

import br.com.climb.core.PersistentEntity;
import br.com.climb.core.sqlengine.interfaces.SqlEngine;
import br.com.climb.modelbean.ModelTableField;
import br.com.climb.test.model.Pessoa;
import br.com.climb.utils.ReflectionUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostgresSqlEngineTest {

    @Test
    public void test_generateInsert() {

        Pessoa pessoa = new Pessoa();
        List<ModelTableField> modelTableFields = ReflectionUtil.generateModel(pessoa);
        SqlEngine sqlEngine = new PostgresSqlEngine();

        final String expected = "INSERT INTO public.tb_pessoa(nome,endereco_comercial," +
                "idade,altura,quantidade_quilos,casado," +
                "id_endereco,foto,lista_emails) VALUES (?,?,?,?,?,?,?,?,?::JSON) RETURNING ID";

        assertThat(expected, is(sqlEngine.generateInsert(modelTableFields, pessoa)));

    }

    @Test
    public void test_generateUpdate() {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(100l);
        List<ModelTableField> modelTableFields = ReflectionUtil.generateModel(pessoa);
        SqlEngine sqlEngine = new PostgresSqlEngine();

        final String expected = "UPDATE public.tb_pessoa SET nome= ?,endereco_comercial= ?," +
                "idade= ?,altura= ?,quantidade_quilos= ?,casado= ?,id_endereco= ?,foto= ?," +
                "lista_emails= ?::JSON WHERE id = 100";

        assertThat(expected, is(sqlEngine.generateUpdate(modelTableFields, pessoa)));

    }

    @Test
    public void test_generateDelete() {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(100l);
        List<ModelTableField> modelTableFields = ReflectionUtil.generateModel(pessoa);
        SqlEngine sqlEngine = new PostgresSqlEngine();

        final String expected = "DELETE FROM public.tb_pessoa WHERE id = 100";

        assertThat(expected, is(sqlEngine.generateDelete(pessoa)));

    }

    @Test
    public void test_generateDeleteWhere() throws Exception {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(100l);
        List<ModelTableField> modelTableFields = ReflectionUtil.generateModel(pessoa);
        SqlEngine sqlEngine = new PostgresSqlEngine();

        final String expected = "DELETE FROM public.tb_pessoa WHERE id = 100";

        assertThat(expected, is(sqlEngine.generateDelete(Pessoa.class, "WHERE id = 100")));

    }

}
