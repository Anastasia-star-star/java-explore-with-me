package ru.practicum.ewm.category.controller;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CatPublicService;

import java.util.List;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CatPublicController {

    private final CatPublicService publicService;

    @GetMapping
    /**
     * Получение категорий.
     */
    public ResponseEntity<List<CategoryDto>> getAllCategories(
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        List<CategoryDto> categoryDtos = publicService.getAllCategories(from, size);
        log.info("Получен список категорий, from = {}, size = {}.", from, size);
        return ResponseEntity.ok().body(categoryDtos);
    }

    @GetMapping("/{catId}")
    /**
     * Получение информации о категории по её идентификатору.
     */
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long catId) {
        CategoryDto categoryDto = publicService.getCategoryById(catId);
        log.info("Получена категория с id = {}.", catId);
        return ResponseEntity.ok(categoryDto);
    }

}