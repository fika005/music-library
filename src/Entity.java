package src;

import java.time.LocalDate;

public class Entity {
    String name;
    LocalDate dateCreated;

    public Entity() {
        name="";
        dateCreated = LocalDate.now();
    }

    public Entity(String n) {
        name = n;
        dateCreated = LocalDate.now();
    }

    /* Add setters and getters */

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    /* you complete this */
    public String toString() {
        String outPut = "name: " + name + "; date: " + dateCreated.toString();
        return outPut;
    }

    public boolean equals(Entity otherEntity) {
        if (this.name.equals(otherEntity.getName()) && this.dateCreated.equals(otherEntity.getDateCreated())) {
            return true;
        } else {
            return false;
        }
    }
}
