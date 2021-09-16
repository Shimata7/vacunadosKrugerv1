package com.vacunadosKruger.vacunadosKruger.models;

import javax.persistence.*;

@Entity
@Table(name= "rol")
public class Rol {

    @Id
    @Column(name="id") @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name="rol")
    private String rol;

    public int getId() {return id;    }

    public void setId(Integer id) { this.id = id;    }

    public String getRol() { return rol;    }

    public void setRol(String rol) { this.rol = rol;   }
}
