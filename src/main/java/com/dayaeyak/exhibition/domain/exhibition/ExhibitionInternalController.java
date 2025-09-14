package com.dayaeyak.exhibition.domain.exhibition;

import com.dayaeyak.exhibition.domain.exhibition.dto.request.ExhibitionCreateRequestDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/exhibitions")
@RequiredArgsConstructor
public class ExhibitionInternalController {

    private final ExhibitionInternalService exhibitionInternalService;

    @PostMapping
    public ExhibitionCreateResponseDto createExhibition(
            @RequestBody ExhibitionCreateRequestDto exhibitionCreateRequestDto
    ) {
        return exhibitionInternalService.createExhibition(exhibitionCreateRequestDto);
    }
}
