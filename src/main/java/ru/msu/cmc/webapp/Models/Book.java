package ru.msu.cmc.webapp.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "book_id")
    private int book_id;

    @Column(nullable = false, name = "title")
    @NonNull
    private String title;

    @Column(nullable = false, name = "authors")
    @NonNull
    private String authors;

    @Column(nullable = false, name = "genre")
    @NonNull
    private String genre;

    @Column(nullable = false, name = "publishing_house")
    @NonNull
    private String publishing_house;

    @Column(nullable = false, name = "publishing_year")
    private int publishing_year;

    @Column(nullable = false, name = "num_of_pages")
    private int num_of_pages;

    @Column(nullable = false, name = "cover_type")
    @NonNull
    private String cover_type;

    @Column(nullable = false, name = "price")
    private double price;

    @Column(nullable = false, name = "amount")
    private int amount;

//    @OneToMany(fetch=FetchType.LAZY, mappedBy = "books")
//    protected List<ShoppingCart> dutyFreeShops;
}