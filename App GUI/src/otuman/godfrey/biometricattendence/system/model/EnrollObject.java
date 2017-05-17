/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otuman.godfrey.biometricattendence.system.model;

/**
 *
 * @author Otoman
 */
public class EnrollObject {
    private int enroll_id;
    private String student_id;
    private String code_id;
    
    EnrollObject(int enroll_id, String student_id, String course_code ){
        this.code_id = course_code;
        this.student_id = student_id;
        this.enroll_id = enroll_id;
    
    }
    public int getEnroll_id() {
        return enroll_id;
    }

    public void setEnroll_id(int enroll_id) {
        this.enroll_id = enroll_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
    }
    
    
    
    
}
