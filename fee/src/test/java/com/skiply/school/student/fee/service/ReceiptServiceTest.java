package com.skiply.school.student.fee.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.skiply.school.data.ReceiptResponse;
import com.skiply.school.entity.Receipt;
import com.skiply.school.entity.Student;
import com.skiply.school.exception.InvalidDataException;
import com.skiply.school.repository.GradesRepository;
import com.skiply.school.repository.ReceiptRepository;
import com.skiply.school.repository.StudentRepository;
import com.skiply.school.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GradesRepository gradesRepository;

    @InjectMocks
    private ReceiptService receiptService;

    @Test
    public void testCollectFee() {
        // Mock student object
        Student student = new Student();
        student.setStudentName("John");
        student.setGrade("10");
        student.setSchoolName("XYZ School");

        double feeValue = 100.0;
        when(gradesRepository.findFeesByGrade(10)).thenReturn(feeValue);

        Receipt receipt = new Receipt();
        receipt.setStudentId(1L);
        receipt.setCardType("VISA");
        receipt.setCustomAmount(50.0);
        receipt.setCurrency("USD");
        receipt.setCardNumber("1234-1111-1111-11");

        when(receiptRepository.save(any(Receipt.class))).thenAnswer(invocation -> invocation.<Receipt>getArgument(0));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        ReceiptResponse receiptResponse = receiptService.collectFee(receipt);


        assertNotNull(receiptResponse);
        assertEquals(student.getStudentName(), receiptResponse.getStudentName());
        assertEquals(student.getSchoolName(), receiptResponse.getSchoolName());
        assertEquals(student.getGrade(), receiptResponse.getGrade());
        assertEquals(receipt.getCardNumber(), receiptResponse.getTransactionDetails().getCardNumber());
        assertEquals(receipt.getCardType(), receiptResponse.getTransactionDetails().getCardType());
        assertEquals(receipt.getCustomAmount(), receiptResponse.getPurchaseDetails().getCustomAmount());
        assertEquals("Your transaction is Successful", receiptResponse.getSuccess());
    }

    @Test
    public void testGetReceipt() {

        Student student = new Student();

        student.setStudentName("John");
        student.setGrade("10");
        student.setSchoolName("XYZ School");

        Receipt receipt = new Receipt();
        receipt.setStudentId(1L);
        receipt.setCardType("VISA");
        receipt.setCustomAmount(50.0);
        receipt.setCurrency("USD");
        receipt.setFees(100.0);
        receipt.setPaymentDate(LocalDateTime.now().toString());
        receipt.setRefNumber("ref123");

        // Mock repository behavior
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt));
        ReceiptResponse receiptResponse = receiptService.getReceipt(1L);

        assertNotNull(receiptResponse);
        assertEquals(student.getStudentName(), receiptResponse.getStudentName());
        assertEquals(student.getSchoolName(), receiptResponse.getSchoolName());
        assertEquals(student.getGrade(), receiptResponse.getGrade());
        assertEquals(receipt.getCardNumber(), receiptResponse.getTransactionDetails().getCardNumber());
        assertEquals(receipt.getCardType(), receiptResponse.getTransactionDetails().getCardType());
        assertEquals(receipt.getCustomAmount(), receiptResponse.getPurchaseDetails().getCustomAmount());
        assertEquals("Your transaction is Successful", receiptResponse.getSuccess());
    }

    @Test
    public void testGetReceiptWithNullStudentId() {
        Receipt receipt = new Receipt();
        receipt.setStudentId(1L);
        receipt.setCardType("VISA");
        receipt.setCustomAmount(50.0);
        receipt.setCurrency("USD");
        receipt.setFees(100.0);
        receipt.setPaymentDate(LocalDateTime.now().toString());
        receipt.setRefNumber("ref123");
        receipt.setCardNumber("1234-1111-1111-11");

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(InvalidDataException.class, () -> receiptService.collectFee(receipt));
    }

    @Test
    public void testGetReceiptWithInvalidCardNumber() {
        Receipt receipt = new Receipt();
        receipt.setStudentId(1L);
        receipt.setCardType("VISA");
        receipt.setCustomAmount(50.0);
        receipt.setCurrency("USD");
        receipt.setFees(100.0);
        receipt.setPaymentDate(LocalDateTime.now().toString());
        receipt.setRefNumber("ref123");
        receipt.setCardNumber("1234-1111-1111");
        assertThrows(InvalidDataException.class, () -> receiptService.collectFee(receipt));
    }

    @Test
    public void testGetReceiptWithEmptyStudentId() {
        Receipt receipt = new Receipt();
        receipt.setCardType("VISA");
        receipt.setCustomAmount(50.0);
        receipt.setCurrency("USD");
        receipt.setFees(100.0);
        receipt.setPaymentDate(LocalDateTime.now().toString());
        receipt.setRefNumber("ref123");
        receipt.setCardNumber("1234-1111-1111");


        assertThrows(InvalidDataException.class, () -> receiptService.collectFee(receipt));
    }
}
