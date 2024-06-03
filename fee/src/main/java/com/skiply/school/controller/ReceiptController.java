package com.skiply.school.controller;

import com.skiply.school.data.ReceiptResponse;
import com.skiply.school.entity.Receipt;
import com.skiply.school.service.ReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/fees")
public class ReceiptController {

    ReceiptService receiptService;

    @PostMapping(value = "/collectFees", produces = "application/json")
    @Operation(summary = "Collect fees")
    @ApiResponse(responseCode = "400", description = "Invalid student details",
            content = @Content)
    @ApiResponse(responseCode = "200", description = "Fees transaction is successful",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReceiptResponse.class)) })
    public ReceiptResponse collectFee(@Valid @RequestBody Receipt receipt) {
        return receiptService.collectFee(receipt);
    }

    @GetMapping(value = "/receipt")
    @ApiResponse(responseCode = "400", description = "Invalid student details",
            content = @Content)
    @ApiResponse(responseCode = "200", description = "Found receipt of the student",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReceiptResponse.class)) })
    public ReceiptResponse getAllReceipts(@RequestParam int studentId) {
        return receiptService.getReceipt((long) studentId);
    }
}
