package com.dayaeyak.exhibition.domain.exhibition;

import com.dayaeyak.exhibition.common.constraints.ExhibitionResponseMessage;
import com.dayaeyak.exhibition.common.entity.ApiResponse;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionFindResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @GetMapping("/{exhibitionId}")
    public ResponseEntity<ApiResponse<ExhibitionFindResponseDto>> getExhibition(
            @PathVariable Long exhibitionId
    ) {
        ExhibitionFindResponseDto data = exhibitionService.findExhibition(exhibitionId);

        return ApiResponse.success(HttpStatus.OK, ExhibitionResponseMessage.FIND, data);
    }
}
