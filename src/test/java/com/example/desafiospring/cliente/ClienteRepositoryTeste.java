package com.example.desafiospring.cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.desafiospring.cidade.Cidade;
import com.example.desafiospring.cidade.CidadeRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClienteRepositoryTeste {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;


	
	@Test
	public void obterCidadePorNome() {
		Cidade cidade = this.criarCidade();
		cidade = this.cidadeRepository.saveAndFlush(this.criarCidade());
		this.clienteRepository.saveAndFlush(this.criarCliente(cidade));
		List<Cliente> cliente = this.clienteRepository.findByNome("João");
		
		assertEquals(cliente.size(), 1);
	}



	private Cliente criarCliente(Cidade cidade) {
		Cliente cliente = new Cliente();
		cliente.setCidade(cidade);
		cliente.setDataNascimento(LocalDate.of(2020, 01, 01));
		cliente.setIdade(1);
		cliente.setNome("João");
		cliente.setSexo("Masculino");
		return cliente;
	}
	
	private Cidade criarCidade() {
		Cidade cidade = new Cidade();
		cidade.setEstado("Minas Gerais");
		cidade.setNome("Juiz de Fora");
		return cidade;
	}
}
