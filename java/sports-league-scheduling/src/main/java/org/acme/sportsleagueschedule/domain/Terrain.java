package org.acme.sportsleagueschedule.domain;

import java.util.Objects;

public class Terrain {

    private Long id;
    private String name;

    public Terrain() {
    }

    public Terrain(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terrain terrain = (Terrain) o;
        return Objects.equals(id, terrain.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Terrain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}