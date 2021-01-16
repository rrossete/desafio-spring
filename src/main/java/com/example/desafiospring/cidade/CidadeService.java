package com.example.desafiospring.cidade;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafiospring.cliente.ClienteService;
import com.example.desafiospring.exception.BusinessException;
import com.example.desafiospring.exception.NotFoundException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteService clienteService;

	public List<CidadeDTO> getCidades() {
		return this.cidadeRepository.findAll().stream().map(Cidade::obterDTO).collect(Collectors.toList());
	}

	public CidadeDTO getById(Integer cidadeId) {
		return this.getCidade(cidadeId).obterDTO();
	}

	public Cidade getCidade(Integer cidadeId) {
		this.validarId(cidadeId);
		return obterCidade(cidadeId);
	}

	private void validarId(Integer cidadeId) {
		if (Objects.isNull(cidadeId)) {
			throw new BusinessException("O ID da cidade é obrigatório.");
		}

	}

	private Cidade obterCidade(Integer cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(
				() -> new NotFoundException(MessageFormat.format("Cidade com ID: {0} inexistente.", cidadeId)));
	}

	public void atualizar(CidadeDTO dto, Integer id) {
		this.validarId(id);
		this.validarDadosCidade(dto);
		Cidade cidade = this.obterCidade(id);
		cidade.atualizar(dto);

		this.cidadeRepository.save(cidade);
	}

	public void delete(Integer id) {
		this.validarId(id);
		Cidade cidade = this.obterCidade(id);
		this.verificarClienteAssociado(cidade);
		this.cidadeRepository.delete(cidade);

	}

	public Cidade novo(CidadeDTO cidadeDto) {
		this.validarDadosCidade(cidadeDto);
		return this.cidadeRepository.saveAndFlush(Cidade.novo(cidadeDto));
	}

	public List<CidadeDTO> getByFiltro(String nome, String estado) {

		if (filtrarPorNomeEstado(nome, estado)) {
			return obterListaCidadeDTO(this.cidadeRepository.findByNomeAndEstado(nome, estado));
		} else if (Objects.nonNull(estado)) {
			return obterListaCidadeDTO(this.cidadeRepository.findByEstado(estado));
		} else {
			return obterListaCidadeDTO(this.cidadeRepository.findByNome(nome));
		}

	}

	private void verificarClienteAssociado(Cidade cidade) {
		if (!this.clienteService.obterClientePorCidade(cidade).isEmpty()) {
			throw new BusinessException(MessageFormat.format("A cidade {0} está associada a um ou mais cliente.", cidade.getNome()));
		}

	}

	private void validarDadosCidade(CidadeDTO dto) {
		if (Objects.isNull(dto.getNomeCidade())) {
			throw new BusinessException("O nome da cidade é obrigatório");
		}
		if (Objects.isNull(dto.getEstado())) {
			throw new BusinessException("O estado é obrigatório.");
		}

	}

	private boolean filtrarPorNomeEstado(String nome, String estado) {
		return Objects.nonNull(nome) && Objects.nonNull(estado);
	}

	private List<CidadeDTO> obterListaCidadeDTO(List<Cidade> cidades) {
		return cidades.stream().map(Cidade::obterDTO).collect(Collectors.toList());
	}
}
