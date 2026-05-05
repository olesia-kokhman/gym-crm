package org.olesia.model;

import java.time.LocalDate;
import java.util.Objects;

public class Trainee extends User {

    private LocalDate dateOfBirth;
    private String address;

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        return Objects.equals(dateOfBirth, trainee.dateOfBirth) && Objects.equals(address, trainee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfBirth, address);
    }
}
