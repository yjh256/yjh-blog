package com.yjh.blog.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> { // JpaRepository<Entity class, PK type>

    @Query(value = "SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
    Page<Posts> findAll(Pageable pageable);
    Page<Posts> findByClassification(Pageable pageable, String classification);
    Page<Posts> findByTitleContaining(Pageable pageable, String keyword);
    Page<Posts> findByTitleContainingAndClassification(Pageable pageable, String keyword, String classification);

    @Modifying
    @Query("update Posts p set p.view=p.view+1 where p.id=:id")
    int updateView(@Param("id") Long id);
}
