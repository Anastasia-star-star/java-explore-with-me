package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotSaveException;
import ru.practicum.ewm.util.UtilService;

import java.util.HashSet;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final UtilService utilService;

    @Override
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        Set<Event> events = new HashSet<>();
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            events.addAll(eventRepository.findAllById(newCompilationDto.getEvents()));
        }

        try {
            Compilation compilation = compilationRepository.save(
                    CompilationMapper.INSTANCE.toCompilationFromNewDto(newCompilationDto, events));
            return CompilationMapper.INSTANCE.toCompilationDto(compilation);
        } catch (DataIntegrityViolationException e) {
            throw new NotSaveException("Compilation could not be saved: " + newCompilationDto);
        }
    }

    @Override
    public Boolean deleteCompilationById(Long compId) {
        utilService.returnCompilation(compId);
        try {
            compilationRepository.deleteById(compId);
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Failed to delete compilation with ID: " + compId);
        }
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = utilService.returnCompilation(compId);

        if (updateCompilationRequest.getEvents() != null) {
            Set<Event> events = new HashSet<>(eventRepository.findAllById(updateCompilationRequest.getEvents()));
            compilation.setEvents(events);
        }
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getTitle() != null && !updateCompilationRequest.getTitle().isBlank()) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }

        try {
            Compilation updatedCompilation = compilationRepository.save(compilation);
            return CompilationMapper.INSTANCE.toCompilationDto(updatedCompilation);
        } catch (DataIntegrityViolationException e) {
            throw new NotSaveException("Compilation could not be updated with ID: " + compId);
        }
    }
}
