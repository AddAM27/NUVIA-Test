package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class responsible for generating a HTML file
 *
 * @author Adam Polách
 */
public class HTMLGenerator {

    /**
     * Basic constructor for making an instance of this class
     */
    public HTMLGenerator(){
    }

    private void WriteHTMLHeader(FileWriter writer) throws IOException {
        String str = """
                <!DOCTYPE html>
                <html>
                <body>
                """;
        writer.write(str);
    }

    private void WriteHTMLFooter(FileWriter writer) throws IOException {
        String str = """
                </body>
                </html>
                """;
        writer.write(str);
    }

    private void WriteLeaderboard(FileWriter writer, List<String[]> data) throws IOException {
        StringBuilder str = new StringBuilder();
        str.append("<p>Leaderboard:</p>\n");
        str.append("<table>\n");
        str.append("<tr>\n");
        str.append("<th><b>Player name</b></th>\n");
        str.append("<th><b>Success ratio</b></th>\n");
        str.append("</tr>\n");

        List<String[]> cpy = new ArrayList<>(data);
        cpy.sort((o1, o2) -> {
            int n1 = Integer.parseInt(o1[3]);
            int n2 = Integer.parseInt(o2[3]);
            return Integer.compare(n2, n1);
        });

        for (String[] row: cpy) {
            str.append("<tr>\n");
            str.append("<th>");
            str.append(row[0]);
            str.append("</th>\n");
            str.append("<th>");
            str.append(row[3]);
            str.append(" %</th>\n");
            str.append("</tr>\n");
        }
        str.append("</table>");
        writer.write(str.toString());
    }

    private void WritePlayerList(FileWriter writer, List<String[]> data) throws IOException {
        StringBuilder str = new StringBuilder();
        str.append("<p>Player List:</p>\n");
        str.append("<table>\n");
        str.append("<tr>\n");
        str.append("<th><b>Player name</b></th>\n");
        str.append("<th><b>Wins</b></th>\n");
        str.append("<th><b>Games Played</b></th>\n");
        str.append("<th><b>Success Ratio</b></th>\n");
        str.append("</tr>\n");

        List<String[]> cpy = new ArrayList<>(data);
        cpy.sort(Comparator.comparing(o -> o[0]));

        for (String[] row: cpy) {
            str.append("<tr>\n");
            str.append("<th>");
            str.append(row[0]);
            str.append("</th>\n");
            str.append("<th>");
            str.append(row[1]);
            str.append("</th>\n");
            str.append("<th>");
            str.append(row[2]);
            str.append("</th>\n");
            str.append("<th>");
            str.append(row[3]);
            str.append(" %</th>\n");
            str.append("</tr>\n");
        }
        str.append("</table>");
        writer.write(str.toString());
    }

    /**
     * Generates a Html file
     * @param data data extracted via the DataHandler Class
     * @param path path to the desired output file
     */
    public void GenerateHTML(List<String[]> data, String path){
        File output;
        FileWriter writer;
        try{
            output = new File(path);
            if(!output.createNewFile()){
                System.out.println("File already exists");
                return;
            }
            writer = new FileWriter(output,false);
            WriteHTMLHeader(writer);
            WriteLeaderboard(writer,data);
            WritePlayerList(writer,data);
            WriteHTMLFooter(writer);
            writer.close();
        } catch (IOException e){
            System.out.println("An IOException occurred while creating the output file");
        }

    }
}
