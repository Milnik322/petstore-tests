package com.petstore.data.pet;

import java.util.List;

public class Pet implements IPet {

    private int id;
    private String name;
    private Category category;
    private List<String> photoUrls;
    private Tag tags;
    private String status;

    public static IPet get() {
        return new Pet();
    }

    // Getters
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    @Override
    public Tag getTags() {
        return tags;
    }

    @Override
    public String getStatus() {
        return status;
    }

    // Setters
    @Override
    public IPet setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public IPet setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IPet setCategory(Category category) {
        this.category = category;
        return this;
    }

    @Override
    public IPet setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    @Override
    public IPet setTags(Tag tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public IPet setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public IPet build() {
        return this;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Category {
        private long id;
        private String name;

        public Category(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class Tag {
        private long id;
        private String name;

        public Tag(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Tag{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
