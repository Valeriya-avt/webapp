package ru.msu.cmc.webapp.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "client_id")
    private int client_id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "surname")
    @NonNull
    private String surname;

    @Column(nullable = false, name = "address")
    @NonNull
    private String address;

    @Column(nullable = false, name = "phone_number")
    @NonNull
    private String phone_number;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @Column(nullable = false, name = "client_login")
    @NonNull
    private String client_login;

    @Column(nullable = false, name = "client_password")
    @NonNull
    private String client_password;

//    @Override
//    public int hashCode() {
//        return Objects.hash(client_id, name, surname, address, phone_number, email, client_login, client_password);
//    }
}
