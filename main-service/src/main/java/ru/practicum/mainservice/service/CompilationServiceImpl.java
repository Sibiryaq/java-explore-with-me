package ru.practicum.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.dto.compilation.CompilationDTO;
import ru.practicum.mainservice.dto.compilation.CreateCompilationDTO;
import ru.practicum.mainservice.dto.compilation.UpdateCompilationDTO;
import ru.practicum.mainservice.exception.APIException;
import ru.practicum.mainservice.mapper.CompilationMapper;
import ru.practicum.mainservice.model.Compilation;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.repository.CompilationRepository;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.util.OffsetBasedPageRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDTO createCompilation(CreateCompilationDTO compilation) {
        Compilation newCompilation = new Compilation();
        Set<Integer> eventsIds = compilation.getEvents();
        if (eventsIds != null) {
            List<Event> events = eventRepository.findAllById(eventsIds);
            newCompilation.setEvents(new HashSet<>(events));
        }
        newCompilation.setTitle(compilation.getTitle());
        newCompilation.setPinned(compilation.getPinned());

        return compilationMapper.toDto(compilationRepository.save(newCompilation));
    }

    @Override
    @Transactional
    public CompilationDTO updateCompilation(int compilationId, UpdateCompilationDTO compilation) {
        Compilation compilationFromDB = getById(compilationId);
        Set<Integer> eventsIds = compilation.getEvents();
        if (eventsIds != null) {
            List<Event> events = eventRepository.findAllById(eventsIds);
            compilationFromDB.setEvents(new HashSet<>(events));
        }
        compilationFromDB.setTitle(compilation.getTitle());
        compilationFromDB.setPinned(compilation.getPinned());
        return compilationMapper.toDto(compilationFromDB);
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDTO getCompilationById(int compilationId) {
        Compilation compilation = getById(compilationId);
        return compilationMapper.toDto(compilation);
    }

    @Override
    @Transactional(readOnly = true)
    public Compilation getById(int compilationId) {
        return compilationRepository.findById(compilationId).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND,
                        String.format("Compilation with id=%s was not found", compilationId),
                        "The required object was not found."));
    }

    @Override
    @Transactional
    public void deleteCompilation(int compilationId) {
        Compilation compilation = getById(compilationId);
        compilationRepository.delete(compilation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDTO> getCompilations(Boolean pinned, int from, int size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        List<Compilation> res;
        if (Objects.nonNull(pinned)) res = compilationRepository.findAllByPinned(pinned, pageable);
        else res = compilationRepository.findAll(pageable).getContent();
        return res.stream().map(compilationMapper::toDto).collect(Collectors.toList());
    }
}