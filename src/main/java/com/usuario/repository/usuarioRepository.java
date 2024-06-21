package com.usuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usuario.models.Usuario;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario, Integer> {


}
