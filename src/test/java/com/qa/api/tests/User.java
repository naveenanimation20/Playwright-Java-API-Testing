package com.qa.api.tests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {

    private String id;
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String gender;

    @NonNull
    private String status;
}
