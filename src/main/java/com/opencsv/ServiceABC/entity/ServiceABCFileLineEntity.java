package com.opencsv.ServiceABC.entity;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "serviceABC_file_line_v1")
public class ServiceABCFileLineEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated = new Date();

    @Column(name = "file_name")
    private String fileName;

    @CsvBindByName(column = "experience_date")
    @Column(name = "experience_date")
    public String experienceDate;

    @CsvBindByName(column = "student_id")
    @Column(name = "student_id")
    public Long studentId;

    @CsvBindByName(column = "organization_id")
    @Column(name = "organization_id")
    public Long collegeId;

    @CsvBindByName(column = "campaign_name")
    @Column(name = "campaign_name")
    public String campaignName;

    @CsvBindByName(column = "source")
    @Column(name = "source")
    public String source;

    @CsvBindByName(column = "opt_in")
    @Column(name = "opt_in")
    public String optIn;

    @CsvBindByName(column = "parent_email")
    @Column(name = "parent_email")
    public String parentEmail;

    @CsvBindByName(column = "military_branch")
    @Column(name = "military_branch")
    public String militaryBranch;

    @CsvBindByName(column = "familiar_with_ci_app")
    @Column(name = "familiar_with_ci_app")
    public String familiarWithCiApp;

    @CsvBindByName(column = "desired_degree")
    @Column(name = "desired_degree")
    public String desiredDegree;

    @CsvBindByName(column = "important_factors")
    @Column(name = "important_factors")
    public String importantFactors;

    @CsvBindByName(column = "request_information")
    @Column(name = "request_information")
    public String requestInformation;

    @CsvBindByName(column = "programs")
    @Column(name = "programs")
    public String programs;

    @CsvBindByName(column = "parent_first_name")
    @Column(name = "parent_first_name")
    public String parentFirstName;

    @CsvBindByName(column = "interested_in_offer")
    @Column(name = "interested_in_offer")
    public String interestedInOffer;

    @CsvBindByName(column = "parent_phone_number")
    @Column(name = "parent_phone_number")
    public String parentPhoneNumber;

    @CsvBindByName(column = "non_us_resident")
    @Column(name = "non_us_resident")
    public String nonUsResident;

    @CsvBindByName(column = "parent_last_name")
    @Column(name = "parent_last_name")
    public String parentLastName;

    @CsvBindByName(column = "gender")
    @Column(name = "gender")
    public String gender;

    @CsvBindByName(column = "athletics")
    @Column(name = "athletics")
    public String athletics;

    @CsvBindByName(column = "best_method_of_contact")
    @Column(name = "best_method_of_contact")
    public String bestMethodOfContact;

    @CsvBindByName(column = "application_interest")
    @Column(name = "application_interest")
    public String applicationInterest;

    @CsvBindByName(column = "test_score_range")
    @Column(name = "test_score_range")
    public String testScoreRange;

    @CsvBindByName(column = "program_of_interest")
    @Column(name = "program_of_interest")
    public String programOfInterest;

    @CsvBindByName(column = "level_of_interest_in_visiting")
    @Column(name = "level_of_interest_in_visiting")
    public String levelOfInterestInVisiting;

    @CsvBindByName(column = "education_level")
    @Column(name = "education_level")
    public String educationLevel;

    @CsvBindByName(column = "small_classroom_setting")
    @Column(name = "small_classroom_setting")
    public String smallClassroomSetting;

    @CsvBindByName(column = "email")
    @Column(name = "email")
    public String email;

    @CsvBindByName(column = "phone_number")
    @Column(name = "phone_number")
    public String phoneNumber;

    @CsvBindByName(column = "first_name")
    @Column(name = "first_name")
    public String firstName;

    @CsvBindByName(column = "last_name")
    @Column(name = "last_name")
    public String lastName;

    @CsvBindByName(column = "postal_code")
    @Column(name = "postal_code")
    public String postalCode;

    @CsvBindByName(column = "city")
    @Column(name = "city")
    public String city;

    @CsvBindByName(column = "state")
    @Column(name = "state")
    public String state;

    @CsvBindByName(column = "student_enrollment")
    @Column(name = "student_enrollment")
    public String studentEnrollment;

    @CsvBindByName(column = "timeframes")
    @Column(name = "timeframes")
    public String timeframes;

    @CsvBindByName(column = "street_address")
    @Column(name = "street_address")
    public String streetAddress;

    @CsvBindByName(column = "year_of_grad__hs")
    @Column(name = "year_of_grad__hs")
    public String yearOfGradHs;

    @CsvBindByName(column = "year_of_grad__college")
    @Column(name = "year_of_grad__college")
    public String yearOfGradCollege;

    @CsvBindByName(column = "level_general_interest")
    @Column(name = "level_general_interest")
    public String levelGeneralInterest;

    @CsvBindByName(column = "location")
    @Column(name = "location")
    public String location;

    @CsvBindByName(column = "information_session")
    @Column(name = "information_session")
    public String informationSession;

    @CsvBindByName(column = "tuition")
    @Column(name = "tuition")
    public String tuition;

    @CsvBindByName(column = "diversity")
    @Column(name = "diversity")
    public String diversity;

    @CsvBindByName(column = "gpa")
    @Column(name = "gpa")
    public String gpa;

    @CsvBindByName(column = "sat")
    @Column(name = "sat")
    public String sat;

    @CsvBindByName(column = "act")
    @Column(name = "act")
    public String act;

    @CsvBindByName(column = "application_type")
    @Column(name = "application_type")
    public String applicationType;

    @CsvBindByName(column = "share_profile")
    @Column(name = "share_profile")
    public String shareProfile;

    @CsvBindByName(column = "summer_program")
    @Column(name = "summer_program")
    public String summerProgram;

    @CsvBindByName(column = "campus_type")
    @Column(name = "campus_type")
    public String campusType;

    @CsvBindByName(column = "institution_type")
    @Column(name = "institution_type")
    public String institutionType;

    @CsvBindByName(column = "first_generation")
    @Column(name = "first_generation")
    public String firstGeneration;

    @CsvBindByName(column = "legacy_connection")
    @Column(name = "legacy_connection")
    public String legacyConnection;

    @CsvBindByName(column = "test_optional")
    @Column(name = "test_optional")
    public String testOptional;

    @CsvBindByName(column = "learning_environment")
    @Column(name = "learning_environment")
    public String learningEnvironment;

    @CsvBindByName(column = "admit_rate")
    @Column(name = "admit_rate")
    public String admitRate;

    @CsvBindByName(column = "residential_housing")
    @Column(name = "residential_housing")
    public String residentialHousing;

    @CsvBindByName(column = "financial_aid")
    @Column(name = "financial_aid")
    public String financialAid;

    @CsvBindByName(column = "free_trial")
    @Column(name = "free_trial")
    public String freeTrial;

    @CsvBindByName(column = "information_type")
    @Column(name = "information_type")
    public String informationType;

    @CsvBindByName(column = "ethnicity")
    @Column(name = "ethnicity")
    public String ethnicity;
}
