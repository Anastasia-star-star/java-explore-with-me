package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.util.UtilService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CatPublicServiceImpl implements CatPublicService {

    private final CategoryRepository categoryRepository;
    private final UtilService utilService;

    @Override
    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        return CategoryMapper.INSTANCE.convertCategoryListToCategoryDTOList(
                categoryRepository.findAll(page).getContent());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        return CategoryMapper.INSTANCE.toCategoryDto(utilService.returnCategory(catId));
    }

}