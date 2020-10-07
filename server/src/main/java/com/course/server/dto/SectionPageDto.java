package com.course.server.dto;

public class SectionPageDto extends PageDto {
    private String CourseId;
    private String ChapterId;

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getChapterId() {
        return ChapterId;
    }

    public void setChapterId(String chapterId) {
        ChapterId = chapterId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SectionPageDto{");
        sb.append("CourseId='").append(CourseId).append('\'');
        sb.append(", ChapterId='").append(ChapterId).append('\'');
        sb.append(", page=").append(page);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
