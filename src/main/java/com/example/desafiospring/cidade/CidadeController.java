package com.example.desafiospring.cidade;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/cidades")
@Api(value = "Cidade")
public class CidadeController {
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	@ApiOperation(value = "Retorna uma lista com todas as cidades cadastradas.")
	public ResponseEntity<List<CidadeDTO>> getCidades(){
		return ResponseEntity.ok().body(this.cidadeService.getCidades());
	}
	
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Restorna o cidade pelo ID.")
	public ResponseEntity<CidadeDTO> getProdutoById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(this.cidadeService.getById(id));

	}
	
	@GetMapping(path = "/filtrar")
	@ApiOperation(value = "Restorna o cidade a partir do filtro passado.")
	public ResponseEntity<List<CidadeDTO>> getProdutoByNome(@RequestParam(name = "nome", required = false) String nome, @RequestParam(name = "estado", required = false) String estado) {
		return ResponseEntity.ok().body(this.cidadeService.getByFiltro(nome, estado));
		
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Edita o cidade selecionado.")
	public ResponseEntity<Void> atualizarProduto(@RequestBody CidadeDTO dto, @PathVariable Integer id) {
		this.cidadeService.atualizar(dto, id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Deleta o cidade pelo ID.")
	public ResponseEntity<Void> atualizarProduto(@PathVariable Integer id) {
		this.cidadeService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	@ApiOperation(value = "Cadastra uma nova cidade.")
	public ResponseEntity<Void> novo(@RequestBody CidadeDTO cidadeDto) {

		Cidade cidade = this.cidadeService.novo(cidadeDto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cidade.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

}
