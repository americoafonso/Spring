package com.example.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Categoria;

/*A intereface JpaRepository ja nos concede todos os metodos basicos de um CRUD*/
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
