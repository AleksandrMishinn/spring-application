package com.andersen.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE")
@Getter
@Setter
public class Role extends Dictionary implements BaseEntity {

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
