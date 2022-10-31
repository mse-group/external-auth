package com.mse.externalauth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author special.fy
 */
@Data
@AllArgsConstructor
@Builder
public class Token {

    private String token;
}
