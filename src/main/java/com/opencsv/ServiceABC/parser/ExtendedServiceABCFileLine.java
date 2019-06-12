package com.opencsv.ServiceABC.parser;

import com.opencsv.ServiceABC.entity.ServiceABCFileLineEntity;

@Getter
@Setter
public class ExtendedServiceABCFileLine extends ServiceABCFileLineEntity {

    @CsvBindByName(column = "student_submitted_date")
    private String studentSubmittedDate;

    @CsvBindByName(column = "student_name")
    private String studentName;

    @CsvBindByName(column = "student_last_name")
    private String studentLastName;

    @CsvBindByName(column = "student_email")
    private String studentEmail;

    @CsvBindByName(column = "student_username")
    private String studentUsername;

    @CsvBindByName(column = "student_cell_number")
    private String studentCellNumber;

    @CsvBindByName(column = "student_birth_date")
    private String studentBirthDate;

    @CsvBindByName(column = "student_graduated")
    private Integer studentGraduated;

    @CsvBindByName(column = "student_start_term")
    private String studentStartTerm;

    @CsvBindByName(column = "student_gender")
    private String studentGender;

    @CsvBindByName(column = "student_ethnicity")
    private String studentEthnicity;

    @CsvBindByName(column = "student_address")
    private String studentAddress;

    @CsvBindByName(column = "student_state")
    private String studentState;

    @CsvBindByName(column = "student_zip_code")
    private String studentZipCode;

    @CsvBindByName(column = "student_country")
    private String studentCountry;

    @CsvBindByName(column = "student_area_interest_1")
    private String studentAreaInterest1;

    @CsvBindByName(column = "student_area_interest_2")
    private String studentAreaInterest2;

    @CsvBindByName(column = "student_area_interest_3")
    private String studentAreaInterest3;

    @CsvBindByName(column = "student_high_school")
    private String studentHighSchool;

    @CsvBindByName(column = "student_high_school_ceeb")
    private String studentHighSchoolCeeb;

    @CsvBindByName(column = "student_high_school_state")
    private String studentHighSchoolState;
}
