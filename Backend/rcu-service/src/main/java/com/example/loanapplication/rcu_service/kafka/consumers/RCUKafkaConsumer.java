package com.example.loanapplication.rcu_service.kafka.consumers;


import com.example.loanapplication.rcu_service.kafka.events.LoanStageChangedEvent;
import com.example.loanapplication.rcu_service.modules.rcumodule.service.RCUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RCUKafkaConsumer {

    @Autowired
    private RCUService rcuService;

    @KafkaListener(
            topics = "loan-stage-events",
            groupId = "rcu-group"
    )
    public void consume(LoanStageChangedEvent event) {

        System.out.println("Received event : " + event);

        try {

            if ("CREDIT_EVALUATION".equals(event.getStage())) {

                rcuService.CreateRCUCase(event.getLoanId());

                System.out.println("RCU CASE CREATED");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}