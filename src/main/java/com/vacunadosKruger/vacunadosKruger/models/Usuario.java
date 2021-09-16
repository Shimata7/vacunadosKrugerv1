package com.vacunadosKruger.vacunadosKruger.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name= "usuario")
public class Usuario {

    @Id @Column(name="id") @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name="cedula")
    private Integer cedula;
    @Column(name="nombres")
    private String nombres;
    @Column(name="apellidos")
    private String apellidos;
    @Column(name="correo")
    private String correo;
    @Column(name="usuario")
    private String usuario;
    @Column(name="clave")
    private String clave;
    @Column(name="fecha_nacimiento")
    private Date fecha_nacimiento;
    @Column(name="direccion")
    private String direccion;
    @Column(name="estado_vacuna")
    private Boolean estado_vacuna;
    //@Column(name="tipo_vacuna_id")
    //private Integer tipo_vacuna_id;
    @Column(name="fecha_vacuna")
    private Date fecha_vacuna;
    @Column(name="dosis")
    private Integer dosis;
    //@Column(name="rol_id")
    //private Integer rol_id;
    @Column(name="estado")
    private Boolean estado;
    @ManyToOne
    @JoinColumn(name="rol_id",referencedColumnName="id")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name="tipo_vacuna_id",referencedColumnName="id")
    private Tipo_vacuna tipo_vacuna;

    public Tipo_vacuna getTipo_vacuna() {
        return tipo_vacuna;
    }

    public void setTipo_vacuna(Tipo_vacuna tipo_vacuna) {
        this.tipo_vacuna = tipo_vacuna;
    }


    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getEstado_vacuna() {
        return estado_vacuna;
    }

    public void setEstado_vacuna(Boolean estado_vacuna) {
        this.estado_vacuna = estado_vacuna;
    }

   /* public Integer getTipo_vacuna_id() {
        return tipo_vacuna_id;
    }

    public void setTipo_vacuna_id(Integer tipo_vacuna_id) {
        this.tipo_vacuna_id = tipo_vacuna_id;
    }*/

    public Date getFecha_vacuna() {
        return fecha_vacuna;
    }

    public void setFecha_vacuna(Date fecha_vacuna) {
        this.fecha_vacuna = fecha_vacuna;
    }

    public Integer getDosis() {
        return dosis;
    }

    public void setDosis(Integer dosis) {
        this.dosis = dosis;
    }

    /*public Integer getRol_id() {
        return rol_id;
    }

    public void setRol_id(Integer rol_id) {
        this.rol_id = rol_id;
    }
*/
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
