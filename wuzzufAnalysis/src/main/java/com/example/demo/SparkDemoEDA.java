package com.example.demo;


import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.*;
import org.apache.spark.sql.Row;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;




import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import java.util.stream.Collectors;


import static java.util.stream.Collectors.toList;


public class SparkDemoEDA {
    static SparkSession sparkSession ;
    static Dataset<Row> data ;
    static Dataset<Row> data2 ;

    public SparkDemoEDA() {
        data = read_data();
        data2 = data;
    }
    public static Dataset<Row> read_data()
    {
        sparkSession = SparkSession.builder().appName("Wuzzuf Spark Linear Regression Demo").master("local[2]").getOrCreate();
        DataFrameReader dataFrameReader = sparkSession.read();
        dataFrameReader.option ("header", "true");
        Dataset<Row> csvDataFrame = dataFrameReader.csv("src/main/resources/Wuzzuf_Jobs.csv");
        return csvDataFrame;

    }
    public static Dataset<Row> show_first10_records(){
        return data.limit(10);
    }


    public static Dataset<Row> show_data_summary(){
        System.out.println(data.summary());
        return data.summary();
    }


    public static String show_data_structure()
    {
        return data.schema().prettyJson();
    }


    public static Dataset<Row> clean_null_data(){

        data = data.na().drop();
        data = data.filter(data.col("YearsExp").notEqual("null Yrs of Exp"));
        return data;
    }


    public static Dataset<Row> clean_duplicates_data(){
        clean_null_data();
        data= data.dropDuplicates();
        return  data;
    }


    public static Dataset<Row> show_top_companies() throws Exception {

        data.createOrReplaceTempView ("Wuzzuf_table");
        Dataset<Row> da = sparkSession.sql("SELECT Company, COUNT(Title) as Companies_Count\n" +
                " FROM Wuzzuf_table \n" +
                " GROUP BY Company\n" +
                " ORDER BY COUNT(Title) DESC");
        return da;
    }


    public static String pieChart() throws Exception {

        Dataset<Row> top_companies = show_top_companies();
        List<String> companies =top_companies.select("Company").as(Encoders.STRING()).collectAsList().stream().limit(8).collect(toList());
        List<Long> counts =top_companies.select("Companies_Count").as(Encoders.LONG()).collectAsList().stream().limit(8).collect(toList());

        PieChart chart = new PieChartBuilder().width (800).height (600).title (SparkDemoEDA.class.getSimpleName ()).build ();
        Color[] sliceColors = new Color[]{new Color (160, 68, 100), new Color (50, 105, 200), new Color (80, 210, 160), new Color (50, 100, 20),new Color (120, 180, 200)};
        chart.getStyler ().setSeriesColors (sliceColors);
        System.out.println( );
        // Series
        for (int i = 0; i < companies.size() ; i++) {
            chart.addSeries(companies.get(i),counts.get(i));
        }

        String path ="src/main/resources/Images/pieChart.png";
        BitmapEncoder.saveBitmap(chart, "./src/main/resources/Images/pieChart", BitmapEncoder.BitmapFormat.PNG);
        return path;
    }



    public static Dataset<Row> show_top_titles() throws Exception {

        data.createOrReplaceTempView("Wuzzuf_table");
        Dataset<Row> da = sparkSession.sql("SELECT Title, COUNT(*) as Titles_Count \n" +
                " FROM  Wuzzuf_table\n" +
                " GROUP BY Title\n" +
                " ORDER BY COUNT(*) DESC");
        return da;
    }

        public static String barChart1() throws Exception {

        Dataset<Row> top_titles = show_top_titles();
        List<String> titles =top_titles.select("Title").as(Encoders.STRING()).collectAsList().stream().limit(8).collect(toList());
        List<Long> counts =top_titles.select("Titles_Count").as(Encoders.LONG()).collectAsList().stream().limit(8).collect(toList());

        CategoryChart chart2 = new CategoryChartBuilder().width (1024).height (768).title ("Titles").xAxisTitle ("Titles").yAxisTitle ("Count").build ();

        chart2.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart2.getStyler ().setHasAnnotations (true);
        chart2.getStyler ().setStacked (true);
        chart2.getStyler().setXAxisLabelRotation(45);


        chart2.addSeries ("Titles Count", titles, counts);

        String path ="src/main/resources/Images/BarChartOne.png";
        BitmapEncoder.saveBitmap(chart2, "./src/main/resources/Images/BarChartOne", BitmapEncoder.BitmapFormat.PNG);
        return path;
    }



    public static Dataset<Row> show_top_locations() throws Exception {

        data.createOrReplaceTempView("Wuzzuf_table");
        Dataset<Row> da = sparkSession.sql("SELECT Location, COUNT(*) as Location_Count\n" +
                " FROM Wuzzuf_table \n" +
                " GROUP BY Location\n" +
                " ORDER BY COUNT(*) DESC");
        return da;
    }


    public static String barChart2() throws Exception {
        Dataset<Row> top_locations = show_top_locations();
        List<String> locations =top_locations.select("Location").as(Encoders.STRING()).collectAsList().stream().limit(8).collect(toList());
        List<Long> counts =top_locations.select("Location_Count").as(Encoders.LONG()).collectAsList().stream().limit(8).collect(toList());

        CategoryChart chart3 = new CategoryChartBuilder().width (1024).height (768).title ("Location counts").xAxisTitle ("location").yAxisTitle ("count").build ();

        chart3.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart3.getStyler ().setHasAnnotations (true);
        chart3.getStyler ().setStacked (true);
        chart3.getStyler().setXAxisLabelRotation(45);


        chart3.addSeries ("location Count", locations, counts);
        String path ="src/main/resources/Images/BarChartTwo.png";
        BitmapEncoder.saveBitmap(chart3, "./src/main/resources/Images/BarChartTwo", BitmapEncoder.BitmapFormat.PNG);
        return path;
    }





    public static List<Row> show_top_skills() throws Exception {
        clean_null_data();
        clean_duplicates_data();
        List<Row> skills_column = data.select("Skills").collectAsList();
        List<String> allSkills = skills_column.stream().map(row -> row.getString(0)).collect(Collectors.toList());

        List<String> skills = new ArrayList<>();
        for (String ls : allSkills) {
            String[] x = ls.split(",");
            for (String s : x) {
                skills.add(s);
            }
        }
        Map<String, Long> result =
                skills.stream().collect(
                        Collectors.groupingBy(p -> p, Collectors.counting()));
        List<Long> count = result.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).map(Map.Entry::getValue).collect(toList());
        List<String> skill = result.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).map(Map.Entry::getKey).collect(toList());

        List<Row> rows = new ArrayList<Row>();

        for (int i = 0; i < count.size(); i++) {
            Row row = RowFactory.create(skill.get(i),count.get(i));
            rows.add(row);
        }
        return rows;
    }


    public static Dataset<Row> Factorized(){
        StringIndexer idx = new StringIndexer();
        idx.setInputCol("YearsExp").setOutputCol("YearsExp indexed");
        Dataset<Row> new_data = idx.fit(data).transform(data);
        String columns[] = {"YearsExp", "YearsExp indexed"};
        Dataset<Row> yearsExpIndexed = new_data.select("YearsExp", "YearsExp indexed");
        return yearsExpIndexed;
    }

}
