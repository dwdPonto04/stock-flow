package com.dwdponto04.stockflow.business.user.entity;

import com.dwdponto04.stockflow.business.user.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name="name",length = 50, nullable = false)
    private String name;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(name="email",length = 100, nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(max = 60)
    @Column(name = "password",length = 60,nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
