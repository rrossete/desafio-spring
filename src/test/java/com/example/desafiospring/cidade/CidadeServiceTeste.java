package com.example.desafiospring.cidade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.desafiospring.cliente.Cliente;
import com.example.desafiospring.cliente.ClienteService;
import com.example.desafiospring.exception.BusinessException;

@RunWith(SpringRunner.class)
public class CidadeServiceTeste {

	private static final String SP = "SP";

	private static final String JUIZ_DE_FORA = "Juiz de Fora";

	private static final String SAO_PAULO = "São Paulo";

	@InjectMocks
	private CidadeService cidadeService;

	@Mock
	private CidadeRepository cidadeRepository;

	@Mock
	private ClienteService clienteService;

	@Before
	public void setUp() {
		Optional<Cidade> cidade = Optional.ofNullable(new Cidade());
		Optional<Cidade> cidade2 = this.obterCidadeId2();

		cidade.get().setEstado("Minas Gerais");
		cidade.get().setNome(JUIZ_DE_FORA);
		cidade.get().setId(1);

		Mockito.when(cidadeRepository.findById(1)).thenReturn(cidade);
		Mockito.when(cidadeRepository.findById(2)).thenReturn(cidade2);
		Mockito.when(cidadeRepository.findAll()).thenReturn(this.obterListaCidades());
		Mockito.when(cidadeRepository.saveAndFlush(Mockito.any())).thenReturn(cidade.get());
		Mockito.when(clienteService.obterClientePorCidade(cidade.get())).thenReturn(new ArrayList<Cliente>());
		Mockito.when(clienteService.obterClientePorCidade(cidade2.get())).thenReturn(this.obterListaClientes());

	}

	private List<Cliente> obterListaClientes() {
		List<Cliente> clientes = new ArrayList<>();

		Cliente cliente = new Cliente();

		cliente.setCidade(this.obterCidadeId2().get());
		cliente.setDataNascimento(LocalDate.of(2000, 01, 01));
		cliente.setId(1);
		cliente.setIdade(21);
		cliente.setNome("João Bosco");
		cliente.setSexo("Masculino");

		clientes.add(cliente);

		return clientes;
	}

	@Test
	public void testObterCidadePorId() {
		CidadeDTO cidade = cidadeService.getById(1);

		Assert.assertEquals(cidade.getNomeCidade(), JUIZ_DE_FORA);
		Assert.assertEquals(cidade.getEstado(), "Minas Gerais");

	}

	@Test
	public void testObterCidadePorIdInexistente() {
		try {
			CidadeDTO cidade = cidadeService.getById(null);
			Assert.assertTrue(Objects.isNull(cidade));
		} catch (BusinessException e) {
			assertEquals("O ID da cidade é obrigatório.", e.getMessage());
		}

	}

	@Test
	public void testObterCidades() {
		List<CidadeDTO> cidade = cidadeService.getCidades();

		Assert.assertEquals(cidade.size(), 1);
		Assert.assertEquals(cidade.get(0).getNomeCidade(), SAO_PAULO);

	}

	@Test
	public void testInserirNovaCidade() {
		Cidade cidade = this.cidadeService.novo(this.obterCidadeDTO());

		Assert.assertEquals(cidade.getNome(), JUIZ_DE_FORA);
		Assert.assertEquals(cidade.getEstado(), "Minas Gerais");
	}

	@Test
	public void testInserirNovaCidadeComDadosFaltando() {
		CidadeDTO dto = this.obterCidadeDTO();

		try {
			dto.setEstado(null);
			Cidade cidade = this.cidadeService.novo(dto);
			Assert.assertTrue(Objects.isNull(cidade));
		} catch (BusinessException e) {
			assertEquals("O estado é obrigatório.", e.getMessage());
		}
		try {
			dto.setEstado(SP);
			dto.setNomeCidade(null);

			Cidade cidade = this.cidadeService.novo(dto);
			Assert.assertTrue(Objects.isNull(cidade));
		} catch (BusinessException e) {
			assertEquals("O nome da cidade é obrigatório", e.getMessage());
		}
	}

	@Test
	public void testDeletarCidade() {
		try {
			this.cidadeService.delete(1);

		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testDeletarCidadeComClientesVinculados() {
		try {
			this.cidadeService.delete(2);
			Assert.assertTrue(false);
		} catch (BusinessException e) {
			Assert.assertEquals("A cidade Bicas está associada a um ou mais cliente.", e.getMessage());
		}
	}

	private CidadeDTO obterCidadeDTO() {
		CidadeDTO dto = new CidadeDTO();

		dto.setEstado(SP);
		dto.setNomeCidade("Jundiaí");

		return dto;
	}

	private List<Cidade> obterListaCidades() {
		List<Cidade> cidades = new ArrayList<>();
		Cidade cidade = new Cidade();
		cidade.setEstado(SP);
		cidade.setId(2);
		cidade.setNome(SAO_PAULO);
		cidades.add(cidade);
		return cidades;
	}

	private Optional<Cidade> obterCidadeId2() {
		Optional<Cidade> cidade = Optional.ofNullable(new Cidade());

		cidade.get().setEstado("Minas Gerais");
		cidade.get().setNome("Bicas");
		cidade.get().setId(2);
		return cidade;
	}
}
