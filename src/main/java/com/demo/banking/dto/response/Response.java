package com.demo.banking.dto.response;

import com.demo.banking.enumeration.Status;
import lombok.Data;

@Data
public class Response {
    private Long RequestId;
    private Status status;

}
