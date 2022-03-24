package ru.msu.cmc.webapp.Models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "admins")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "admin_id")
    private int admin_id;

    @Column(nullable = false, name = "admin_login")
    @NonNull
    private String admin_login;

    @Column(nullable = false, name = "admin_password")
    @NonNull
    private String admin_password;
}
