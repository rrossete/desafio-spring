package com.example.desafiospring.cidade;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

	List<Cidade> findByEstado(String estado);
	List<Cidade> findByNome(String nome);
	
	List<Cidade> findByNomeAndEstado(String nome, String estado);
}
