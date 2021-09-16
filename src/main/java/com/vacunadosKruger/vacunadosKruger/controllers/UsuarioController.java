package com.vacunadosKruger.vacunadosKruger.controllers;


import com.vacunadosKruger.vacunadosKruger.dao.UsuarioDao;
import com.vacunadosKruger.vacunadosKruger.models.Usuario;
import com.vacunadosKruger.vacunadosKruger.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping (value= "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token){
        if(confirmToken(token))
        {
            return usuarioDao.getUsuarios();
        }
        return new ArrayList<>();
    }

    @RequestMapping (value= "api/usuarios/{id}")
    public Usuario getUsuarioById(@RequestHeader(value="Authorization") String token, @PathVariable Integer id){
        if(!confirmToken(token)){ return null; }
        return usuarioDao.getUsuarioById(id);
    }

    @RequestMapping (value= "api/usuarios", method = RequestMethod.POST)
    public boolean registerUsuario(@RequestHeader(value="Authorization") String token, @RequestBody Usuario usuario){
        if(!confirmToken(token)){ return false; }
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        usuario.setClave(argon2.hash(1,1024,1,usuario.getClave()));
        return usuarioDao.registerUsuario(usuario);
    }

    @RequestMapping (value= "api/usuarios/{id}", method = RequestMethod.DELETE)
    public boolean deleteUsuario(@RequestHeader(value="Authorization") String token, @PathVariable Integer id){
        if(!confirmToken(token)){ return false; }
        return usuarioDao.deleteUsuario(id);
    }

    @RequestMapping (value= "api/usuarios/{id}", method = RequestMethod.PUT)
    public boolean updateUsuario(@RequestHeader(value="Authorization") String token, @RequestBody Usuario usuario){
        if(!confirmToken(token)){ return false; }
        return usuarioDao.updateUsuario(usuario);
    }

    @RequestMapping (value= "api/usuarios_por_vacuna/{id}", method = RequestMethod.GET)
    public List<Usuario> getUsuariosByVaccine(@RequestHeader(value="Authorization") String token, @PathVariable Integer id){
        if(!confirmToken(token)){ return null; }
        return usuarioDao.getUsuariosByVaccine(id);
    }

    @RequestMapping (value= "api/usuarios_estado_vacuna/{status}", method = RequestMethod.GET)
    public List<Usuario> getUsuariosByVaccineStatus(@RequestHeader(value="Authorization") String token, @PathVariable Boolean status){
        if(!confirmToken(token)){ return null; }
        return usuarioDao.getUsuariosByVaccineStatus(status);
    }

    @GetMapping (value= "api/usuarios_rango_fecha/{dateStart}/{dateEnd}")
        public List<Usuario> getUsuariosByDateRange(@RequestHeader(value="Authorization") String token, @PathVariable Map<String, String> pathVarsMap){
        if(!confirmToken(token)){ return null; }
        String dateStart = pathVarsMap.get("dateStart");
        String dateEnd = pathVarsMap.get("dateEnd");
        return usuarioDao.getUsuariosByDateRange(dateStart, dateEnd);
    }

    private boolean confirmToken (String token){
        if(jwtUtil.getKey(token)== null){
            return false;
        }
        return true;
    }
}
