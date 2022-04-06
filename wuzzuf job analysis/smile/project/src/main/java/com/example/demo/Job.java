package com.example.demo;

public class Job {

    static int id = 0;
    Integer jobId;
    String Title;
    String Company;
    String Location;
    String Type;
    String Level;
    String YearsExp;
    String Country;
    String Skills;

    public Job() {
    }

    public Job(String Title,
               String Company,
               String Location,
               String Type,
               String Level,
               String YearsExp,
               String Country,
               String Skills) {
        this.Title = Title;
        this.Company =Company;
        this.Location = Location;
        this.Type=Type;
        this.Level = Level;
        this.YearsExp= YearsExp;
        this.Country = Country;
        this.Skills = Skills;

    }

    public Job(String s) {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getYearsExp() {
        return YearsExp;
    }

    public void setYearsExp(String yearsExp) {
        YearsExp = yearsExp;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }

    public String toString() {
        return "Job{" +
                "Title=" + Title +
                ", Company=" + Company +
                ", Location='" + Location + '\'' +
                ", Type='" + Type + '\'' +
                ", Level=" + Level +
                ", YearsExp=" + YearsExp +
                ", Country=" + Country +
                ", Skills='" + Skills +
                '}'+"\n";
    }
}
