package br.com.integrationtest;

import br.com.climb.core.ClimbORM;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.test.model.Cidade;
import br.com.climb.test.model.Email;
import br.com.climb.test.model.Endereco;
import br.com.climb.test.model.Pessoa;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConnectionTest {

    public static Long idPessoa;
    public static Long idCidade;
    public static Long idEndereco;

    public static ManagerFactory managerFactory;

    @Test
    @Order(0)
    void generateFactory() {
        managerFactory = ClimbORM.createManagerFactory("climb.properties");
        assertTrue(managerFactory != null);
    }

    @Test
    @Order(1)
    void test_connection() {
        managerFactory = ClimbORM.createManagerFactory("climb.properties");
        ClimbConnection connection = managerFactory.getConnection();
    }

    @Test
    @Order(2)
    void test_insert() {

        ClimbConnection connection = managerFactory.getConnection();

        connection.getTransaction().start();

        Cidade cidade = new Cidade();
        cidade.setNomeDaCidade("Cacoal");
        connection.save(cidade);

        idCidade = cidade.getId();
        assertTrue(idCidade != null && idCidade > 0);

        Endereco endereco = new Endereco();
        endereco.setNomeDaRua("Rua Ji-parana");
        endereco.setCidade(cidade);
        connection.save(endereco);

        idEndereco = endereco.getId();
        assertTrue(idEndereco != null);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Maria antonia");
        pessoa.setEnderecoComercial("Treta");
        pessoa.setIdade(32l);
        pessoa.setAltura(174.1325525544f);
        pessoa.setQtdQuilos(145.6144d);
        pessoa.setCasado(true);
        pessoa.setEndereco(endereco);
        pessoa.setFoto(new byte[] {});

        List<Email> emails = new ArrayList<>();
        Email email = new Email();
        email.setEmail("taliba@jose.com.br");
        emails.add(email);
        pessoa.setEmails(emails);

        connection.save(pessoa);

        idPessoa = pessoa.getId();
        assertTrue(idPessoa != null);

        connection.getTransaction().commit();
        connection.close();
    }

    @Test
    @Order(3)
    void test_printaID() {
        System.out.println("ID PESSOA: " + idPessoa);
        System.out.println("ID CIDADE: " + idCidade);
        System.out.println("ID ENDERECO: " + idEndereco);
    }

    @Test
    @Order(4)
    void test_select() {

        ClimbConnection connection = managerFactory.getConnection();
        Pessoa pessoa = (Pessoa) connection.findOne(Pessoa.class, idPessoa);

        assertTrue(pessoa != null);
        assertTrue(pessoa.getEmails().size() > 0);
        assertTrue(pessoa.getId() != null);
        assertTrue(pessoa.getEndereco() != null);
        assertTrue(pessoa.getEndereco().getId() != null);
        assertTrue(pessoa.getEndereco().getCidade() != null);
        assertTrue(pessoa.getEndereco().getCidade().getId() != null);

        connection.close();

//        connection = factory.getConnection("localhost");
//        ResultIterator iterator = connection.find(Pessoa.class, "where id = " + idPessoa.toString());
//
//        assertTrue(iterator != null);
//
//        while (iterator.next()) {
//            Pessoa pessoa1 = (Pessoa) iterator.getObject();
//
//            assertTrue(pessoa1 != null);
//            assertTrue(pessoa1.getEmails().size() > 0);
//            assertTrue(pessoa1.getId() != null);
//            assertTrue(pessoa1.getEndereco() != null);
//            assertTrue(pessoa1.getEndereco().getId() != null);
//            assertTrue(pessoa1.getEndereco().getCidade() != null);
//            assertTrue(pessoa1.getEndereco().getCidade().getId() != null);
//        }
//
//        ResultIterator resultIterator = connection.findWithQuery(RespostaQuery.class, "SELECT " +
//                "p.nome, e.nome_da_rua, p.id_endereco, c.nome_da_cidade, p.lista_emails " +
//                "FROM localhost.tb_pessoa p \n" +
//                "INNER JOIN localhost.tb_endereco e on p.id_endereco = e.id\n" +
//                "INNER JOIN localhost.tb_cidade c on e.id_cidade = c.id\n" +
//                "where e.id_cidade > 1 and p.lista_emails is not null");
//
//        while(resultIterator.next()) {
//            RespostaQuery RespostaQuery = (RespostaQuery) resultIterator.getObject();
//            assertTrue(RespostaQuery != null);
//            assertTrue(RespostaQuery.getNome() != null);
//        }

        connection.close();

    }

}
