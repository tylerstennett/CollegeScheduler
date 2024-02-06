package com.example.collegescheduler.db.entities;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "assignment",
        foreignKeys=@ForeignKey(
                entity = User.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("username")})
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    public int assignmentId;
    public String assignmentName;
    public String dueDate;
    public String courseName;
    public String username;
    public boolean completed;

    public Assignment(String assignmentName, String dueDate, String courseName, String username, boolean completed) {
            this.assignmentName = assignmentName;
            this.dueDate = dueDate;
            this.courseName = courseName;
            this.username = username;
            this.completed = completed;
        }

        public Assignment getAssignment() {
            return this;
        }
        public String getDueDate() {
            return dueDate;
        }
        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }
        public String getCourseName() {
            return courseName;
        }
        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
        public String getAssignmentName() {
            return assignmentName;
        }
        public void setAssignmentName(String assignmentName) {
            this.assignmentName = assignmentName;
        }
    }