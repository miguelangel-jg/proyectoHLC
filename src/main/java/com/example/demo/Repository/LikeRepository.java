package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

}
