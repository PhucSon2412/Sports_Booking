package com.thcsdl.demothymeleaf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@NamedStoredProcedureQuery(
        name = "UpdateRoomType",
        procedureName = "UpdateRoomType",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "roomType", type = String.class),
        }
)

@NamedStoredProcedureQuery(
        name = "UpdateRoomPrice",
        procedureName = "UpdateRoomPrice",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "price", type = Double.class),
        }
)

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
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
        return "RoomID:" + id + ", roomType=" + roomType + ", price=" + price;
    }

    public String toString2() {
        return id;
    }

    public String toString3()
    {
        return roomType;
    }
}