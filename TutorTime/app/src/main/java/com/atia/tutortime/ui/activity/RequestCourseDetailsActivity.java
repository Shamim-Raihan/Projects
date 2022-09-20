package com.atia.tutortime.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.atia.tutortime.R;

public class RequestCourseDetailsActivity extends AppCompatActivity {

    private String availabeSeat, courseClass, courseName, endTime, media, startTime, totalSeat, teacherName, teacherContact, status, liveClass, modelTest, notes, finalExam;

    private TextView courseNameTv, courseTeacherTv, courseTotalSeatTv, courseAvailableSeatTv, courseStartTimeTv, courseEndTimeTv, courseClassTv, courseMediaTv, teacherContactTv;
    private TextView statusTv, liveClassTv, modelTestTv, notesTv, finalExamTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_course_details);


        courseNameTv = findViewById(R.id.request_course_name_id);
        courseTeacherTv = findViewById(R.id.request_course_teacher_name_id);
        courseTotalSeatTv = findViewById(R.id.request_total_seat_id);
        courseAvailableSeatTv = findViewById(R.id.request_available_seat_id);
        courseStartTimeTv = findViewById(R.id.request_start_time_id);
        courseEndTimeTv = findViewById(R.id.request_end_time_id);
        courseClassTv = findViewById(R.id.request_class_id);
        courseMediaTv = findViewById(R.id.request_media_id);
        teacherContactTv = findViewById(R.id.request_course_teacher_contact_id);
        statusTv = findViewById(R.id.request_status_id);
        liveClassTv = findViewById(R.id.request_live_class_id);
        modelTestTv = findViewById(R.id.request_model_test_id);
        notesTv = findViewById(R.id.request_notes_id);
        finalExamTv = findViewById(R.id.request_final_exam_id);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            availabeSeat = bundle.getString("availableSeat");
            courseClass = bundle.getString("class");
            courseName = bundle.getString("courseName");
            endTime = bundle.getString("endTime");
            media = bundle.getString("media");
            startTime = bundle.getString("startTime");
            totalSeat = bundle.getString("totalSeat");
            teacherName = bundle.getString("teacherName");
            teacherContact = bundle.getString("teacherContact");
            status = bundle.getString("status");
            liveClass = bundle.getString("liveClass");
            modelTest = bundle.getString("modelTest");
            notes = bundle.getString("notes");
            finalExam = bundle.getString("finalExam");
        }

        courseNameTv.setText(courseName);
        courseTeacherTv.setText(teacherName);
        courseTotalSeatTv.setText(totalSeat);
        courseAvailableSeatTv.setText(availabeSeat);
        courseStartTimeTv.setText(startTime);
        courseEndTimeTv.setText(endTime);
        courseClassTv.setText(courseClass);
        courseMediaTv.setText(media);
        teacherContactTv.setText(teacherContact);
        statusTv.setText(status);
        liveClassTv.setText(liveClass);
        modelTestTv.setText(modelTest);
        notesTv.setText(notes);
        finalExamTv.setText(finalExam);
    }
}