package site_parser_app.entity;

import javax.persistence.*;

@Entity
@Table(name = "responses")
public class ResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "path_link")
    private String path;

    @Column(name = "code_response")
    private int code;

    @Column(name = "content")
    private String content;

    public ResponseEntity(String path, int code, String content) {
        this.path = path;
        this.code = code;
        this.content = content;
    }

    public ResponseEntity() {
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
