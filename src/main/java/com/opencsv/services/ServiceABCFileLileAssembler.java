package com.opencsv.services;

import com.opencsv.ServiceABC.entity.ServiceABCFileLineEntity;
import com.opencsv.ServiceABC.parser.ExtendedServiceABCFileLine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ServiceABCFileLileAssembler extends AutofillAbstractAssembler<ServiceABCFileLineEntity, ExtendedServiceABCFileLine> {

    @Inject
    private UserDao userDao;

    @Override
    public void assemble(ExtendedServiceABCFileLine result, ServiceABCFileLineEntity entity) {
        super.assemble(result, entity);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StudentEntity student = getStudent(entity.getStudentId());
        StudentEntity studentEmail = getStudentEmail(entity.getEmail());
        if (student != null) {
            SetAdditionalInfoByStudentId(result, entity, dateFormat, student);
        } else if (studentEmail != null) {
            SetAdditionalInfoByStudentEmail(result, entity, dateFormat, studentEmail);
        }
    }

    private void SetAdditionalInfoByStudentId(ExtendedServiceABCFileLine resultListByStudentId, ServiceABCFileLineEntity entity, DateFormat dateFormat, StudentEntity student) {
        resultListByStudentId.setStudentSubmittedDate(""); //TODO
        resultListByStudentId.setStudentName(student.getName());
        resultListByStudentId.setStudentLastName(student.getLastName());
        resultListByStudentId.setStudentEmail(student.getEmail());
        resultListByStudentId.setStudentUsername(student.getUsername());
        resultListByStudentId.setStudentCellNumber(student.getCellNumber());
        if (student.getBirthDate() != null) {
            resultListByStudentId.setStudentBirthDate(dateFormat.format(student.getBirthDate()));
        }
        resultListByStudentId.setStudentGraduated(student.getGraduated());
        resultListByStudentId.setStudentStartTerm(student.getPlannedStartTermSeason() + " " + student.getPlannedStartTermYear());
        if (student.getGender() != null) {
            resultListByStudentId.setStudentGender(student.getGender().getTitle());
        }
        if (student.getEthnicity() != null) {
            resultListByStudentId.setStudentEthnicity(student.getEthnicity().getTitle());
        }
        if (student.getAddress() != null) {
            resultListByStudentId.setStudentAddress(student.getAddress().getAddress());
            resultListByStudentId.setStudentState(student.getAddress().getState());
            resultListByStudentId.setStudentZipCode(student.getAddress().getZipCode());
            resultListByStudentId.setStudentCountry(student.getAddress().getCountry());
        }
        if (student.getInterests() != null) {
            int size = student.getAreasInterests().size();
            if (size >= 1) {
                resultListByStudentId.setStudentAreaInterest1(student.getAreasInterests().get(0).getValue());
            }
            if (size >= 2) {
                resultListByStudentId.setStudentAreaInterest2(student.getAreasInterests().get(1).getValue());
            }
            if (size >= 3 ) {
                resultListByStudentId.setStudentAreaInterest3(student.getAreasInterests().get(2).getValue());
            }
        }
        resultListByStudentId.setStudentHighSchool(student.getHighSchool());
        resultListByStudentId.setStudentHighSchoolCeeb(student.getHighSchoolCeeb());
        if (student.getHighSchoolAddress() != null) {
            resultListByStudentId.setStudentHighSchoolState(student.getHighSchoolAddress().getState());
        }
    }

    private void SetAdditionalInfoByStudentEmail(ExtendedServiceABCFileLine resultListByStudentEmail, ServiceABCFileLineEntity entity, DateFormat dateFormat, StudentEntity studentEmail) {
        resultListByStudentEmail.setStudentSubmittedDate("");
        resultListByStudentEmail.setStudentName(studentEmail.getName());
        resultListByStudentEmail.setStudentLastName(studentEmail.getLastName());
        resultListByStudentEmail.setStudentEmail(studentEmail.getEmail());
        resultListByStudentEmail.setStudentUsername(studentEmail.getUsername());
        resultListByStudentEmail.setStudentCellNumber(studentEmail.getCellNumber());
        if (studentEmail.getBirthDate() != null) {
            resultListByStudentEmail.setStudentBirthDate(dateFormat.format(studentEmail.getBirthDate()));
        }
        resultListByStudentEmail.setStudentGraduated(studentEmail.getGraduated());
        resultListByStudentEmail.setStudentStartTerm(studentEmail.getPlannedStartTermSeason() + " " + studentEmail.getPlannedStartTermYear());
        if (studentEmail.getGender() != null) {
            resultListByStudentEmail.setStudentGender(studentEmail.getGender().getTitle());
        }
        if (studentEmail.getEthnicity() != null) {
            resultListByStudentEmail.setStudentEthnicity(studentEmail.getEthnicity().getTitle());
        }
        if (studentEmail.getAddress() != null) {
            resultListByStudentEmail.setStudentAddress(studentEmail.getAddress().getAddress());
            resultListByStudentEmail.setStudentState(studentEmail.getAddress().getState());
            resultListByStudentEmail.setStudentZipCode(studentEmail.getAddress().getZipCode());
            resultListByStudentEmail.setStudentCountry(studentEmail.getAddress().getCountry());
        }
        if (studentEmail.getInterests() != null) {
            int size = studentEmail.getAreasInterests().size();
            if (size >= 1) {
                resultListByStudentEmail.setStudentAreaInterest1(studentEmail.getAreasInterests().get(0).getValue());
            }
            if (size >= 2) {
                resultListByStudentEmail.setStudentAreaInterest2(studentEmail.getAreasInterests().get(1).getValue());
            }
            if (size >= 3) {
                resultListByStudentEmail.setStudentAreaInterest3(studentEmail.getAreasInterests().get(2).getValue());
            }
        }
        resultListByStudentEmail.setStudentHighSchool(studentEmail.getHighSchool());
        resultListByStudentEmail.setStudentHighSchoolCeeb(studentEmail.getHighSchoolCeeb());
        if (studentEmail.getHighSchoolAddress() != null) {
            resultListByStudentEmail.setStudentHighSchoolState(studentEmail.getHighSchoolAddress().getState());
        }
    }

    private StudentEntity getStudent(Long studentId) {
        if (studentId == null) {
            return null;
        }

        try {
            return (StudentEntity) userDao.findById(studentId);
        } catch (Exception e) {
            return null;
        }
    }

    private StudentEntity getStudentEmail(String emailStudent) {
        if (emailStudent == null) {
            return null;
        }

        try {
            return (StudentEntity) userDao.findByStringId(emailStudent);
        } catch (Exception e) {
            return null;
        }
    }

}
