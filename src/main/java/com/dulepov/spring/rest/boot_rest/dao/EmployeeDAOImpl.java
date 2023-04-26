package com.dulepov.spring.rest.boot_rest.dao;

import com.dulepov.spring.rest.boot_rest.entity.Employee;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
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

        //получаем сессию из EntityManager
        Session session=entityManager.unwrap(Session.class);

        //получаем информацию из базы
        List<Employee> allEmployees=session.createQuery("from Employee",  Employee.class).getResultList();

        return allEmployees;
    }

    //CREATE
    @Override
    public void saveEmployee(Employee emp){

        Session session=entityManager.unwrap(Session.class);

        //merge является родителем для метода saveOrUpdate у Hibernate
        //используется при ошибке NonUniqueObjectException у saveOrUpdate
        //NonUniqueObjectException возникла при переходе с Hibernate на JPA(на entityManager)
//        session.merge(emp);


        if (emp.getId()==0){    //если в json не будет передан id, то при десериализации он будет =0
            session.save(emp);	//CREATE
        } else {
            session.merge(emp);	//UPDATE and PARTIAL UPDATE
        }
    }

    //UPDATE
    @Override
    public Employee getCurrentEmployee(int id){

        Session session=entityManager.unwrap(Session.class);

        //получаем обьект
        Employee emp=session.get(Employee.class, id);

        return emp;
    }

    //DELETE
    @Override
    public void deleteEmployee(int id){

        Session session=entityManager.unwrap(Session.class);

        //удаляем обьект
        Query<Employee> query=session.createQuery("delete from Employee where id=:employeeId");
        query.setParameter("employeeId", id);
        query.executeUpdate();
    }

}
