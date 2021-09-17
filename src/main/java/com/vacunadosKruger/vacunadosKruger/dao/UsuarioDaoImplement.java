package com.vacunadosKruger.vacunadosKruger.dao;

import com.vacunadosKruger.vacunadosKruger.models.Rol;
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

    //Trae todos los usuarios cuyo estado sea true, es decir, que están activos
    @Override
    public List<Usuario> getUsuarios() {
        String query="From Usuario where estado=true";
        return entityManager.createQuery(query).getResultList();
    }
    //Elimina de forma lógica un usuario cambiando su estado a false
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
    //Encuentra un susuario mediante su id
    @Override
    public Usuario getUsuarioById(Integer id) {
        try{
            Usuario usuario = entityManager.find(Usuario.class, id);
            return usuario;
        }catch(Exception e){
            return null;
        }

    }
    //Actualiza un usuario validando que los datos requeridos se encuentren, además de verificar que sean correctos
    @Override
    public boolean updateUsuario(Usuario usuario) {
        if(usuario.getCedula()==null || usuario.getCorreo()==null || usuario.getApellidos()==null || usuario.getNombres()==null) {
            return false;
        }else{
                if (verifyData(usuario)) {
                    try {
                        entityManager.merge(usuario);
                    } catch (Exception e) {
                        return false;
                    }
                    return true;
                }else{
                return false;
            }
    }}

    //Registra el usuario validando los datos requeridos, al pasar el filtro se genera un usuario y contraseña para el usuario
    //junto con su rol y estado
    @Override
    public boolean registerUsuario(Usuario usuario) {
        if(usuario.getCedula()==null || usuario.getCorreo()==null || usuario.getApellidos()==null || usuario.getNombres()==null){
            return false;
        }else{
            if (verifyData(usuario)) {
        try{
            usuario.setUsuario(usuario.getNombres().substring(0,1)+usuario.getApellidos().substring(0,1)+usuario.getCedula().toString());
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            usuario.setClave(argon2.hash(1,1024,1,usuario.getCedula().toString()));
            usuario.setEstado(true);
            Rol rol= new Rol();
            rol.setId(2);
            usuario.setRol(rol);
            entityManager.persist(usuario);
        }catch(Exception e){
            return false;
        }
        return true;
            }else{
                return false;
            }
        }
    }
    //Devuelve una lista de usuarios de acuerdo al tipo de vacuna
    @Override
    public List<Usuario> getUsuariosByVaccine(Integer id) {
        try {
            String query = "From Usuario where tipo_vacuna_id=:id and estado=true";
            return entityManager.createQuery(query)
                    .setParameter("id", id)
                    .getResultList();
        }catch (Exception e){
           return null;
        }
    }

    //Devuelve una lista de usuarios de acuerdo al estado de vacuna
    @Override
    public List<Usuario> getUsuariosByVaccineStatus(Boolean status) {
        try {
            String query = "From Usuario where estado_vacuna=:status and estado=true";
            return entityManager.createQuery(query)
                    .setParameter("status", status)
                    .getResultList();
        }catch(Exception e){
            return null;
        }
    }
    //Devuelve lista de usuarios según rango de fechas
    @Override
    public List<Usuario> getUsuariosByDateRange(String dateStart, String dateEnd) {

        try {
            String query = "From Usuario where fecha_vacuna>= ?1 and fecha_vacuna<= ?2 and estado=true";
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
    //Genera conprueba que el usuario que intenta logearse exista y se encuentre activo
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
        if(confirm){
            return result;
        }else{
            return null;
        }

    }
    //Actualiza la información del usuario que deben brindar los empleados
    @Override
    public boolean updateUsuarioByEmployee(Usuario usuario) {
        if(isNumeric(usuario.getCelular().toString())) {
            try {
                Usuario usuarioOriginal = entityManager.find(Usuario.class, usuario.getId());
                usuarioOriginal.setFecha_nacimiento(usuario.getFecha_nacimiento());
                usuarioOriginal.setDireccion(usuario.getDireccion());
                usuarioOriginal.setCelular(usuario.getCelular());
                usuarioOriginal.setEstado_vacuna(usuario.getEstado_vacuna());
                if (usuarioOriginal.getEstado_vacuna()) {
                    usuarioOriginal.setTipo_vacuna(usuario.getTipo_vacuna());
                    usuarioOriginal.setFecha_vacuna(usuario.getFecha_vacuna());
                    usuarioOriginal.setDosis(usuario.getDosis());
                }
                entityManager.merge(usuarioOriginal);
            } catch (Exception e) {
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    //verifica que la cedula, nombres, apellidos y correo tengan el formato completo
    public boolean verifyData(Usuario usuario){
        if(verifyCedula(usuario.getCedula().toString())
                && isAlpha(usuario.getApellidos())&& isAlpha(usuario.getNombres())
                && isValidEmail(usuario.getCorreo()) ){
            return true;
        }else{
            return false;
        }
    }
    //Verifica que la cédula sea una cédula válida del Ecuador
    public boolean verifyCedula(String cedula)
    {
        Integer total = 0;
        Integer valor =0;
        Integer lenghtCedula = 10;
        Integer[] coeficiente = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };

        if (isNumeric(cedula) && cedula.length() == lenghtCedula)
        {
            Integer provincia = Integer.valueOf(cedula.substring(0,2));
            if (provincia > 0 && provincia <= 24)
            {
                Integer digitoVerificador = Integer.valueOf(cedula.substring(9));
                for (Integer i = 0; i < coeficiente.length; i++)
                {
                    valor = coeficiente[i] * Integer.valueOf(cedula.substring(i,i+1));
                    total = valor >= 10 ? total + (valor - 9) : total + valor;
                }
                Integer digitoVerificadorObtenido = 0;
                if (total < 10)
                {
                    digitoVerificadorObtenido = 10 - total;
                }
                else
                {
                    digitoVerificadorObtenido = total >= 10 ? (total % 10) != 0 ? 10 - (total % 10) : (total % 10) : total;
                }
                return digitoVerificadorObtenido == digitoVerificador;
            }
        }
        return false;
    }
        private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    private boolean isAlpha(String strAlp) {
        return strAlp.matches("[a-zA-Z]+");
    }
    private boolean isValidEmail(String strEmail) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(strEmail);
        return m.matches();
    }
}
