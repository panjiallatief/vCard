package com.ecard.vCard.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Blob;

@Entity
@Table(name = "person")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_person")
    private Integer id_person;

    @Column(name = "nama")
    String nama;

    @Column(name = "divisi")
    String divisi;

    @Column(name = "email")
    String email;

    @Column(name = "no_wa")
    String no_wa;

    @Column(name = "username")
    String username;

    @Column(name = "foto")
    Blob foto;
}
