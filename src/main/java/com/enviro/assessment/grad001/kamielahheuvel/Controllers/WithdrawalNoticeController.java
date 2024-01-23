package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.enviro.assessment.grad001.kamielahheuvel.Models.AppExceptions;
import com.enviro.assessment.grad001.kamielahheuvel.Models.WithdrawalNotice;
import com.enviro.assessment.grad001.kamielahheuvel.Services.WithdrawalNoticeService;

@RestController
@RequestMapping("/withdrawals")
public class WithdrawalNoticeController {

    @Autowired
    private WithdrawalNoticeService withdrawalNoticeService;

    // Hello endpoint for testing
    @GetMapping("/hello")
    public String hello() {
    return "Hello, this is the WithdrawalNoticeController!";
    }

   // Endpoint to show the new withdrawal notice form
    @GetMapping("/new_withdrawalNotice")
    public ModelAndView showNewWithdrawalNoticeForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new_withdrawalNotice");
        return modelAndView;
    }

    // Endpoint to create a new withdrawal notice
    @PostMapping("/new_withdrawalNotice.action")
    public ResponseEntity<WithdrawalNotice> createNewWithdrawalNotice(
            @RequestParam BigDecimal withdrawalAmount,
            @RequestParam String date,
            @RequestParam String bankingDetails) {

        try {
            // Convert the date string to LocalDateTime, adjust as needed
            LocalDateTime parsedDate = LocalDateTime.parse(date);

            // Create a new instance of WithdrawalNotice using the provided request parameters
            WithdrawalNotice newWithdrawalNotice = new WithdrawalNotice(withdrawalAmount, parsedDate, bankingDetails);

            // Call the service method to create the new withdrawal notice
            WithdrawalNotice createdWithdrawalNotice = withdrawalNoticeService.createWithdrawalNotice(newWithdrawalNotice);

            // Return the created withdrawal notice in the response
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWithdrawalNotice);
        } catch (DateTimeParseException | AppExceptions e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    // Endpoint to get all withdrawal notices
    @GetMapping("/allWithdrawals")
    public ResponseEntity<List<WithdrawalNotice>> getAllWithdrawalNotices() {
        List<WithdrawalNotice> withdrawalNotices = withdrawalNoticeService.getAllWithdrawalNotices();
        return ResponseEntity.ok(withdrawalNotices);
    }

    // Endpoint to get a specific withdrawal notice by ID
    @GetMapping("/id/{withdrawalId}")
    public ResponseEntity<WithdrawalNotice> getWithdrawalNoticeById(@PathVariable Long withdrawalId) {
        WithdrawalNotice withdrawalNotice = withdrawalNoticeService.getWithdrawalNoticeById(withdrawalId);
        return ResponseEntity.ok(withdrawalNotice);
    }

    // Endpoint to update an existing withdrawal notice
    @PutMapping("/update/{withdrawalId}")
    public ResponseEntity<String> updateWithdrawalNotice(@PathVariable Long withdrawalId, @RequestBody WithdrawalNotice updatedWithdrawalNotice) {
        withdrawalNoticeService.updateWithdrawalNotice(withdrawalId, updatedWithdrawalNotice);
        return ResponseEntity.ok("Withdrawal notice updated successfully.");
    }

    // Endpoint to delete a withdrawal notice by ID
    @DeleteMapping("/delete/{withdrawalId}")
    public ResponseEntity<String> deleteWithdrawalNotice(@PathVariable Long withdrawalId) {
        withdrawalNoticeService.deleteWithdrawalNotice(withdrawalId);
        return ResponseEntity.ok("Withdrawal notice deleted successfully.");
    }
}