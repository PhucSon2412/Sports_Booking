package com.thcsdl.demothymeleaf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToMany(mappedBy = "roomid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookingList;

    @Override
    public String toString() {
        return "Room [id=" + id + ", roomType=" + roomType + ", price=" + price + "]";
    }

    public String toString2() {
        return id;
    }

    public String toString3()
    {
        return roomType;
    }
}