package com.skiply.school.service;

import com.skiply.school.data.Constants;
import com.skiply.school.data.PurchaseDetails;
import com.skiply.school.data.ReceiptResponse;
import com.skiply.school.data.TransactionDetails;
import com.skiply.school.entity.Receipt;
import com.skiply.school.entity.Student;
import com.skiply.school.exception.InvalidDataException;
import com.skiply.school.repository.GradesRepository;
import com.skiply.school.repository.ReceiptRepository;
import com.skiply.school.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class ReceiptService {
    private static final Logger log = LoggerFactory.getLogger(ReceiptService.class);
    ReceiptRepository receiptRepository;
    StudentRepository studentRepository;
    GradesRepository gradesRepository;

    public ReceiptResponse collectFee(Receipt receipt) {
        Long studentId = receipt.getStudentId();
        if(studentId == null) {
            log.error(Constants.STUDENT_ID_CANNOT_BE_NULL);
            throw new InvalidDataException(Constants.STUDENT_ID_CANNOT_BE_NULL);
        }
        Pattern pattern = Pattern.compile("^(?:\\d[ -]*?){13,16}$");
        Matcher matcher = pattern.matcher(receipt.getCardNumber());
        if(!matcher.matches()) {
            log.error("Invalid card number");
            throw new InvalidDataException("Invalid card number it should be between 13-16 digits");
        }
        Optional<Student> student = getStudent(studentId);

        Double fee = gradesRepository.findFeesByGrade(Integer.parseInt(student.get().getGrade()));
        Double fees = fee !=null ? fee:0;

        Receipt receiptDto = setReceiptInput(receipt, studentId,fees);

        Receipt receiptDtoResponse = receiptRepository.save(receiptDto);

        return setReceiptResponse(student, receiptDtoResponse, fees);
    }

    private ReceiptResponse setReceiptResponse(Optional<Student> student, Receipt receiptDtoResponse, Double fees) {
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setGrade(student.get().getGrade());
        receiptResponse.setSchoolName(student.get().getSchoolName());
        receiptResponse.setStudentName(student.get().getStudentName());

        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setCardNumber(receiptDtoResponse.getCardNumber());
        transactionDetails.setStudentName(student.get().getStudentName());
        setTxnDetails(transactionDetails, receiptDtoResponse);

        PurchaseDetails purchaseDetails = getPurchaseDetails(receiptDtoResponse, fees);

        receiptResponse.setTransactionDetails(transactionDetails);
        receiptResponse.setPurchaseDetails(purchaseDetails);
        receiptResponse.setEmail(Constants.MAIL);
        receiptResponse.setSuccess(Constants.SUCCESSFUL);
        return receiptResponse;
    }

    private Optional<Student> getStudent(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isEmpty()) {
            log.error(Constants.STUDENT_NOT_FOUND);
            throw new InvalidDataException(Constants.STUDENT_NOT_FOUND);
        }
        return student;
    }

    private void setTxnDetails(TransactionDetails transactionDetails, Receipt receiptDtoResponse) {
        transactionDetails.setDateOfTxn(receiptDtoResponse.getPaymentDate());
        transactionDetails.setStudentId(receiptDtoResponse.getStudentId());
        transactionDetails.setCardType(receiptDtoResponse.getCardType());
        transactionDetails.setRefNumber(receiptDtoResponse.getRefNumber());
    }

    private PurchaseDetails getPurchaseDetails(Receipt receiptDtoResponse, Double fees) {
        PurchaseDetails purchaseDetails = new PurchaseDetails();
        purchaseDetails.setCurrency(receiptDtoResponse.getCurrency());
        purchaseDetails.setTuitionFees(fees);
        purchaseDetails.setCustomAmount(receiptDtoResponse.getCustomAmount());
        purchaseDetails.setTotalAmount(receiptDtoResponse.getTotalAmount());
        return purchaseDetails;
    }

    private Receipt setReceiptInput(Receipt receipt, Long studentId, Double fees) {
        Receipt receiptInput = new Receipt();
        receiptInput.setStudentId(studentId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
        receiptInput.setPaymentDate(LocalDateTime.now().format(formatter));
        receiptInput.setCardNumber(receipt.getCardNumber());
        receiptInput.setCardType(receipt.getCardType());
        receiptInput.setCustomAmount(receipt.getCustomAmount());
        receiptInput.setTotalAmount(fees+ receipt.getCustomAmount());
        receiptInput.setFees(fees);
        receiptInput.setRefNumber(UUID.randomUUID().toString());
        receiptInput.setCurrency(receipt.getCurrency());
        return receiptInput;
    }

    public ReceiptResponse getReceipt(Long studentId) {
        Optional<Student> student = getStudent(studentId);
        Receipt receipt = receiptRepository.findById(studentId)
                .orElseThrow(() -> new InvalidDataException("Receipt not found for student ID: " + studentId));
        Double fees = receipt.getFees();
        return setReceiptResponse(student, receipt,fees);
    }
}
