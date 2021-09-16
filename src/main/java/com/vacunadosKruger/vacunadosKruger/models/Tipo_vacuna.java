package com.vacunadosKruger.vacunadosKruger.models;

import javax.persistence.*;

@Entity
@Table(name= "tipo_vacuna")
public class Tipo_vacuna {

    @Id
    @Column(name="id") @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name="tipo_vacuna")
    private String tipo_vacuna;

    public int getId() { return id;  }

    public void setId(Integer id) { this.id = id;  }

    public String getTipo_vacuna() { return tipo_vacuna;   }

    public void setTipo_vacuna(String tipo_vacuna) { this.tipo_vacuna = tipo_vacuna;  }
}
