package ru.practicum.ewm.category.controller;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.service.CategoryAdminService;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController {

    private final CategoryAdminService adminService;

    @PostMapping
    @Validated
    public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Adding new category");
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.saveCategory(newCategoryDto));
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Boolean> deleteCategoryById(@PathVariable Long catId) {
        log.info("Deleting category by id = {}", catId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(adminService.deleteCategoryById(catId));
    }

    @PatchMapping("/{catId}")
    @Validated
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long catId, @Valid @RequestBody CategoryDto categoryDto) {
        log.info("Updating categ by id {}", catId);
        return ResponseEntity.ok(adminService.updateCategory(catId, categoryDto));
    }

}