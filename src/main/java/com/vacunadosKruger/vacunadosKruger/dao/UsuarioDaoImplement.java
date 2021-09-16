package com.vacunadosKruger.vacunadosKruger.dao;

import com.vacunadosKruger.vacunadosKruger.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Conexión con base de datos
@Repository
//Permite gestionar las consultas a base de datos
@Transactional
public class UsuarioDaoImplement implements UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        String query="From Usuario where estado=true";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean deleteUsuario(Integer id) {
        try{
            Usuario usuario = entityManager.find(Usuario.class, id);
            usuario.setEstado(Boolean.FALSE);
            entityManager.merge(usuario);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        try{
            Usuario usuario = entityManager.find(Usuario.class, id);
            return usuario;
        }catch(Exception e){
            return null;
        }

    }

    @Override
    public boolean updateUsuario(Usuario usuario) {
        try{
            if(usuario.getCedula()==null){
                Usuario usuarioOriginal = entityManager.find(Usuario.class, usuario.getId());
                usuarioOriginal.setFecha_nacimiento(usuario.getFecha_nacimiento());
                usuarioOriginal.setDireccion(usuario.getDireccion());
                usuarioOriginal.setCelular(usuario.getCelular());
                usuarioOriginal.setEstado_vacuna(usuario.getEstado_vacuna());
                if(usuarioOriginal.getEstado_vacuna()){
                    usuarioOriginal.setTipo_vacuna(usuario.getTipo_vacuna());
                    usuarioOriginal.setFecha_vacuna(usuario.getFecha_vacuna());
                    usuarioOriginal.setDosis(usuario.getDosis());
                }
                entityManager.merge(usuarioOriginal);
            }else {
                entityManager.merge(usuario);
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean registerUsuario(Usuario usuario) {
        try{
            entityManager.persist(usuario);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<Usuario> getUsuariosByVaccine(Integer id) {
        try {
            String query = "From Usuario where tipo_vacuna_id=:id";
            return entityManager.createQuery(query)
                    .setParameter("id", id)
                    .getResultList();
        }catch (Exception e){
           return null;
        }
    }

    @Override
    public List<Usuario> getUsuariosByVaccineStatus(Boolean status) {
        try {
            String query = "From Usuario where estado_vacuna=:status";
            return entityManager.createQuery(query)
                    .setParameter("status", status)
                    .getResultList();
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public List<Usuario> getUsuariosByDateRange(String dateStart, String dateEnd) {

        try {
            String query = "From Usuario where fecha_vacuna>= ?1 and fecha_vacuna<= ?2";
            try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
                return entityManager.createQuery(query)
                        .setParameter(1,date1)
                        .setParameter(2,date2)
                        .getResultList();
        }catch (Exception e){
           return null;
        }

        }catch(Exception e){
            return null;
        }
    }

    @Override
    public List<Usuario> login(Usuario usuario) {
        boolean confirm=false;
        List<Usuario> result = new ArrayList<>();
        try {
            String query = "From Usuario where usuario= :usuario and estado=true";
                    result = entityManager.createQuery(query)
                    .setParameter("usuario",usuario.getUsuario())
                    .getResultList();
 //Comprueba que las claves hasheadas coincidan
            if(!result.isEmpty()) {
                Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                confirm = argon2.verify(result.get(0).getClave(), usuario.getClave());
            }else{
                return null;
            }
        }catch(Exception e){
            return null;
        }
        return result;
    }
}
