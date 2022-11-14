package com.yourNewBank.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.yourNewBank.Banking.model.Customer} entity
 */
public class CustomerDto implements Serializable {
    private final Long id;
    private final String firstName;
    private final String lastName;

    public CustomerDto(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto entity = (CustomerDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ")";
    }
}