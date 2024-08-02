package ru.practicum.ewm.user.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @NotNull
    @Column(name = "users_email", unique = true)
    private String email;

    @NotNull
    @Column(name = "users_name")
    private String name;

}