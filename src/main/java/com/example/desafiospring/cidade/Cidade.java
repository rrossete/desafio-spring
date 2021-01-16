package com.example.desafiospring.cidade;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.desafiospring.cliente.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CP_Cidade")
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;
	
	@Column(name= "estado")
	private String estado;
	
	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "cidade_id",  updatable = false, insertable = false)
	private List<Cliente> cliente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Cliente> getCliente() {
		return cliente;
	}

	public void setCliente(List<Cliente> cliente) {
		this.cliente = cliente;
	}
	
	public CidadeDTO obterDTO() {
		CidadeDTO dto = new CidadeDTO();
		dto.setCidadeId(this.getId());
		dto.setEstado(this.getEstado());
		dto.setNomeCidade(this.getNome());
		
		return dto;
	}
	
	public static Cidade novo(CidadeDTO dto) {
		Cidade cidade = new Cidade();
		cidade.setNome(dto.getNomeCidade());
		cidade.setEstado(dto.getEstado());
		return cidade;
	}

	public void atualizar(CidadeDTO dto) {
		this.setNome(dto.getNomeCidade());
		this.setEstado(dto.getEstado());
		
	}
	
	


}
