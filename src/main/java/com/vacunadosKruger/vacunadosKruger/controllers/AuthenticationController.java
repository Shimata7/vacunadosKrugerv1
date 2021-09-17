package com.vacunadosKruger.vacunadosKruger.controllers;

import com.vacunadosKruger.vacunadosKruger.dao.UsuarioDao;
import com.vacunadosKruger.vacunadosKruger.models.Usuario;
import com.vacunadosKruger.vacunadosKruger.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthenticationController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;
    //Comprueba que el usuario exista y se regresa el token JWT junto con el rol e id del usuario
    @RequestMapping(value= "api/login", method = RequestMethod.POST)
    public List<String> login(@RequestBody Usuario usuario){
        List<String> data = new ArrayList<>();
        try{
            Usuario userlog = usuarioDao.login(usuario).get(0);
            String token = jwtUtil.create(userlog.getId().toString(),userlog.getUsuario());
            data.add(token);
            data.add(userlog.getRol().getRol());
            data.add(userlog.getId().toString());
            return data;
        }catch(Exception e){
            return data;
        }

    }
}
