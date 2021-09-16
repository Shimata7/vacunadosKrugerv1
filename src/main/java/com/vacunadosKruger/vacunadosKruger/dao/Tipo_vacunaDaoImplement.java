package com.vacunadosKruger.vacunadosKruger.dao;

import com.vacunadosKruger.vacunadosKruger.models.Tipo_vacuna;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//Conexión con base de datos
@Repository
//Permite gestionar las consultas a base de datos
@Transactional

public class Tipo_vacunaDaoImplement implements Tipo_vacunaDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tipo_vacuna> getVacunas() {
        String query="From Tipo_vacuna";
        return entityManager.createQuery(query).getResultList();
    }
}
