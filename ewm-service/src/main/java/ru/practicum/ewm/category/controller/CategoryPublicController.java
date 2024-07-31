package ru.practicum.ewm.category.controller;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryPublicService;

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
public class CategoryPublicController {

    private final CategoryPublicService publicService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Getting list of categ from = {}, size = {}.", from, size);
        return ResponseEntity.ok().body(publicService.getAllCategories(from, size));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long catId) {
        log.info("Getting categ by id = {}", catId);
        return ResponseEntity.ok(publicService.getCategoryById(catId));
    }

}