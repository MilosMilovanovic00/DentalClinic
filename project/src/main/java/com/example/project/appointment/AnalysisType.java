package com.example.project.appointment;

public enum AnalysisType {
    InitialExam("Initial Exam"),
    DentalCheckup("Dental Checkup"),
    ComprehensiveExamination("Comprehensive Examination"),
    EmergencyCare("Emergency Care");

    public final String label;

    AnalysisType(String label) {
        this.label = label;
    }
}
