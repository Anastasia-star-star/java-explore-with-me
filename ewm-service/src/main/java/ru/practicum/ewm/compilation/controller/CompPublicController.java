package ru.practicum.ewm.compilation.controller;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompPublicService;

import java.util.List;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompPublicController {

    private final CompPublicService publicService;

    @GetMapping
    /**
     * Получение подборок событий.
     */
    public ResponseEntity<List<CompilationDto>> getAllCompilations(
            @RequestParam(required = false) Boolean pinned,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        List<CompilationDto> compilationDtos = publicService.getAllCompilations(pinned, from, size);
        log.info("Получена подборка событий, pinned = {}, from = {}, size = {}.", pinned, from, size);
        return ResponseEntity.ok().body(compilationDtos);
    }

    @GetMapping("/{compId}")
    /**
     * Получение подборки событий по его id.
     */
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable Long compId) {
        CompilationDto compilationDto = publicService.getCompilationById(compId);
        log.info("Получена подборка событий с id = {}.", compId);
        return ResponseEntity.ok(compilationDto);
    }

}