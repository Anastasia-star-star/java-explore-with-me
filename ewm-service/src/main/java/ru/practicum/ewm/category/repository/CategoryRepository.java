package ru.practicum.ewm.category.repository;

import ru.practicum.ewm.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query("delete from Category c where c.id = ?1")
    Integer deleteByIdWithReturnedLines(Long catId);

}