package site_parser_app.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pages")
public class PageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "path_link")
    private String path;

    @Column(name = "code_response")
    private int code;

    @Column(name = "content")
    private String content;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "indexes",
            joinColumns = @JoinColumn(name = "lemma_id"),
            inverseJoinColumns = @JoinColumn(name = "page_id")
    )
    private List<LemmaEntity> lemmas;

    public PageEntity(String path, int code, String content) {
        this.path = path;
        this.code = code;
        this.content = content;
    }

    public void addLemmas(LemmaEntity lemmaEntity) {
        if (lemmas == null) {
            lemmas = new ArrayList<>();
        }
        lemmas.add(lemmaEntity);
    }

    public PageEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
