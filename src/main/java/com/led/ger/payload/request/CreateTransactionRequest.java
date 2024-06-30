package com.led.ger.payload.request;

import com.led.ger.enumeration.TransactionType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateTransactionRequest {

    @NonNull
    private String amount;

    @NonNull
    private String transactionType;

    @NonNull
    private Long debitAccountId;

    @NonNull
    private Long creditAccountId;

}
