package com.petstore.data.pet;

import java.util.List;

public interface IPet {

    // Getters
    int getId();

    String getName();

    Pet.Category getCategory();

    List<String> getPhotoUrls();

    Pet.Tag getTags();

    String getStatus();

    // Setters
    IPet setId(int id);

    IPet setName(String name);

    IPet setCategory(Pet.Category category);

    IPet setPhotoUrls(List<String> photoUrls);

    IPet setTags(Pet.Tag tags);

    IPet setStatus(String status);

    IPet build();
}
