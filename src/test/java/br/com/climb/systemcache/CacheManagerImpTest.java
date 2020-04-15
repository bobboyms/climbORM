package br.com.climb.systemcache;

import br.com.climb.configfile.FactoryConfigFile;
import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.mapping.Cachable;
import br.com.climb.exception.ConfigFileException;
import br.com.climb.systemcache.model.CommandDTO;
import br.com.climb.test.model.Cidade;
import br.com.climb.test.model.Endereco;
import br.com.climb.test.model.Pessoa;
import br.com.climb.utils.ReflectionUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class CacheManagerImpTest {

    @Test
    void addToCache() throws IOException, ConfigFileException {

        Cidade cidade = new Cidade();
        cidade.setId(1l);
        cidade.setNomeDaCidade("Cacoal");

        Endereco endereco = new Endereco();
        endereco.setId(1l);
        endereco.setNomeDaRua("Rua Ji-parana");
        endereco.setCidade(cidade);

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1l);
        pessoa.setNome("Maria antonia");
        pessoa.setEnderecoComercial("Treta");
        pessoa.setIdade(32l);
        pessoa.setAltura(174.1325525544f);
        pessoa.setQtdQuilos(145.6144d);
        pessoa.setCasado(true);
        pessoa.setEndereco(endereco);
        pessoa.setFoto("asdfasdasdasdsdf".getBytes());

        ConfigFile configFile = new FactoryConfigFile().getConfigFile("climb.properties");

        CacheManagerImp cacheManagerImp = new CacheManagerImp(configFile);
        cacheManagerImp.addToCache(pessoa);

    }

    @Test
    void getValueCache() throws Exception {

        ConfigFile configFile = new FactoryConfigFile().getConfigFile("climb.properties");

        CacheManagerImp cacheManagerImp = new CacheManagerImp(configFile);
        Object object = cacheManagerImp.getValueCache(Pessoa.class, 1l);

//        System.out.println(((Pessoa)object).getNome());
//        System.out.println(commandDTO.getValue());

    }

    @Test
    void teste() {
        Cachable cachable = Pessoa.class.getAnnotation(Cachable.class);
        System.out.println(cachable != null);
    }
}