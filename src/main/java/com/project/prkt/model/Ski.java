package com.project.prkt.model;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ResourceBundle;

@Entity
@Table
public class Ski extends Equipment{
    public enum Stiffness {
        UNKNOWN,
        SOFT,
        MEDIUM,
        HARD;

        private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("equipment");

        public String toString() {
            return resourceBundle.getString("ski.stiffness." + name());
        }
        }

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long id;
    //@NotEmpty(message = "type something!")
    @Size(min = 3, max = 30, message = "{ski.message.invalid_name}")
    private String name;
    private EquipmentCondition condition;
    private String size; //пишем руками.  размер лыж - это цифры от 80 до 200
    private Stiffness stiffness;

    public Ski() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentCondition getCondition() {
        return condition;
    }

    public void setCondition(EquipmentCondition condition) {
        this.condition = condition;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Stiffness getStiffness() {
        return stiffness;
    }

    public void setStiffness(Stiffness stiffness) {
        this.stiffness = stiffness;
    }

    @Override
    public String toString() {
        return "Ski{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                ", size='" + size + '\'' +
                ", stiffness=" + stiffness +
                '}';
    }
}
