package com.example.desafiospring.cidade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CidadeControllerTeste {

	@InjectMocks
	private CidadeService cidadeService;

	@Mock
	CidadeRepository cidadeRepository;
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	private Optional<Cidade> cidade;

	@Before
	public void antes() {

		this.cidade = Optional.ofNullable(new Cidade());
		this.cidade.get().setEstado("Minas Gerais");
		this.cidade.get().setId(1);
		this.cidade.get().setNome("Juiz de Fora");

	}

	

}
