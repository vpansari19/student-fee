package com.skiply.school.student.fee.controller;

import com.skiply.school.controller.ReceiptController;
import com.skiply.school.data.ReceiptResponse;
import com.skiply.school.entity.Receipt;
import com.skiply.school.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiptControllerTest {
    @Mock
    ReceiptService receiptService;

    @InjectMocks
    ReceiptController receiptController;

    @Test
    public void testCollectFees() {
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setEmail("xyz@abc.com");
        receiptResponse.setGrade("12");
        receiptResponse.setStudentName("test");

        Receipt receipt = new Receipt();
        receipt.setCurrency("AED");

        when(receiptService.collectFee(any(com.skiply.school.entity.Receipt.class))).thenReturn(receiptResponse);
        ReceiptResponse resp = receiptController.collectFee(receipt);
        assertEquals(resp.getStudentName(), "test");
    }

    @Test
    public void testGetReceipt() {
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setEmail("xyz@abc.com");
        receiptResponse.setGrade("12");
        receiptResponse.setStudentName("test");

        when(receiptService.getReceipt(any())).thenReturn(receiptResponse);
        ReceiptResponse resp = receiptController.getAllReceipts(1);
        assertEquals(resp.getStudentName(), "test");
    }
}
