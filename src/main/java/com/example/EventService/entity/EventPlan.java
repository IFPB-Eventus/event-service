package com.example.EventService.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event_plans")
public class EventPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "event_date", nullable = false)
    private Date eventDate;

    @Column(nullable = false)
    private int microphones;

    @Column(nullable = false)
    private int projectors;

    @Column(nullable = false)
    private String rooms;

    @Column(nullable = false)
    private String members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getMicrophones() {
        return microphones;
    }

    public void setMicrophones(int microphones) {
        this.microphones = microphones;
    }

    public int getProjectors() {
        return projectors;
    }

    public void setProjectors(int projectors) {
        this.projectors = projectors;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}