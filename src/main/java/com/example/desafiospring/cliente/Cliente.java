package com.example.desafiospring.cliente;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.desafiospring.cidade.Cidade;

@Entity
@Table(name = "CP_Cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name="sexo")
	private String sexo;

	@Column(name="data_nascimento")
	private LocalDate dataNascimento;

	@Column(name="idade")
	private Integer idade;
	
	@ManyToOne
	@JoinColumn(name = "cidade_id", updatable = true, insertable = true)
	private Cidade cidade;
	

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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public ClienteDTO obterDTO() {
		ClienteDTO dto = new  ClienteDTO();
		dto.setCidade(this.getCidade().getId());
		dto.setDataNascimento(this.getDataNascimento());
		dto.setId(this.getId());
		dto.setNome(this.getNome());
		dto.setIdade(this.getIdade());
		dto.setSexo(this.getSexo());
		
		return dto;
	}
	
	public static Cliente novo(ClienteDTO dto, Cidade cidade ) {
		Cliente cliente = new Cliente();
		
		cliente.setCidade(cidade);
		cliente.setDataNascimento(dto.getDataNascimento());
		cliente.setNome(dto.getNome());
		cliente.setIdade(dto.getIdade());
		cliente.setSexo(dto.getSexo());
		
		return cliente;
	}

	public void atualizarDados(ClienteDTO dto, Cidade cidade ) {
		this.setDataNascimento(dto.getDataNascimento());
		this.setIdade(dto.getIdade());
		this.setCidade(cidade);
		this.setNome(dto.getNome());
		this.setSexo(dto.getSexo());
	}
	

}
