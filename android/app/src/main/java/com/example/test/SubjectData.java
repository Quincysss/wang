package com.example.test;

public class SubjectData {
    String SubjectName;
    String Image;
    public SubjectData(String subjectName,String image) {
        this.SubjectName = subjectName;
        this.Image = image;
    }

    public String getSubjectName()
    {
        return SubjectName;
    }
}
