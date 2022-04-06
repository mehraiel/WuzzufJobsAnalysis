package com.example.demo;

import org.apache.commons.codec.binary.Base64;
import smile.data.DataFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class HTMLBuilder {
    private final StringBuilder table = new StringBuilder();
    public static String STYLE = "<head><style>" +"tr:nth-child(even) { background-color: #9AAAB8;" +
            "height:70px; }" +
            "</style></head>";
    public static String HTML_START = "<html>";
    public static String HTML_END = "</html>";
    public static String TABLE_START = "<table border=\"2;\" align=\"center\"  >";
    public static String TABLE_END = "</table>";
    public static String ROW_START = "<tr >";
    public static String ROW_END = "</tr>";
    public static String HEADER_START = "<th style=\"width:100px;\" color=#005500>";
    public static String HEADER_END = "</th>";
    public static String COLUMN_START = "<td style=\"width:100px;\">";
    public static String COLUMN_END = "</td>";
    public static String HEAD_START = "<h1 style=\"text-align: center; background-color:#032A4B; color:white; padding: 15px 15px 15px 15px\">";
    public static String HEAD_END = "</h1>";
    public static DataFrame df;
    public static List<Job> j;

    public HTMLBuilder(String func,DataFrame df)
    {
        this.df = df;
        table.append(HTML_START);
        table.append(STYLE);
        table.append(HEAD_START);
        table.append(func);
        table.append(HEAD_END);
        table.append(TABLE_START);
        table.append(TABLE_END);
        table.append(HTML_END);

    }
    public HTMLBuilder(String func,List<Job> j)
    {
        this.j = j;
        table.append(HTML_START);
        table.append(STYLE);
        table.append(HEAD_START);
        table.append(func);
        table.append(HEAD_END);
        table.append(TABLE_START);
        table.append(TABLE_END);
        table.append(HTML_END);
    }

    public HTMLBuilder(String func)
    {
        table.append(HTML_START);
        table.append(STYLE);
        table.append(HEAD_START);
        table.append(func);
        table.append(HEAD_END);
        table.append(TABLE_START);
        table.append(TABLE_END);
        table.append(HTML_END);
    }

    public void addTableHeader()
    {
        int lastIndex = table.lastIndexOf(TABLE_END);
        if (lastIndex > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(ROW_START);
            for (String value : df.names()) {
                sb.append(HEADER_START);
                sb.append(value);
                sb.append(HEADER_END);
            }
            sb.append(ROW_END);
            table.insert(lastIndex, sb.toString());
        }
    }
    public void addTableHeaderList()
    {
        int lastIndex = table.lastIndexOf(TABLE_END);
        if (lastIndex > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(ROW_START);
                sb.append(HEADER_START);
                sb.append("Title");
                sb.append(HEADER_END);
                sb.append(HEADER_START);
                sb.append("Company");
                sb.append(HEADER_END);
                sb.append(HEADER_START);
                sb.append("Location");
                sb.append(HEADER_END);
                sb.append(HEADER_START);
                sb.append("Type");
                sb.append(HEADER_END);
                sb.append(HEADER_START);
                sb.append("Level");
                sb.append(HEADER_END);
                sb.append(HEADER_START);
                sb.append("Years Exp");
                sb.append(HEADER_END);
                sb.append(HEADER_START);
                sb.append("Country");
                sb.append(HEADER_END);
                sb.append(HEADER_START);
                sb.append("Skills");
                sb.append(HEADER_END);
            sb.append(ROW_END);
            table.insert(lastIndex, sb.toString());
        }
    }
    public void addTableValuesFromDataframe(int iter)
    {
        df.stream().forEach(r -> {
            String[] s = r.toString().replace("{","").replace("}","")
                    .split(",",iter);
            int lastIndex2 = table.lastIndexOf(ROW_END);
            if (lastIndex2 > 0) {
                int index = lastIndex2 + ROW_END.length();
                StringBuilder sb = new StringBuilder();
                sb.append(ROW_START);
                for (String value : s) {
                    sb.append(COLUMN_START);
                    if(value.contains(":"))
                    {
                        String[] values = value.split(":");
                        sb.append(values[1]);
                    }
                    else
                        sb.append(value);
                    sb.append(COLUMN_END);
                }
                sb.append(ROW_END);
                table.insert(index, sb.toString());
            }
        });
    }
    public void addTableValuesFromList()
    {
        j.stream().forEach(job->{
            int lastIndex2 = table.lastIndexOf(ROW_END);
            if (lastIndex2 > 0) {
                int index = lastIndex2 + ROW_END.length();
                StringBuilder sb = new StringBuilder();
                sb.append(ROW_START);
                    sb.append(COLUMN_START);
                    sb.append(job.getTitle());
                    sb.append(COLUMN_END);
                    sb.append(COLUMN_START);
                    sb.append(job.getCompany());
                    sb.append(COLUMN_END);
                    sb.append(COLUMN_START);
                    sb.append(job.getLocation());
                    sb.append(COLUMN_END);
                    sb.append(COLUMN_START);
                    sb.append(job.getType());
                    sb.append(COLUMN_END);
                    sb.append(COLUMN_START);
                    sb.append(job.getLevel());
                    sb.append(COLUMN_END);
                    sb.append(COLUMN_START);
                    sb.append(job.getYearsExp());
                    sb.append(COLUMN_END);
                    sb.append(COLUMN_START);
                    sb.append(job.getCountry());
                    sb.append(COLUMN_END);
                    sb.append(COLUMN_START);
                    sb.append(job.getSkills());
                    sb.append(COLUMN_END);
                sb.append(ROW_END);
                table.insert(index, sb.toString());
            }
        });
    }
    public String displayTableFromDataframe(int iter) {
        addTableHeader();
        addTableValuesFromDataframe(iter);
        return build();
    }
    public String displayTableFromList() {
        addTableHeaderList();
        addTableValuesFromList();
        return build();
    }
    public String display(String name, List<String> s, List<?> l) {
        int lastIndex = table.lastIndexOf(TABLE_END);
        if (lastIndex > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(ROW_START);
            sb.append(HEADER_START);
            sb.append(name);
            sb.append(HEADER_END);
            sb.append(HEADER_START);
            sb.append("Count");
            sb.append(HEADER_END);
            sb.append(ROW_END);
            table.insert(lastIndex, sb.toString());
        }
        int lastIndex2 = table.lastIndexOf(ROW_END);
        if (lastIndex2 > 0) {
            int index = lastIndex2 + ROW_END.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.size(); i++) {
                sb.append(ROW_START);
                sb.append(COLUMN_START);
                sb.append(s.get(i));
                sb.append(COLUMN_END);
                sb.append(COLUMN_START);
                sb.append(l.get(i));
                sb.append(COLUMN_END);
                sb.append(ROW_END);
            }
            table.insert(index, sb.toString());
        }
        return build();
    }
    public String build() {

        return table.toString();
    }

    public  String viewchart(String path,String chart_name){

        FileInputStream img ;
        try {
            File f= new File(path);
            img = new FileInputStream(f);
            byte[] bytes =  new byte[(int)f.length()];
            img.read(bytes);
            String encodedfile = new String(Base64.encodeBase64(bytes) , "UTF-8");

            return "<div style =\"margin:auto;\" >" +
                    "<img src=\"data:image/png;base64, "+encodedfile+"\" alt=\"Red dot;\" style=\"margin-right:auto;  margin-left:250;\"/>" +
                    "</div>";
        } catch (IOException e) {
            return "error";
        }
    }


}
