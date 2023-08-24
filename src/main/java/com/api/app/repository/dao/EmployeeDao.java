package com.api.app.repository.dao;

import com.api.app.model.Employee;
import com.api.app.model.PhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
@AllArgsConstructor
public class EmployeeDao {
  private final EntityManager entityManager;

  public List<Employee> findByCriteria(
    String firstName,
    String lastName,
    String sex,
    String job,
    String code,
    String entrance,
    String leavingDate,
    String firstNameOrder,
    String lastNameOrder,
    String sexOrder,
    String jobOrder,
    String codeOrder,
    Pageable pageable) {
    var criteria = entityManager.getCriteriaBuilder();
    var query = criteria.createQuery(Employee.class);
    Root<Employee> root = query.from(Employee.class);
    Join<Employee, PhoneNumber> phoneNumbers = root.join("phoneNumbers");

    List<Predicate> predicates = new ArrayList<>();
    if (firstName != null) {
      predicates.add(criteria.or(
        criteria.like(criteria.lower(root.get("firstName")), '%' + firstName.toLowerCase() + '%'),
        criteria.like(root.get("firstName"), '%' + firstName + '%')
      ));
    }
    if (lastName != null) {
      predicates.add(criteria.or(
        criteria.like(criteria.lower(root.get("lastName")), '%' + lastName.toLowerCase() + '%'),
        criteria.like(root.get("lastName"), '%' + lastName + '%')
      ));
    }
    if (sex != null) {
      predicates.add(criteria.or(
              criteria.like(root.get("sex"), sex))
      );
    }
    if (job != null) {
      predicates.add(criteria.or(
        criteria.like(criteria.lower(root.get("job").get("job")), '%' + job.toLowerCase() + '%'),
        criteria.like(root.get("job").get("job"), '%' + job + '%')
      ));
    }

    if (code != null && !code.equals("")) {
      predicates.add(criteria.equal(phoneNumbers.get("code"), code));
    }

    if (entrance != null && !entrance.equals("") && !leavingDate.equals("")) {
      predicates.add(criteria.or(criteria.greaterThanOrEqualTo(root.get("entranceDate"), entrance),
        criteria.lessThanOrEqualTo(root.get("leavingDate"), leavingDate)));
    }

    List<Order> orders = new ArrayList<>();
    if (firstNameOrder != null) {
      orders.add(geOrder(root, criteria, firstNameOrder, "firstName"));
    }
    if (lastNameOrder != null) {
      orders.add(geOrder(root, criteria, lastNameOrder, "lastName"));
    }
    if (sexOrder != null) {
      orders.add(geOrder(root, criteria, sexOrder, "sex"));
    }
    if (jobOrder != null) {
      orders.add(geOrder(root, criteria, jobOrder, "job"));
    }
    /*if (codeOrder != null) {
      orders.add(geOrder(root, criteria, codeOrder, "phoneNumbers"));
    }*/
    Predicate[] predicatesArray = new Predicate[predicates.size()];
    query.where(predicates.toArray(predicatesArray));
    if (orders.isEmpty()) {
      query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, criteria));
    } else {
      query.orderBy(orders);
    }

    return entityManager.createQuery(query)
      .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
      .setMaxResults(pageable.getPageSize())
      .getResultList();
  }

  private Order geOrder(Root<Employee> root, CriteriaBuilder builder, String order, String property) {
    if (order == null || order.isEmpty()) {
      return null;
    }
    if (order.equalsIgnoreCase("ASC")) {
      return builder.asc(root.get(property));
    }
    if (order.equalsIgnoreCase("DESC")) {
      return builder.desc(root.get(property));
    } else {
      return null;
    }
  }
}
