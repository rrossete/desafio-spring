package com.example.desafiospring.cidade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CidadeRepositoryTeste {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Test
	public void obterCidadePorNome() {
		this.cidadeRepository.saveAndFlush(this.criarCidade());
		List<Cidade> cidade = this.cidadeRepository.findByNome("Juiz de Fora");

		assertEquals(cidade.size(), 1);
	}

	@Test
	public void obterCidadePorEstado() {
		this.cidadeRepository.saveAndFlush(this.criarCidade());
		List<Cidade> cidade = this.cidadeRepository.findByEstado("Minas Gerais");

		assertEquals(cidade.size(), 1);
	}

	@Test
	public void obterCidadePorEstadoENome() {
		this.cidadeRepository.saveAndFlush(this.criarCidade());
		List<Cidade> cidade = this.cidadeRepository.findByNomeAndEstado("Juiz de Fora", "Minas Gerais");

		assertEquals(cidade.size(), 1);
	}

	private Cidade criarCidade() {
		Cidade cidade = new Cidade();
		cidade.setEstado("Minas Gerais");
		cidade.setNome("Juiz de Fora");
		return cidade;
	}
}
