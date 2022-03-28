package com.mycompany.locationmanagementapi.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity(name = "user")
@Data
@NoArgsConstructor
public class UserEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @NotEmpty
    @Email(message = "Email cannot be empty", regexp = "/^[a-zA-Z0-9.! #$%&'*+/=? ^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\. [a-zA-Z0-9-]+)*$/.")
    private String email;

    @NotEmpty
    @Column(nullable = false)
    private String password;

    @Column(name = "mobile_number", unique = true)
    @Size(min = 10, max = 10)
    private String mobileNumber;

}
