package com.project.prkt.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.ResourceBundle;

@Entity
@Table
public class SnowboardBoots extends Equipment {
    public enum Stiffness {
        UNKNOWN(),
        SOFT(),
        MEDIUM(),
        HARD();

        private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("snowboard_boots");

        public String toString() {
            return resourceBundle.getString("snowboard_boots.stiffness." + name());
        }
    }

    public enum Size {
        OTHER,
        RU36_EU37_MM235,
        RU34_EU35_MM240,
        RU37_EU38_MM245,
        RU38_EU39_MM250,
        RU39_EU40_MM255,
        RU40_EU41_MM260,
        RU41_EU42_MM265,
        RU415_EU425_MM275,
        RU42_EU43_MM275,
        RU425_EU435_MM280,
        RU43_EU44_MM285,
        RU44_EU45_MM290,
        RU45_EU46_MM300,
        RU46_EU47_MM310,
        JUNIOR26_MM165,
        JUNIOR28_MM175,
        JUNIOR30_MM185,
        JUNIOR31_MM195,
        JUNIOR32_MM205,
        JUNIOR33_MM210,
        JUNIOR34_MM215,
        JUNIOR35_MM225;

        private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("snowboard_boots");

        public String toString() {
            return resourceBundle.getString("snowboard_boots.size." + name());
        }
    }

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long id;
    @javax.validation.constraints.Size(min = 3, max = 30, message = "{snowboard_boots.message.invalid_name}")
    private String name;
    private boolean available;
    private EquipmentCondition condition;
    private Size size;
    private Stiffness stiffness;

    public SnowboardBoots() {
    }

    public SnowboardBoots(Long id, String name, boolean available, EquipmentCondition condition, Size size, Stiffness stiffness) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.condition = condition;
        this.size = size;
        this.stiffness = stiffness;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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

    public Stiffness getStiffness() {
        return stiffness;
    }

    public void setStiffness(Stiffness stiffness) {
        this.stiffness = stiffness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnowboardBoots that = (SnowboardBoots) o;
        return id.equals(that.id) && available == that.available &&
                name.equals(that.name) && condition == that.condition &&
                size == that.size && stiffness == that.stiffness;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, available, condition, size, stiffness);
    }

    @Override
    public String toString() {
        return "Snowboard Boots -> " +
                "id: " + id +
                ", name: " + name +
                ", available: " + (available ? "available" : "not available") +
                ", condition: " + condition.toString() +
                ", size: " + size.toString() +
                ", stiffness: " + stiffness.toString();
    }
}
