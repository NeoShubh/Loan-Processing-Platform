package com.example.loanapplication.loan_service.kafka.consumers;


import com.example.loanapplication.loan_service.kafka.events.LoanStageHistoryEvent;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LoanStageHistoryKafkaConsumer {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @KafkaListener(topics = "loan-stage-history-events", groupId = "loan-stage-history-group")
    public void consume(LoanStageHistoryEvent event) {

        System.out.println("Received event: " + event);

        loanApplicationService.createLoanStageHistory(event.getLoanID(), event.getUserID(), event.getLoanStageHistoryRequestDTO());
    }
}
