package com.project.prkt.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nikolai Khriapov
 */

@Entity
public class ProtectiveShorts extends Equipment {

    enum Size {
        XS,
        S,
        M,
        L,
        XL
    }

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long id;
    @javax.validation.constraints.Size(min = 3, max = 30, message = "{protective_shorts.message.invalid_name}")
    private String name;
    private EquipmentCondition condition;
    private Size size;
    @OneToMany(mappedBy = "protectiveShorts")
    private List<AssignedEquipment> listOfAssignedEquipment;

    public ProtectiveShorts() {
    }

    public ProtectiveShorts(String name, EquipmentCondition condition, Size size) {
        this.name = name;
        this.condition = condition;
        this.size = size;
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

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ProtectiveShorts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", condition=" + condition +
                ", size=" + size +
                '}';
    }
}
