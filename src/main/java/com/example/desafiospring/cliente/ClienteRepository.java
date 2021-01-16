package com.example.desafiospring.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.desafiospring.cidade.Cidade;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	 List<Cliente> findByCidade(Cidade cidade);
	 
	 List<Cliente> findByNome(String nome);
}
