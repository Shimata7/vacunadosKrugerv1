package com.vacunadosKruger.vacunadosKruger.controllers;


import com.vacunadosKruger.vacunadosKruger.dao.UsuarioDao;
import com.vacunadosKruger.vacunadosKruger.models.Usuario;
import com.vacunadosKruger.vacunadosKruger.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Api(value= "Gestión de usuarios Rest Endpoint")
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @ApiOperation(value="Obtiene todos los usuarios habilitados")
    @RequestMapping (value= "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token){
        if(confirmToken(token))
        {
            return usuarioDao.getUsuarios();
        }
        return new ArrayList<>();
    }
    @ApiOperation(value="Obtiene un usuario de acuerdo a su id")
    @RequestMapping (value= "api/usuarios/{id}", method = RequestMethod.POST )
    public Usuario getUsuarioById(@RequestHeader(value="Authorization") String token, @PathVariable Integer id){
        if(!confirmToken(token)){ return null; }
        return usuarioDao.getUsuarioById(id);
    }
    @ApiOperation(value="Registra un usuario nuevo")
    @RequestMapping (value= "api/usuarios", method = RequestMethod.POST)
    public boolean registerUsuario(@RequestHeader(value="Authorization") String token, @RequestBody Usuario usuario){
        if(!confirmToken(token)){ return false; }
        return usuarioDao.registerUsuario(usuario);
    }
    @ApiOperation(value="Elimina lógicamente un usuario")
    @RequestMapping (value= "api/usuarios/{id}", method = RequestMethod.DELETE)
    public boolean deleteUsuario(@RequestHeader(value="Authorization") String token, @PathVariable Integer id){
        if(!confirmToken(token)){ return false; }
        return usuarioDao.deleteUsuario(id);
    }
    @ApiOperation(value="Modifica un usuario")
    @RequestMapping (value= "api/usuarios/{id}", method = RequestMethod.PUT)
    public boolean updateUsuario(@RequestHeader(value="Authorization") String token, @RequestBody Usuario usuario){
        if(!confirmToken(token)){ return false; }
        return usuarioDao.updateUsuario(usuario);
    }
    @ApiOperation(value="Actualiza los datos de vacunas desde el rol empleado")
    @RequestMapping (value= "api/usuario_por_empleado/{id}", method = RequestMethod.PUT)
    public boolean updateUsuarioByEmployee(@RequestHeader(value="Authorization") String token, @RequestBody Usuario usuario){
        if(!confirmToken(token)){ return false; }
        return usuarioDao.updateUsuarioByEmployee(usuario);
    }
    @ApiOperation(value="Obtiene todos los usuarios según tipo de vacuna")
    @RequestMapping (value= "api/usuarios_por_vacuna/{id}", method = RequestMethod.GET)
    public List<Usuario> getUsuariosByVaccine(@RequestHeader(value="Authorization") String token, @PathVariable Integer id){
        if(!confirmToken(token)){ return null; }
        return usuarioDao.getUsuariosByVaccine(id);
    }
    @ApiOperation(value="Obtiene todos los usuarios según estado de vacunación ")
    @RequestMapping (value= "api/usuarios_estado_vacuna/{status}", method = RequestMethod.GET)
    public List<Usuario> getUsuariosByVaccineStatus(@RequestHeader(value="Authorization") String token, @PathVariable Boolean status){
        if(!confirmToken(token)){ return null; }
        return usuarioDao.getUsuariosByVaccineStatus(status);
    }
    @ApiOperation(value="Obtiene todos los usuarios según rango de fecha de vacunación")
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
