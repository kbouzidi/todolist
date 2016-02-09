package com.reit.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "REIT_PROJECT")
public class Project implements Serializable {

    @Id
    @Column(name = "N_PROJECT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;

    @Column(name = "N_PROJECT_NAME")
    private String projectName;

    @Column(name = "C_PROJECT_DESCRIPTION")
    private String projectDescription;


    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
