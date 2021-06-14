package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Please enter .csv file location:");
        File input = new File(scanner.nextLine());
        while(!input.exists() || !input.isFile()){
            System.out.println("Please enter a valid file:");
            input = new File(scanner.nextLine());
        }
        System.out.println("Please input desired directory for the output file:");
        String outStr = scanner.nextLine();
        File output = new File(outStr);
        while(!output.exists() || !output.isDirectory()){
            System.out.println("Please enter a valid directory:");
            outStr = scanner.nextLine();
            output = new File(outStr);
        }
        System.out.println("Please input desired output file name (default: output.html):");
        String outNme = scanner.nextLine();
        if(outNme.isBlank()){
            outNme = "\\output.html";
        }
        DataHandler handler = new DataHandler(input);
        try {
            HTMLGenerator generator = new HTMLGenerator();
            List<String[]> data = handler.ExtractData(handler.ReadRows());
            generator.GenerateHTML(data,outStr + outNme);

        } catch (FileNotFoundException | FileInvalidException e) {
            System.out.println(e);
        }
    }
}
