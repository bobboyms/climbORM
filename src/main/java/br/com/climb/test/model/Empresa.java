package br.com.climb.test.model;


import br.com.climb.core.PersistentEntity;
import br.com.climb.core.mapping.Column;
import br.com.climb.core.mapping.Entity;
import br.com.climb.core.mapping.Relation;

@Entity(name = "tb_empresa")
public class Empresa extends PersistentEntity {

    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Relation
    @Column(name = "id_diretor")
    private Diretor diretor;

//    @DynamicField
//    private DynamicFields dynamicFields;

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

//    public DynamicFields getDynamicFields() {
//        return dynamicFields;
//    }

//    public void setDynamicFields(DynamicFields dynamicFields) {
//        this.dynamicFields = dynamicFields;
//    }

    public void setDiretor(Diretor diretor) {
        this.diretor = diretor;
    }
}
