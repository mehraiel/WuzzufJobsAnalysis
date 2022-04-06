package com.example.demo;

import com.google.common.primitives.Ints;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import smile.clustering.KMeans;
import smile.clustering.PartitionClustering;
import smile.data.DataFrame;
import smile.data.measure.NominalScale;
import smile.data.vector.IntVector;
import smile.plot.swing.ScatterPlot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SmileDemoEDA {

    static String  trainPath = "src/main/resources/static/Wuzzuf_Jobs.csv";
    static DataFrame df;
    static List<Job> j;

    public static void main(String[] args) throws IOException {

        SmileDemoEDA s = new SmileDemoEDA();
        System.out.println ("display ");
        s.read_display();
        System.in.read();
        df = df.merge (IntVector.of ("Years of Exp",
                s.encodeCategory (df, "YearsExp")));
//        Map<String, Long> m = s.encodeCategory(df,"YearsExp");
//        for (String a: m.keySet()) {
//            System.out.println(a +" "+ m.get(a));
//        }
        System.out.println(df.slice(0,30));
        System.out.println (df.structure ());
        System.out.println ("display summary");
        System.out.println (s.display_summary() );
        System.in.read();
        System.out.println ( "clean nulls");
        System.out.println (s.clean_null());
        System.in.read();
        System.out.println ( "clean dupl");
        System.out.println (s.clean_duplicate());


        System.out.println ("=======transfor from data frame to list==============");
        j= s.transform_to_list();


//        s.pie_chart();
//
//        System.out.println("this is the first barchart");
//        System.in.read();
//        s.bar_chart_one(j);
//
//        System.out.println("this is the second barchart");
//        System.in.read();
//        s.bar_chart_two(j);

        System.out.println("this is the skills");
        System.in.read();
    }


    public static DataFrame read( ) {
        JobProvider wProvider = new JobProvider ();
        DataFrame trainData = wProvider.readCSV (trainPath);
        df=trainData;
        return df;
    }


    public static DataFrame read_display( ) {
        JobProvider wProvider = new JobProvider ();
        DataFrame trainData = wProvider.readCSV (trainPath);
        df=trainData;
        return df.structure();
    }

    public static DataFrame display_summary( ) {
        read();
        return df.summary();

    }


    public static DataFrame clean_null( ) {
        read();
        df = DataFrame.of(df.stream().filter(r-> !r.getString("YearsExp").contains("null")));
        df = DataFrame.of(df.stream().filter(r-> !r.getString("Skills").contains("null")));
        return df;
    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    public  List<Job> clean_duplicate( ) {
        clean_null();
        j = transform_to_list();
        j=j.stream().filter( distinctByKey(j -> j.getTitle() + " " + j.getCompany() +" "+j.getLocation() + " " + j.getType() +" " +j.getLevel() + " " + j.getCountry() +" "+j.getYearsExp() + " " + j.getSkills() +" "      )   ).collect(Collectors.toList());
        return j;
    }


    public  List<Job> transform_to_list( ) {
        JobProvider jProvider = new JobProvider ();
        List<Job>  alljobs=jProvider.getJobList(df);
        return alljobs;
    }
    public Map<String, Long> TopCompanies()
    {
        List<Job> jobsList = clean_duplicate();
        Map<String, Long> result =
                jobsList.stream().collect(
                        Collectors.groupingBy(
                                Job::getCompany, Collectors.counting()
                        )
                );
        return result;
    }

    public  String pie_chart() throws IOException {
        Map<String, Long> result = TopCompanies();
        List<String> s = result.entrySet().stream().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map(Map.Entry::getKey).limit(6).collect(Collectors.toList());
        List<Long> i = result.entrySet().stream().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map(Map.Entry::getValue).limit(6).collect(Collectors.toList());

        PieChart chart = new PieChartBuilder().width(800).height(600).title ("Top Companies demanding Jobs").build();

        int size = result.size();
        for (int k = 0; k < 6; k++) {
            chart.addSeries(s.get(k), i.get(k));
        }

        String path ="src/main/resources/Images/pieChart.png";
        BitmapEncoder.saveBitmap(chart, "./src/main/resources/Images/pieChart", BitmapEncoder.BitmapFormat.PNG);

        return path;
    }

    public Map<String, Long> popular_job_titles()
    {
        List<Job> jobList = clean_duplicate();
        Map<String, Long> result =
                jobList.stream ().collect (
                        Collectors.groupingBy (
                                Job::getTitle, Collectors.counting ()
                        )
                )  ;
        return result;
    }

public String bar_chart_one() throws IOException {

        Map<String, Long> result = popular_job_titles();
        List<Long> counts = result.entrySet().stream ().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map (Map.Entry::getValue  ).limit(8).collect (Collectors.toList ());
        List<String> titles = result.entrySet().stream ().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map (Map.Entry::getKey  ).limit(8).collect (Collectors.toList ());

        for(int j=0;j<8;j++)
        {
            System.out.println(titles.get(j)+" : "+counts.get(j));
        }

        CategoryChart chart = new CategoryChartBuilder().width (1024).height (500).title ("Titles Popularity").xAxisTitle ("Titles").yAxisTitle ("Count").build ();

        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);

        chart.addSeries ("Most Popular Job Titles", titles, counts);
        String path ="src/main/resources/Images/barChart1.png";
        BitmapEncoder.saveBitmap(chart, "./src/main/resources/Images/barChart1", BitmapEncoder.BitmapFormat.PNG);

        return path;
    }
    public Map<String, Long> popular_areas()
    {
        List<Job> jobList = clean_duplicate();
        Map<String, Long> result =
                jobList.stream ().collect (
                        Collectors.groupingBy (
                                Job::getLocation, Collectors.counting ()
                        )
                )  ;
        return result;
    }
    public String bar_chart_two() throws IOException {

        Map<String, Long> result = popular_areas();
        List<Long> counts = result.entrySet().stream ().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map (Map.Entry::getValue  ).limit(15).collect (Collectors.toList ());
        List<String> areas = result.entrySet().stream ().sorted(Comparator.comparing(p1 -> p1.getValue(), Comparator.reverseOrder())).map (Map.Entry::getKey  ).limit(15).collect (Collectors.toList ());

        CategoryChart chart = new CategoryChartBuilder().width (1024).height (500).title ("Areas Popularity").xAxisTitle ("Areas").yAxisTitle ("Count").build ();

        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);

        chart.addSeries ("Most Popular Job Areas", areas, counts);
        String path ="src/main/resources/Images/barChart2.png";
        BitmapEncoder.saveBitmap(chart, "./src/main/resources/Images/barChart2", BitmapEncoder.BitmapFormat.PNG);

        return path;
    }

//
//    public Map<String, Long> popular_skills() {
//        List<Job> jobList = clean_duplicate();
//        Map<String, Long> result = new HashMap<>();
//        jobList.stream().forEach((job) -> {
//            String[] ss = job.getSkills().split(",");
//            for (String s:ss) {
//                s = s.strip();
//                result.put(s, result.containsKey(s) ? result.get(s) + 1 : 1);
//            }
//        });
//
//        Map<String,Long> sorted = result.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//        return sorted;
//
//    }
//
//
//    public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
//        return IntStream.range(0, keys.size()).boxed()
//                .collect(Collectors.toMap(keys::get, values::get));
//    }


    public Map<String, Long> popular_skills2() {
    List<String> ls = new ArrayList<>();
    List<List<String>>  nestedList= j.stream().map(i->i.getSkills()).filter(value -> value != null).map(r->Arrays.asList(r.split("\\s*,\\s*"))).collect(Collectors.toList()) ;
    nestedList.forEach(ls::addAll);
    Map<String, Long> result =
            ls.stream ().collect (
                    Collectors.groupingBy (p->p, Collectors.counting ())) ;
    List<Long> counts = result.entrySet().stream () .sorted( Comparator.comparing( p1->p1.getValue() ,Comparator.reverseOrder() )).map (Map.Entry::getValue  ).limit(8).collect (Collectors.toList ());
    List<String> skills = result.entrySet().stream () .sorted( Comparator.comparing( p1->p1.getValue() ,Comparator.reverseOrder() )).map (Map.Entry::getKey  ).limit(8).collect (Collectors.toList ());
    Map<String, Long> sorted = IntStream.range(0, skills.size())
                .collect(
                        HashMap::new,
                        (m, i) -> m.put(skills.get(i), counts.get(i)),
                        Map::putAll
                );

        return sorted;
}



    public int[]  encodeCategory(DataFrame df, String columnName) {
        String[] v = df.stringVector (columnName).distinct ().toArray (new String[]{});
        int[] yearsofExpValues = df.stringVector (columnName).factorize (new NominalScale(v)).toIntArray ();
        return yearsofExpValues;
    }


    public String KMeans(DataFrame df) throws IOException {
        int [] df_title = encodeCategory(df,"Title");
        df= df.merge(IntVector.of("title_factorize",df_title));

        int [] df_company = encodeCategory(df,"Company");
        df = df.merge(IntVector.of("company_factorize",df_company));

        DataFrame Kmeans = df.select("title_factorize","company_factorize");
        KMeans clusters = PartitionClustering.run(100,()-> KMeans.fit(Kmeans.toArray(),4));
        BufferedImage image = ScatterPlot.of(Kmeans.toArray(),clusters.y,'.').canvas().setAxisLabels("Companies","Titles").toBufferedImage(1024,500);
        File Out = new File("./src/main/resources/Images/Kmeans.png");
        ImageIO.write(image,"png",Out);

        return Out.getPath();
    }


}
