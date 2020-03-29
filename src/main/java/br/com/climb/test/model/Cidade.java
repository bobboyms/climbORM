package br.com.climb.test.model;

import br.com.climb.core.PersistentEntity;
import br.com.climb.core.mapping.Column;
import br.com.climb.core.mapping.Entity;

@Entity(name = "tb_cidade")
public class Cidade extends PersistentEntity {
	
	@Column(name = "nome_da_cidade")
	private String nomeDaCidade;

	public String getNomeDaCidade() {
		return nomeDaCidade;
	}

	public void setNomeDaCidade(String nomeDaCidade) {
		this.nomeDaCidade = nomeDaCidade;
	}
	
	
}
