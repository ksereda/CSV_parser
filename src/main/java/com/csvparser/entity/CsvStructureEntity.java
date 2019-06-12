package com.csvparser.entity;

import jdk.nashorn.internal.objects.annotations.Getter;

@Getter
@Setter
@Entity
@Table(name = "csvstructure_v1")
public class CsvStructureEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated = new Date();

    @Column(name = "student_id")
    public Long studentId;

    @Column(name = "organization_id")
    public Long organizationId;

    @Column(name = "campaign_name")
    public String campaignName;

    @Column(name = "source")
    public String source;

    @Column(name = "opt_in")
    public String optIn;

    @Column(name = "teacher_id")
    public String teacherId;

    @Column(name = "decision_process")
    public String decisionProcess;


}
