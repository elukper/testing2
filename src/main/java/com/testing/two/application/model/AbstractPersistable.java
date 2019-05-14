package com.testing.two.application.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class AbstractPersistable implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GENERAL")
    @SequenceGenerator(
            name="GENERAL"
    )
    @Column(name = "ID", nullable = false)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
