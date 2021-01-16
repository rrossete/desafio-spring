package com.example.desafiospring.cliente;

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
@RequestMapping("/clientes")
@Api(value = "Clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	@ApiOperation(value = "Restorna uma lista com os clientes cadastrados.")
	public ResponseEntity<List<ClienteDTO>> getClientes() {
		return ResponseEntity.ok().body(clienteService.getClientes());
	}

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Restorna o cliente pelo ID.")
	public ResponseEntity<ClienteDTO> getProdutoById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(this.clienteService.getById(id));

	}
	
	@GetMapping(path = "/filtro")
	@ApiOperation(value = "Restorna o cliente pelo nome.")
	public ResponseEntity<List<ClienteDTO>> getProdutoByNome(@RequestParam(name = "nome") String nome) {
		return ResponseEntity.ok().body(this.clienteService.getByNome(nome));
		
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Edita o cliente selecionado.")
	public ResponseEntity<Void> atualizarProduto(@RequestBody ClienteDTO dto, @PathVariable Integer id) {
		this.clienteService.atualizar(dto, id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Deleta o cliente pelo ID.")
	public ResponseEntity<Void> atualizarProduto(@PathVariable Integer id) {
		this.clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	@ApiOperation(value = "Cadastra um novo cliente.")
	public ResponseEntity<Void> novo(@RequestBody ClienteDTO clienteDto) {

		Cliente cliente = this.clienteService.novo(clienteDto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

}
