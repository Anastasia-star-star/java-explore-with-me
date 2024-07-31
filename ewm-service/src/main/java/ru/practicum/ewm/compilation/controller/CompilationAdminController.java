package ru.practicum.ewm.compilation.controller;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.service.CompilationAdminService;

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
@Validated
public class CompilationAdminController {

    private final CompilationAdminService adminService;

    @PostMapping
    public ResponseEntity<CompilationDto> saveCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("save new compilation");
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.saveCompilation(newCompilationDto));
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteCompilationById(@PathVariable Long compId) {
        log.info("delete compilation by id = {}", compId);
        return adminService.deleteCompilationById(compId);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(
            @PathVariable Long compId, @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("update compilation by id = {}", compId);
        return ResponseEntity.ok(adminService.updateCompilation(compId, updateCompilationRequest));
    }

}