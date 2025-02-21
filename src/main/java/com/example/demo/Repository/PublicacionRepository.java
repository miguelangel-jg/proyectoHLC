package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Publicacion;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {

}
