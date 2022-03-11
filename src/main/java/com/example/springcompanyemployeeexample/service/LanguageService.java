package com.example.springcompanyemployeeexample.service;

import com.example.springcompanyemployeeexample.entity.Language;
import com.example.springcompanyemployeeexample.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;


    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    public Language getById(Integer id) {
        return languageRepository.getById(id);
    }
}
