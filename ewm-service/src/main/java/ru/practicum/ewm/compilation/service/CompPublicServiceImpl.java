package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.util.UtilService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompPublicServiceImpl implements CompPublicService {

    private final CompilationRepository compilationRepository;
    private final UtilService utilService;

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id"));

        if (pinned == null) {
            return CompilationMapper.INSTANCE.convertCompilationListToCompilationDTOList(
                    compilationRepository.findAll(page).getContent());
        } else {
            return CompilationMapper.INSTANCE.convertCompilationListToCompilationDTOList(
                    compilationRepository.findAllByPinned(pinned, page));
        }
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        return CompilationMapper.INSTANCE.toCompilationDto(utilService.returnCompilation(compId));
    }

}