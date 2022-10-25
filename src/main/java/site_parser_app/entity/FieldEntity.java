package site_parser_app.entity;

import javax.persistence.*;

@Entity
@Table(name = "fields")
public class FieldEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "selector")
    private String selector;

    @Column(name = "weight")
    private float weight;

    public FieldEntity(String name, String selector, float weight) {
        this.name = name;
        this.selector = selector;
        this.weight = weight;
    }

    public FieldEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
