package ru.practicum.ewm.category;

import ru.practicum.ewm.BaseDataJpaTest;
import ru.practicum.ewm.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CategoryRepositoryTest extends BaseDataJpaTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void deleteByIdWithReturnedLines() {
    }

}