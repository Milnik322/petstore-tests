package com.petstore.data.pet;

import static com.petstore.data.GeneralConstants.PET_STATUS_AVAILABLE;

public class PetsRepository {

    public static IPet getValidPet() {
        return Pet.get()
                .setId(12345)
                .setName("Doggie")
                .setStatus(PET_STATUS_AVAILABLE)
                .build();
    }
}
