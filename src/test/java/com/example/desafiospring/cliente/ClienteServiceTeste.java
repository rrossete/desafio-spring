package com.example.desafiospring.cliente;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.desafiospring.cidade.Cidade;
import com.example.desafiospring.cidade.CidadeService;
import com.example.desafiospring.exception.BusinessException;
import com.example.desafiospring.exception.NotFoundException;

@RunWith(SpringRunner.class)
public class ClienteServiceTeste {

	@InjectMocks
	private ClienteService clienteService;

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private CidadeService cidadeService;

	@Before
	public void setUp() {

		Optional<Cliente> cliente = obterclienteOptional();

		when(clienteRepository.findById(1)).thenReturn(cliente);
		when(clienteRepository.findAll()).thenReturn(this.obterClientes());
		when(cidadeService.getCidade(1)).thenReturn(this.criarCidade());
		when(clienteRepository.saveAndFlush(Mockito.any())).thenReturn(this.criarCliente(this.obterClienteDTO(), criarCidade()));
	}


	private Cliente criarCliente(ClienteDTO dto, Cidade criarCidade) {
		Cliente cliente = Cliente.novo(dto, criarCidade);
		cliente.setId(dto.getId());
		return cliente;
	}


	@Test
	public void testObterClientePorId() {
		ClienteDTO dto = this.clienteService.getById(1);

		assertEquals(dto.getCidade(), 1);
		assertEquals(dto.getDataNascimento(), LocalDate.of(2000, 01, 01));
	}

	@Test
	public void testObterClientePorIdInexistente() {
		try {
			ClienteDTO dto = this.clienteService.getById(3);
			assertTrue(Objects.nonNull(dto));

		} catch (NotFoundException e) {
			assertEquals("Cliente com ID: 3 inexistente", e.getMessage());
		}

	}
	@Test
	public void testObterClienteSemId() {
		try {
			ClienteDTO dto = this.clienteService.getById(null);
			assertTrue(Objects.nonNull(dto));
			
		} catch (BusinessException e) {
			assertEquals("O ID do cliente é obrigatório", e.getMessage());
		}
		
	}

	@Test
	public void testObterClientes() {
		List<ClienteDTO> clientes = this.clienteService.getClientes();
		
		assertTrue(!clientes.isEmpty());
		assertEquals(clientes.get(0).getDataNascimento(), LocalDate.of(2000, 01, 01));
		assertEquals(clientes.get(0).getId(), 1);
	}
	
	@Test
	public void testCriarNovoCliente() {
		Cliente cliente = this.clienteService.novo(this.obterClienteDTO());
		
		assertEquals(cliente.getId(), 1);
		assertEquals(cliente.getIdade(), 21);
		assertEquals(cliente.getNome(), "Mário");
	}
	
	
	@Test
	public void testNovoClienteComDadosInvalidos() {
		ClienteDTO dto = this.obterClienteDTO();
		
		try {
			dto.setCidade(null);
			Cliente cliente = this.clienteService.novo(dto);
			assertTrue(Objects.isNull(cliente));
		} catch (BusinessException e) {
			assertEquals("O ID da cidade é obrigatório.", e.getMessage());
		}
		try {
			dto.setCidade(1);
			dto.setDataNascimento(null);
			Cliente cliente = this.clienteService.novo(dto);
			assertTrue(Objects.isNull(cliente));
		} catch (BusinessException e) {
			assertEquals("A data de nascimento do cliente é obrigatório.", e.getMessage());
		}
		try {
			dto.setDataNascimento(LocalDate.of(2000, 01, 01));
			dto.setIdade(null);
			Cliente cliente = this.clienteService.novo(dto);
			assertTrue(Objects.isNull(cliente));
		} catch (BusinessException e) {
			assertEquals("A idade do cliente é obrigatório.", e.getMessage());
		}
		try {
			dto.setIdade(21);
			dto.setNome(null);
			Cliente cliente = this.clienteService.novo(dto);
			assertTrue(Objects.isNull(cliente));
		} catch (BusinessException e) {
			assertEquals("O nome do cliente é obrigatório.", e.getMessage());
		}
		
		try {
			dto.setNome("Mário");
			dto.setSexo(null);
			Cliente cliente = this.clienteService.novo(dto);
			assertTrue(Objects.isNull(cliente));
		} catch (BusinessException e) {
			assertEquals("O sexo do cliente é obrigatório.", e.getMessage());
		}
		
		
	}
	
	@Test
	public void testDeletar() {
		try {
			this.clienteService.delete(1);
			
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	
	private ClienteDTO obterClienteDTO() {
		ClienteDTO dto = new ClienteDTO();
		
		dto.setCidade(1);
		dto.setDataNascimento(LocalDate.of(2000, 01, 01));
		dto.setIdade(21);
		dto.setNome("Mário");
		dto.setSexo("Masculino");
		dto.setId(1);
		return dto;
	}


	private List<Cliente> obterClientes() {
		List<Cliente> clientes = new ArrayList<>();
		
		Cliente cliente =  new Cliente();
		
		cliente.setCidade(this.criarCidade());
		cliente.setDataNascimento(LocalDate.of(2000, 01, 01));
		cliente.setId(1);
		cliente.setIdade(21);
		cliente.setNome("Antônio Carlos");
		cliente.setSexo("Masculino");
		
		clientes.add(cliente);
		return clientes;
	}

	private Optional<Cliente> obterclienteOptional() {
		Optional<Cliente> cliente = Optional.ofNullable(new Cliente());
		cliente.get().setCidade(this.criarCidade());
		cliente.get().setDataNascimento(LocalDate.of(2000, 01, 01));
		cliente.get().setId(1);
		cliente.get().setIdade(21);
		cliente.get().setNome("Antônio Carlos");
		cliente.get().setSexo("Masculino");

		return cliente;
	}

	private Cidade criarCidade() {
		Cidade cidade = new Cidade();
		cidade.setEstado("MG");
		cidade.setNome("Juiz de Fora");
		cidade.setId(1);

		return cidade;
	}

}
