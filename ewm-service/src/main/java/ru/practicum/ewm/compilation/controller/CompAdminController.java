package ru.practicum.ewm.compilation.controller;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.service.CompAdminService;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompAdminController {

    private final CompAdminService adminService;

    @PostMapping
    @Validated
    /**
     * Добавление новой подборки.
     */
    public ResponseEntity<CompilationDto> saveCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        CompilationDto compilationDto = adminService.saveCompilation(newCompilationDto);
        log.info("Добавлена новая подборка: {}", compilationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    /**
     * Удаление подборки.
     */
    public void deleteCompilationById(@PathVariable Long compId) {
        adminService.deleteCompilationById(compId);
        log.info("Удалена подборка с id = {}", compId);
    }

    @PatchMapping("/{compId}")
    @Validated
    /**
     * Обновить информацию о подборке.
     */
    public ResponseEntity<CompilationDto> updateCompilation(
            @PathVariable Long compId, @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        CompilationDto compilationDto = adminService.updateCompilation(compId, updateCompilationRequest);
        log.info("Обновлена информация о подборке с id = {}: {}.", compId, updateCompilationRequest);
        return ResponseEntity.ok(compilationDto);
    }

}