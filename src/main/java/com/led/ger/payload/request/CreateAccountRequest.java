package com.led.ger.payload.request;

import com.led.ger.entity.User;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateAccountRequest {

    @NonNull
    private String title;

    @NonNull
    private Long parentId;

}
