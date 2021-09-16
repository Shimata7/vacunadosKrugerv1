package com.vacunadosKruger.vacunadosKruger.dao;

import com.vacunadosKruger.vacunadosKruger.models.Usuario;

import java.util.Date;
import java.util.List;

public interface UsuarioDao {
    List<Usuario> getUsuarios ();

    boolean deleteUsuario(Integer id);

    Usuario getUsuarioById(Integer id);

    boolean updateUsuario(Usuario usuario);

    boolean registerUsuario(Usuario usuario);

    List<Usuario> getUsuariosByVaccine(Integer id);

    List<Usuario> getUsuariosByVaccineStatus(Boolean status);

    List<Usuario> getUsuariosByDateRange(String dateStart, String dateEnd);

    List<Usuario> login(Usuario usuario);

    boolean updateUsuarioByEmployee(Usuario usuario);
}
