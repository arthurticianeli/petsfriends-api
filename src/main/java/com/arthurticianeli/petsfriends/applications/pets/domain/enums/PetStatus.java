package com.arthurticianeli.petsfriends.applications.pets.domain.enums;

import lombok.Getter;

@Getter
public enum PetStatus {
    AVAILABLE("available"),
    ADOPTED("adopted");

    private final String status;

    PetStatus(String status) {
        this.status = status;
    }

    public static PetStatus fromString(String status) {
        if(status == null) {
            return null;
        }

        for (PetStatus petStatus : PetStatus.values()) {
            if (petStatus.getStatus().equalsIgnoreCase(status)) {
                return petStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
