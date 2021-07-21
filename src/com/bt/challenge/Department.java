package com.bt.challenge;

public class Department implements Comparable {

    private Employee director;

    public Employee getDirector() {
        return director;
    }

    public void setDirector(Employee director) {
        this.director = director;
    }

    @Override
    public int compareTo(Object o) {
        return this.director.getId().compareTo(((Department) o).getDirector().getId());
    }
}
