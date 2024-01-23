package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

     // Endpoint to create a new withdrawal notice
    @PostMapping("/new_withdrawalNotice")
    public ResponseEntity<String> createWithdrawalNotice(@RequestBody WithdrawalNotice withdrawalNotice) {
        withdrawalNoticeService.createWithdrawalNotice(withdrawalNotice);
        return ResponseEntity.status(HttpStatus.CREATED).body("Withdrawal notice created successfully.");
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