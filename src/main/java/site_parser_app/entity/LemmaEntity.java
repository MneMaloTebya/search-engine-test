package site_parser_app.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lemmas")
public class LemmaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "lemma")
    private String lemma;

    @Column(name = "frequency")
    private int frequency;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "indexes",
            joinColumns = @JoinColumn(name = "page_id"),
            inverseJoinColumns = @JoinColumn(name = "lemma_id")
    )
    private List<PageEntity> pages;

    public LemmaEntity(String lemma, int frequency) {
        this.lemma = lemma;
        this.frequency = frequency;
    }

    public LemmaEntity() {
    }

    public void addPage(PageEntity pageEntity) {
        if (pages == null) {
            pages = new ArrayList<>();
        }
        pages.add(pageEntity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
