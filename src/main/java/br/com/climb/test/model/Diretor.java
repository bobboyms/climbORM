package br.com.climb.test.model;


import br.com.climb.core.PersistentEntity;
import br.com.climb.core.mapping.Column;
import br.com.climb.core.mapping.Entity;

@Entity(name = "tb_diretor")
public class Diretor extends PersistentEntity {

    @Column(name = "nome_diretor")
    private String nomeDiretor;

//    @DynamicField
//    private DynamicFields dynamicFields;

//    public DynamicFields getDynamicFields() {
//        return dynamicFields;
//    }

//    public void setDynamicFields(DynamicFields dynamicFields) {
//        this.dynamicFields = dynamicFields;
//    }

    public String getNomeDiretor() {
        return nomeDiretor;
    }

    public void setNomeDiretor(String nomeDiretor) {
        this.nomeDiretor = nomeDiretor;
    }


}
