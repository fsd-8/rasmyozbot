package net.idrok.model;

import java.time.LocalDateTime;

public class Session {
    private Long userId;
    private Section section;
    private LocalDateTime lastUpdate;


    private String firstName;
    private String lastName;
    private String username;
    private String phone;

    public Session() {
    }

    public Session(Long userId, Section section, LocalDateTime lastUpdate) {
        this.userId = userId;
        this.section = section;
        this.lastUpdate = lastUpdate;
    }

    public Session(Long userId, Section section, LocalDateTime lastUpdate, String firstName, String lastName, String username, String phone) {
        this.userId = userId;
        this.section = section;
        this.lastUpdate = lastUpdate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
