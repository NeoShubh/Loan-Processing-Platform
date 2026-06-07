package com.example.loanapplication.loan_service.kafka.consumers;


import com.example.loanapplication.loan_service.external.services.RCUService;
import com.example.loanapplication.loan_service.kafka.events.LoanStageChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RCUKafkaConsumer {

    @Autowired
    private RCUService rcuService;

    @KafkaListener(topics = "loan-stage-events", groupId = "rcu-group")
    public void consume(LoanStageChangedEvent event) {

        System.out.println("Received event: " + event);

        if (event.getStage().equals("CREDIT_EVALUATION")) {
            rcuService.CreateRCUCase(String.valueOf(event.getLoanId()));
        }
    }
}
