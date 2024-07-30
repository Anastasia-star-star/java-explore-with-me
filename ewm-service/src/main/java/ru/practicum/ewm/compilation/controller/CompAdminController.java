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
    public ResponseEntity<CompilationDto> saveCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("save new compilation");
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.saveCompilation(newCompilationDto));
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationById(@PathVariable Long compId) {
        log.info("delete compilation by id = {}", compId);
        adminService.deleteCompilationById(compId);
    }

    @PatchMapping("/{compId}")
    @Validated
    public ResponseEntity<CompilationDto> updateCompilation(
            @PathVariable Long compId, @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("update compilation by id = {}", compId);
        return ResponseEntity.ok(adminService.updateCompilation(compId, updateCompilationRequest));
    }

}