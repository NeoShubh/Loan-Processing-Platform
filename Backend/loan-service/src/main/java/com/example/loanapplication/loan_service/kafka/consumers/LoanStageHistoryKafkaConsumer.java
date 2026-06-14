package com.example.loanapplication.loan_service.kafka.consumers;


import com.example.loanapplication.loan_service.kafka.events.LoanStageHistoryEvent;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.service.LoanApplicationService;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LoanStageHistoryKafkaConsumer {


    private final LoanApplicationService loanApplicationService;

    public LoanStageHistoryKafkaConsumer(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

//    @PostConstruct
//    public void init() {
//        System.out.println("🔥🔥🔥 LoanStageHistoryKafkaConsumer Loaded 🔥🔥🔥");
//    }
    @KafkaListener(topics = "loan-stage-history-events", groupId = "loan-stage-history-group-test")
    public void consume(LoanStageHistoryEvent event) {
//
//        System.out.println("🔥 LISTENER HIT 🔥");
//        System.out.println(event);

        try {
            System.out.println("👉 BEFORE DB CALL");

            loanApplicationService.createLoanStageHistory(
                    event.getLoanID(),
                    event.getUserID(),
                    event.getLoanStageHistoryRequestDTO()
            );

            System.out.println("✅ AFTER DB CALL SUCCESS");
        }
        catch (Exception e) {
            System.out.println("ERROR IN DB CALL");
            e.printStackTrace();
        }
    }

}
