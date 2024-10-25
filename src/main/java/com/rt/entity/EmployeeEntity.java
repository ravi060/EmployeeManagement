package com.rt.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@Table(name="emp")
@SQLDelete(sql="UPDATE EMP SET STATUS='deleted' WHERE EMPNO=?")
@SQLRestriction("status <> 'DELETED'")
public class EmployeeEntity {

    @Id
    @SequenceGenerator(name="gen1",sequenceName = "Emp_No_seq",allocationSize = 1,initialValue = 1000)
    @GeneratedValue(generator = "gen1",strategy = GenerationType.SEQUENCE)
    private Integer empno;
    private String ename;
    private String job;
    private Double sal;
    @Column(name="status")
    private String status="active";
    private Integer deptno;
}
