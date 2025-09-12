package com.dayaeyak.exhibition.domain.exhibition;

import com.dayaeyak.exhibition.common.constraints.ExhibitionResponseMessage;
import com.dayaeyak.exhibition.common.entity.ApiResponse;
import com.dayaeyak.exhibition.domain.exhibition.dto.request.ExhibitionUpdateRequestDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionFindResponseDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionUpdateActivationResponseDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionUpdateResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{exhibitionId}")
    public ResponseEntity<ApiResponse<ExhibitionUpdateResponseDto>> updateExhibition(
            @PathVariable Long exhibitionId,
            @RequestBody @Valid ExhibitionUpdateRequestDto exhibitionUpdateRequestDto,
            @RequestHeader("X-User-Id") Long userId
    ) {
        ExhibitionUpdateResponseDto data = exhibitionService.updateExhibition(exhibitionId, exhibitionUpdateRequestDto, userId);

        return ApiResponse.success(HttpStatus.OK, ExhibitionResponseMessage.UPDATE, data);
    }

    @PatchMapping("/{exhibitionId}/activation")
    public ResponseEntity<ApiResponse<ExhibitionUpdateActivationResponseDto>> updateExhibitionActivation(
            @PathVariable Long exhibitionId,
            @RequestHeader("X-User-Id") Long userId
    ) {
        ExhibitionUpdateActivationResponseDto data = exhibitionService.updateExhibitionActivation(exhibitionId, userId);

        return ApiResponse.success(HttpStatus.OK, ExhibitionResponseMessage.ACTIVATION_UPDATE, data);
    }
}
