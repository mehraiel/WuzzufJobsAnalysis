package com.example.demo;

import org.apache.commons.csv.CSVFormat;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.io.Read;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;


public class JobProvider {

    private DataFrame jobsDataFrame;

    public DataFrame readCSV(String path) {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        DataFrame df = null;
        try {
            df = Read.csv(path, format);
            System.out.println(df.summary());

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        jobsDataFrame = df;
        return df;
    }

    public DataFrame getJobDataFrame() {
        return jobsDataFrame;
    }

    public List<Job> getJobList(DataFrame d) {
        jobsDataFrame = d;
        assert jobsDataFrame != null;
        List<Job> jobs = new ArrayList<>();
        ListIterator<Tuple> iterator = jobsDataFrame.stream().collect(Collectors.toList()).listIterator();
        while (iterator.hasNext()) {
            Tuple t = iterator.next();
            Job j = new Job();
            Job.id += 1;

            j.jobId = Job.id;
            j.setTitle((String) t.get("Title"));
            j.setCompany((String) t.get("Company"));
            j.setLocation((String) t.get("Location"));
            j.setType((String) t.get("Type"));
            j.setLevel((String) t.get("Level"));
            j.setYearsExp((String) t.get("YearsExp"));
            j.setCountry((String) t.get("Country"));
            j.setSkills((String) t.get("Skills"));
            jobs.add(j);
        }
        return jobs;

    }
}
