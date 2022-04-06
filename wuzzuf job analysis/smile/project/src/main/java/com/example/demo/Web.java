package com.example.demo;

import com.google.common.primitives.Ints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smile.data.DataFrame;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Web {

    SmileDemoEDA s = new SmileDemoEDA();
//    @GetMapping("/main")
//    public String m(){
//
//        HTMLBuilder h = new HTMLBuilder();
//        return h.build();
////        return jobs.get(0).getTitle();
//    }
    @GetMapping("/display-data")
    public String displayData(){

        s.read();
//        return s.df.toString();
        HTMLBuilder h = new HTMLBuilder("Display Data",s.df.slice(0,5));
        return h.displayTableFromDataframe(8);
//        return h.build();

    }
    @GetMapping("/show_structure")
    public String show_structure(){

        DataFrame d = s.read_display();
//        return s.df.toString();
        HTMLBuilder h = new HTMLBuilder("Structure of Data",d);
        return h.displayTableFromDataframe(7);
//        return h.build();

    }
    @GetMapping("/show_summary")
    public String show_summary()
    {
        DataFrame d = s.display_summary();
        HTMLBuilder h = new HTMLBuilder("Structure of Data",d);
        return h.displayTableFromDataframe(5);
    }
    @GetMapping("/clean_data")
    public String clean_data()
    {
        List<Job> jobs = s.clean_duplicate();
        HTMLBuilder h = new HTMLBuilder("Clean Data",jobs);
        return h.displayTableFromList();
    }
    @GetMapping("/show_top_companies")
    public String show_top_companies()
    {
        Map<String, Long> companies = s.TopCompanies();
        List<String> s = companies.entrySet().stream().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map(Map.Entry::getKey).collect(Collectors.toList());
        List<Long> i = companies.entrySet().stream().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map(Map.Entry::getValue).collect(Collectors.toList());
        HTMLBuilder h = new HTMLBuilder("Top Companies");
        return h.display("Company",s,i);
    }
    @GetMapping("/show_top_titles")
    public String show_top_titles()
    {
        Map<String, Long> titles = s.popular_job_titles();
        List<String> s = titles.entrySet().stream().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map(Map.Entry::getKey).collect(Collectors.toList());
        List<Long> i = titles.entrySet().stream().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map(Map.Entry::getValue).collect(Collectors.toList());
        HTMLBuilder h = new HTMLBuilder("Top Titles");
        return h.display("Title",s,i);
    }
    @GetMapping("/show_top_areas")
    public String show_top_areas()
    {
        Map<String, Long> areas = s.popular_areas();
        List<String> s = areas.entrySet().stream().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map(Map.Entry::getKey).collect(Collectors.toList());
        List<Long> i = areas.entrySet().stream().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map(Map.Entry::getValue).collect(Collectors.toList());
        HTMLBuilder h = new HTMLBuilder("Popular Areas");
        return h.display("Area",s,i);
    }
    @GetMapping("/show_top_skills")
    public String show_top_skills()
    {
        Map<String, Long> skills = s.popular_skills2();
        List<String> s = skills.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<Long> i = skills.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        HTMLBuilder h = new HTMLBuilder("Popular Skills");
        return h.display("Skills",s,i);
    }

    @GetMapping("/show_pie_chart")
    public String show_pie_chart() throws IOException {
        String path = s.pie_chart();
        HTMLBuilder h = new HTMLBuilder("Pie Chart");
        return h.viewchart(path,"Top Companies Pie Chart");
    }

    @GetMapping("/title_bar_chart")
    public String title_bar_chart() throws IOException {
        String path = s.bar_chart_one();
        HTMLBuilder h = new HTMLBuilder("Titles Bar Chart");
        return h.viewchart(path,"Top Titles Bar Chart");
    }
    @GetMapping("/location_bar_chart")
    public String location_bar_chart() throws IOException {
        String path = s.bar_chart_two();
        HTMLBuilder h = new HTMLBuilder("Location Bar Chart");
        return h.viewchart(path,"Top Location Bar Chart");
    }
    @GetMapping("/show_YearsExp")
    public String show_YearsExp() throws IOException {
        DataFrame d = s.read();
        List<Integer> yearsExpsEncoded = Ints.asList(s.encodeCategory(d,"YearsExp"));
        List<String> yearsExp = d.stringVector ("YearsExp").stream().collect (Collectors.toList());
        HTMLBuilder h = new HTMLBuilder("Encoded YearsExp");
        return h.display("Years Exp",yearsExp,yearsExpsEncoded);
    }

    @GetMapping("/show_KMeans")
    public String show_KMeans() throws IOException {
        DataFrame d = s.read();
        String path = s.KMeans(d);
        HTMLBuilder h = new HTMLBuilder("KMeans");
        return h.viewchart(path,"KMeans Chart");
    }
}
