package com.example.demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Web {

    @GetMapping("/display-data")
    public String display_data(){
        SparkDemoEDA sp = new SparkDemoEDA();
        HTMLHandler h = new HTMLHandler("Display Data",sp.show_first10_records()) ;
        return h.displayTable(8);
    }


    @GetMapping("/show_structure")
    public String Data_Structure(){
        SparkDemoEDA sp = new SparkDemoEDA();
        return sp.show_data_structure();
    }


    @GetMapping("/show_summary")
    public String Data_Summary(){
        SparkDemoEDA sp = new SparkDemoEDA();
        HTMLHandler h = new HTMLHandler("Data summary",sp.show_data_summary()) ;
        return h.displayTable(9);
    }

    @GetMapping("/clean_data")
    public String Clean_Data(){
        SparkDemoEDA sp = new SparkDemoEDA();
        HTMLHandler h = new HTMLHandler("Data Cleaned",sp.clean_duplicates_data()) ;
        return h.displayTable(8);
    }

    @GetMapping("/show_top_companies")
    public String Top_Companies() throws Exception {
        SparkDemoEDA sp = new SparkDemoEDA();
        HTMLHandler h = new HTMLHandler("Top Companies",sp.show_top_companies()) ;
        return h.displayTable(2);

    }

    @GetMapping("/show_top_titles")
    public String Top_Titles() throws Exception {
        SparkDemoEDA sp = new SparkDemoEDA();
        HTMLHandler h = new HTMLHandler("Top Titles",sp.show_top_titles()) ;
        return h.displayTable(2);
    }


    @GetMapping("/show_top_areas")
    public String Top_Areas() throws Exception {
        SparkDemoEDA sp = new SparkDemoEDA();
        HTMLHandler h = new HTMLHandler("Top Areas",sp.show_top_locations()) ;
        return h.displayTable(2);
    }


    @GetMapping("/show_top_skills")
    public String show_top_skills()throws Exception {
        SparkDemoEDA sp = new SparkDemoEDA();
        HTMLHandler h = new HTMLHandler("Top Skills",sp.show_top_skills()) ;
        return h.addTableList();
    }


    @GetMapping("/show_pie_chart")
    public String Companies_pie_chart() throws Exception {
        SparkDemoEDA sp = new SparkDemoEDA();
        String path = sp.pieChart();
        HTMLHandler h = new HTMLHandler("Companies Pie Chart");
        return h.viewchart(path,"Top Companies Pie Chart");
    }

    @GetMapping("/title_bar_chart")
    public String Title_Bar_Chart() throws Exception {
        SparkDemoEDA sp = new SparkDemoEDA();
        String path = sp.barChart1();
        HTMLHandler h = new HTMLHandler("titles bar Chart");
        return h.viewchart(path,"Top Titles Bar Chart");
    }

    @GetMapping("/location_bar_chart")
    public String Area_Bar_Chart() throws Exception {
        SparkDemoEDA sp = new SparkDemoEDA();
        String path = sp.barChart2();
        HTMLHandler h = new HTMLHandler("Area bar Chart");
        return h.viewchart(path,"Top Locations Bar Chart");
    }


    @GetMapping("/show_YearsExp")
    public String YearsExp() throws Exception {
        SparkDemoEDA sp = new SparkDemoEDA();
        HTMLHandler h = new HTMLHandler("Factorized Years Experience",sp.Factorized()) ;
        return h.displayTable(2);
    }

    @GetMapping("/KMeans_Model")
    public String KMeans()throws Exception {
        SparkDemoEDA sp = new SparkDemoEDA();
        HTMLHandler h = new HTMLHandler("KMeans Model",sp.KMeansModel()) ;
        return h.displayTable(3);
    }



}

