package com.api.data;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class Users {
    private String id;
    private String name;
    private String email;
    private String gender;
    private String status;
}
