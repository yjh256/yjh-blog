package com.yjh.book.springboot.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query("SELECT c from Comments c where c.post.id=post_id and c.id>0 order by c.id DESC")
    public List<Comments> getCommentsOfPost(@Param("post_id") Long postNo);
}
