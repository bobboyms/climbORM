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
    public void test_connection() {
        managerFactory = ClimbORM.createManagerFactory("climb.properties");
        ClimbConnection connection = managerFactory.getConnection();
    }

    @Test
    @Order(1)
    void insert() {

        ClimbConnection connection = managerFactory.getConnection();

        connection.getTransaction().start();

        Cidade cidade = new Cidade();
        cidade.setNomeDaCidade("Cacoal");
        connection.save(cidade);

        idCidade = cidade.getId();
        assertTrue(idCidade != null && idCidade > 0);

//        Endereco endereco = new Endereco();
//        endereco.setNomeDaRua("Rua Ji-parana");
//        endereco.setCidade(cidade);
//        connection.save(endereco);
//
//        idEndereco = endereco.getId();
//        assertTrue(idEndereco != null);
//
//        Pessoa pessoa = new Pessoa();
//        pessoa.setNome("Maria antonia");
//        pessoa.setEnderecoComercial("Treta");
//        pessoa.setIdade(32l);
//        pessoa.setAltura(174.1325525544f);
//        pessoa.setQtdQuilos(145.6144d);
//        pessoa.setCasado(true);
//        pessoa.setEndereco(endereco);
//        pessoa.setFoto(new byte[] {});
//
//        List<Email> emails = new ArrayList<>();
//        Email email = new Email();
//        email.setEmail("taliba@jose.com.br");
//        emails.add(email);
//        pessoa.setEmails(emails);
//
//        connection.save(pessoa);
//
//        idPessoa = pessoa.getId();
//        assertTrue(idPessoa != null);

        connection.getTransaction().commit();
        connection.close();
    }

    @Test
    @Order(2)
    void printaID() {
        System.out.println("ID PESSOA: " + idPessoa);
        System.out.println("ID CIDADE: " + idCidade);
        System.out.println("ID ENDERECO: " + idEndereco);
    }
}
