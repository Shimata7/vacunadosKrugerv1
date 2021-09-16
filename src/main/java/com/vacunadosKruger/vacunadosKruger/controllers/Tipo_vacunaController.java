package com.vacunadosKruger.vacunadosKruger.controllers;

import com.vacunadosKruger.vacunadosKruger.dao.Tipo_vacunaDao;
import com.vacunadosKruger.vacunadosKruger.dao.UsuarioDao;
import com.vacunadosKruger.vacunadosKruger.models.Tipo_vacuna;
import com.vacunadosKruger.vacunadosKruger.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Tipo_vacunaController {

    @Autowired
    private Tipo_vacunaDao tipo_vacunaDao;

    @RequestMapping(value= "api/vacunas", method = RequestMethod.GET)
    public List<Tipo_vacuna> getVacunas(){
        return tipo_vacunaDao.getVacunas();
    }




}
