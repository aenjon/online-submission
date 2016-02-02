package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/30
 * Time: 23:37
 * Version: 1.0
 */
public interface AuthorRepository extends JpaRepository<OsAuthor, Integer> {
}