package com.dulepov.spring.rest.boot_rest.dao;

import com.dulepov.spring.rest.boot_rest.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO{

    //используем дефолтный бин EntityManager [сами мы его не описывали, он системный](вместо sessionFactory, чтобы соответствовать независимому JPA)
    @Autowired
    private EntityManager entityManager;

    //READ
    @Override
    @Transactional
    public List<Employee> getAllEmployees(){

//        //получаем сессию из EntityManager
//        Session session=entityManager.unwrap(Session.class);

        //получаем информацию из базы (by JPA)
        List<Employee> allEmployees=entityManager.createQuery("from Employee",  Employee.class).getResultList();

//        //получаем информацию из базы (by Hibernate)
//        List<Employee> allEmployees=session.createQuery("from Employee",  Employee.class).getResultList();

        return allEmployees;
    }

    //CREATE
    @Override
    public void saveEmployee(Employee emp){

//        сохраняем или апдейтим (by Hibernate)-разделяет по id=0
//        session.saveOrUpdate(emp);

        //сохраняем или апдейтим (by JPA)-разделяет по id=0
        //merge является родителем для метода saveOrUpdate у Hibernate
        //используется при ошибке NonUniqueObjectException у saveOrUpdate
        //NonUniqueObjectException возникла при переходе с Hibernate на JPA(на entityManager)
        Employee newEmp=entityManager.merge(emp);

        //emp.id при create = 0, дозаполним его присвоенным id для вывода json
        emp.setId(newEmp.getId());

    }

    //GET ONE
    @Override
    public Employee getCurrentEmployee(int id){

        //получаем обьект (by JPA)
        Employee emp=entityManager.find(Employee.class, id);

        //получаем обьект (by Hibernate)
//        Employee emp=session.get(Employee.class, id);

        return emp;
    }

    //DELETE
    @Override
    public void deleteEmployee(int id){


//        //удаляем обьект (by Hibernate)
//        Query<Employee> query=session.createQuery("delete from Employee where id=:employeeId");
//        query.setParameter("employeeId", id);
//        query.executeUpdate();

        //удаляем обьект (by JPA)
        Query query= entityManager.createQuery("delete from Employee where id=:employeeId");
        query.setParameter("employeeId", id);
        query.executeUpdate();
    }

}
