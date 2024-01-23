package com.enviro.assessment.grad001.kamielahheuvel.Services;

import com.enviro.assessment.grad001.kamielahheuvel.Models.AppExceptions;
import com.enviro.assessment.grad001.kamielahheuvel.Models.WithdrawalNotice;
import com.enviro.assessment.grad001.kamielahheuvel.Respositories.WithdrawalNoticeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WithdrawalNoticeService {

    @Autowired
    private WithdrawalNoticeRepository withdrawalNoticeRepository;

    @Autowired
    private NotificationServcie notificationServcie;

    @Transactional
    public void createWithdrawalNotice(WithdrawalNotice withdrawalNotice) {
        // Validate withdrawal and perform necessary checks
        validateWithdrawal(withdrawalNotice.getId());

        // Save the withdrawal notice
        withdrawalNoticeRepository.save(withdrawalNotice);

        // Notify the investor
        notifyInvestor(withdrawalNotice);
    }


    public void validateWithdrawal(Long withdrawalNoticeId) {
        WithdrawalNotice withdrawalNotice = withdrawalNoticeRepository.findById(withdrawalNoticeId).orElse(null);

        if (withdrawalNotice == null) {
           throw new AppExceptions("Withdrawl Notice with" + withdrawalNoticeId + " not found");
        }

        // Validate PRODUCT is RETIREMENT and investor's age is greater than 65
        validateProductAndAge(withdrawalNotice);

        // Validate WITHDRAWAL AMOUNT is greater than current BALANCE
        validateWithdrawalAmount(withdrawalNotice);

        // Validate Investors cannot withdraw an AMOUNT more than 90% of the current BALANCE
        validateWithdrawalLimit(withdrawalNotice);
    }

    private void validateProductAndAge(WithdrawalNotice withdrawalNotice) {
        String productType = withdrawalNotice.getProduct().getType();
        Integer age = withdrawalNotice.getInvestor().getAge();

        if ("RETIREMENT".equalsIgnoreCase(productType) && isInvestorAgeGreaterThan65(age)) {
            throw new AppExceptions("Investor must at least be 66 years old for RETIREMENT ");
        }
    }

    private boolean isInvestorAgeGreaterThan65(Integer age) {
        return age != null && age > 65;
    }

    private void validateWithdrawalAmount(WithdrawalNotice withdrawalNotice) {
        BigDecimal withdrawalAmount = withdrawalNotice.getWithdrawalAmount();
        BigDecimal currentBalance =withdrawalNotice.getCurrentBalance(); 

        if (withdrawalAmount.compareTo(currentBalance) > 0) {
            throw new AppExceptions("Withdrawal amount is greater than current balance");
        }
    }

    private void validateWithdrawalLimit(WithdrawalNotice withdrawalNotice) {
        BigDecimal withdrawalAmount = withdrawalNotice.getWithdrawalAmount();
        BigDecimal currentBalance = withdrawalNotice.getCurrentBalance(); 

        BigDecimal withdrawalLimit = currentBalance.multiply(BigDecimal.valueOf(0.9));
        if (withdrawalAmount.compareTo(withdrawalLimit) > 0) {
            throw new AppExceptions("Investors cannot withdraw an AMOUNT more than 90% of the current BALANCE");
        }
    }

    private void notifyInvestor(WithdrawalNotice withdrawalNotice) {
        String notificationMessage = buildNotificationMessage(withdrawalNotice);

        notificationServcie.sendNotification(withdrawalNotice.getInvestor().getName(), "Withdrawal Notice", notificationMessage);

    }

    private String buildNotificationMessage(WithdrawalNotice withdrawalNotice) {
        BigDecimal currentBalance = withdrawalNotice.getCurrentBalance(); 

        return "Withdrawal Notice:\n" +
                "Balance before withdrawal: " + currentBalance + "\n" +
                "Amount withdrawn: " + withdrawalNotice.getWithdrawalAmount() + "\n" +
                "Closing balance: " + calculateClosingBalance(currentBalance, withdrawalNotice.getWithdrawalAmount());
    }

    private BigDecimal calculateClosingBalance(BigDecimal currentBalance, BigDecimal withdrawalAmount) {
        return currentBalance.subtract(withdrawalAmount);
    }

}
