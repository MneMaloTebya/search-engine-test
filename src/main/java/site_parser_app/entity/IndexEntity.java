package site_parser_app.entity;

import javax.persistence.*;

@Entity
@Table(name = "indexes")
public class IndexEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "page_id")
    private int pageId;

    @Column(name = "lemma_id")
    private int lemmaId;

    @Column(name = "rank")
    private float rank;
}
