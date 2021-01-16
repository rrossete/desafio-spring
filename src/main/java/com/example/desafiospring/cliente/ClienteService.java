package com.example.desafiospring.cliente;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafiospring.cidade.Cidade;
import com.example.desafiospring.cidade.CidadeService;
import com.example.desafiospring.exception.BusinessException;
import com.example.desafiospring.exception.NotFoundException;

@Service
public class ClienteService {

	private static final String CLIENTE_NAO_ENCONTRADO = "Cliente com ID: {0} inexistente";

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeService cidadeService;

	public List<ClienteDTO> getClientes() {
		return this.obterListaClienteDTO(this.clienteRepository.findAll());
	}

	public ClienteDTO getById(Integer id) {
		this.validarIdCliente(id);
		Cliente cliente = obterCliente(id);

		return cliente.obterDTO();
	}

	public void atualizar(ClienteDTO dto, Integer id) {
		this.validarIdCliente(id);
		this.validarDadosCliente(dto);
		Cliente cliente = this.obterCliente(id);

		Cidade cidade = this.obterClidade(dto.getCidade());

		cliente.atualizarDados(dto, cidade);

		this.clienteRepository.save(cliente);

	}

	public void delete(Integer id) {
		this.validarIdCliente(id);
		this.clienteRepository.delete(this.obterCliente(id));
	}

	public Cliente novo(ClienteDTO clienteDto) {
		this.validarDadosCliente(clienteDto);

		return this.clienteRepository.saveAndFlush(Cliente.novo(clienteDto, this.obterClidade(clienteDto.getCidade())));
	}
	
	public List<Cliente> obterClientePorCidade(Cidade cidade){
		return this.clienteRepository.findByCidade(cidade);
	}

	private void validarIdCliente(Integer id) {
		if (Objects.isNull(id)) {
			throw new BusinessException("O ID do cliente é obrigatório");
		}

	}

	private Cliente obterCliente(Integer id) {
		return this.clienteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(MessageFormat.format(CLIENTE_NAO_ENCONTRADO, id)));
	}

	private Cidade obterClidade(Integer cidade) {
		return this.cidadeService.getCidade(cidade);
	}

	private void validarDadosCliente(ClienteDTO dto) {

		this.validarNomeCliente(dto.getNome());
		if (Objects.isNull(dto.getDataNascimento())) {
			throw new BusinessException("A data de nascimento do cliente é obrigatório.");
		}
		if (Objects.isNull(dto.getIdade())) {
			throw new BusinessException("A idade do cliente é obrigatório.");
		}
		if (Objects.isNull(dto.getSexo())) {
			throw new BusinessException("O sexo do cliente é obrigatório.");
		}
		if (Objects.isNull(dto.getCidade())) {
			throw new BusinessException("O ID da cidade é obrigatório.");
		}

	}

	private void validarNomeCliente(String nome) {
		if (Objects.isNull(nome)) {
			throw new BusinessException("O nome do cliente é obrigatório.");
		}
	}

	public List<ClienteDTO> getByNome(String nome) {
		this.validarNomeCliente(nome);
		return this.obterListaClienteDTO(this.clienteRepository.findByNome(nome));
	}

	private List<ClienteDTO> obterListaClienteDTO(List<Cliente> clientes) {
		return clientes.stream().map(Cliente::obterDTO).collect(Collectors.toList());
	}

}
