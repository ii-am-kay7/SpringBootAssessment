package com.enviro.assessment.grad001.kamielahheuvel.Services;

import com.enviro.assessment.grad001.kamielahheuvel.Models.AppExceptions;
import com.enviro.assessment.grad001.kamielahheuvel.Models.WithdrawalNotice;
import com.enviro.assessment.grad001.kamielahheuvel.Respositories.WithdrawalNoticeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalNoticeService {

    @Autowired
    private WithdrawalNoticeRepository withdrawalNoticeRepository;

    @Autowired
    private NotificationServcie notificationServcie;

    // Transactional method to create a new withdrawal notice
    @Transactional
    public WithdrawalNotice createWithdrawalNotice(WithdrawalNotice withdrawalNotice) {
        // Validate withdrawal and perform necessary checks
        validateWithdrawal(withdrawalNotice.getId());

        // Notify the investor
        notifyInvestor(withdrawalNotice);

         // Save the withdrawal notice
        return withdrawalNoticeRepository.save(withdrawalNotice);
    }

    // Validate withdrawal details
    public void validateWithdrawal(Long withdrawalNoticeId) {
        WithdrawalNotice withdrawalNotice = withdrawalNoticeRepository.findById(withdrawalNoticeId).orElse(null);

        if (withdrawalNotice == null) {
            throw new AppExceptions("Withdrawal Notice with ID " + withdrawalNoticeId + " not found");
        }

        // Validate PRODUCT is RETIREMENT and investor's age is greater than 65
        validateProductAndAge(withdrawalNotice);

        // Validate WITHDRAWAL AMOUNT is greater than current BALANCE
        validateWithdrawalAmount(withdrawalNotice);

        // Validate Investors cannot withdraw an AMOUNT more than 90% of the current BALANCE
        validateWithdrawalLimit(withdrawalNotice);
    }

    // Validate PRODUCT is RETIREMENT and investor's age is greater than 65
    private void validateProductAndAge(WithdrawalNotice withdrawalNotice) {
        String productType = withdrawalNotice.getProduct().getType();
        Integer age = withdrawalNotice.getInvestor().getAge();

        if ("RETIREMENT".equalsIgnoreCase(productType) && isInvestorAgeGreaterThan65(age)) {
            throw new AppExceptions("Investor must be at least 66 years old for RETIREMENT");
        }
    }

    // Check if investor's age is greater than 65
    private boolean isInvestorAgeGreaterThan65(Integer age) {
        return age != null && age > 65;
    }

    // Validate WITHDRAWAL AMOUNT is greater than current BALANCE
    private void validateWithdrawalAmount(WithdrawalNotice withdrawalNotice) {
        BigDecimal withdrawalAmount = withdrawalNotice.getWithdrawalAmount();
        BigDecimal currentBalance = withdrawalNotice.getCurrentBalance();

        if (withdrawalAmount.compareTo(currentBalance) > 0) {
            throw new AppExceptions("Withdrawal amount is greater than current balance");
        }
    }

    // Validate Investors cannot withdraw an AMOUNT more than 90% of the current BALANCE
    private void validateWithdrawalLimit(WithdrawalNotice withdrawalNotice) {
        BigDecimal withdrawalAmount = withdrawalNotice.getWithdrawalAmount();
        BigDecimal currentBalance = withdrawalNotice.getCurrentBalance();

        BigDecimal withdrawalLimit = currentBalance.multiply(BigDecimal.valueOf(0.9));
        if (withdrawalAmount.compareTo(withdrawalLimit) > 0) {
            throw new AppExceptions("Investors cannot withdraw an AMOUNT more than 90% of the current BALANCE");
        }
    }

    // Notify the investor about the withdrawal
    private void notifyInvestor(WithdrawalNotice withdrawalNotice) {
        String notificationMessage = buildNotificationMessage(withdrawalNotice);

        notificationServcie.sendNotification(withdrawalNotice.getInvestor().getContact(), "Withdrawal Notice", notificationMessage);
    }

    // Build notification message with withdrawal details
    private String buildNotificationMessage(WithdrawalNotice withdrawalNotice) {
        BigDecimal currentBalance = withdrawalNotice.getCurrentBalance();

        return "Withdrawal Notice:\n" +
                "Balance before withdrawal: " + currentBalance + "\n" +
                "Amount withdrawn: " + withdrawalNotice.getWithdrawalAmount() + "\n" +
                "Closing balance: " + calculateClosingBalance(currentBalance, withdrawalNotice.getWithdrawalAmount());
    }

    // Calculate closing balance after withdrawal
    private BigDecimal calculateClosingBalance(BigDecimal currentBalance, BigDecimal withdrawalAmount) {
        return currentBalance.subtract(withdrawalAmount);
    }

    // Retrieve all withdrawal notices
    public List<WithdrawalNotice> getAllWithdrawalNotices() {
        return withdrawalNoticeRepository.findAll();
    }

    // Retrieve a specific withdrawal notice by ID
    public WithdrawalNotice getWithdrawalNoticeById(Long id) {
        Optional<WithdrawalNotice> optionalWithdrawalNotice = withdrawalNoticeRepository.findById(id);
        return optionalWithdrawalNotice.orElse(null);
    }

    // Update an existing withdrawal notice
    @Transactional
    public void updateWithdrawalNotice(Long id, WithdrawalNotice updatedWithdrawalNotice) {
        Optional<WithdrawalNotice> optionalExistingWithdrawalNotice = withdrawalNoticeRepository.findById(id);

        if (optionalExistingWithdrawalNotice.isPresent()) {
            WithdrawalNotice existingWithdrawalNotice = optionalExistingWithdrawalNotice.get();

            // Update fields of the existing withdrawal notice with the values from the updated withdrawal notice
            existingWithdrawalNotice.setWithdrawalAmount(updatedWithdrawalNotice.getWithdrawalAmount());
            existingWithdrawalNotice.setDate(updatedWithdrawalNotice.getDate());
            existingWithdrawalNotice.setBankingDetails(updatedWithdrawalNotice.getBankingDetails());

            // Save the updated withdrawal notice
            withdrawalNoticeRepository.save(existingWithdrawalNotice);
        }
    }

    // Delete a withdrawal notice by ID
    @Transactional
    public void deleteWithdrawalNotice(Long id) {
        withdrawalNoticeRepository.deleteById(id);
    }
}
