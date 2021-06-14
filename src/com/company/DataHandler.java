package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Class responsible for extracting data from a csv file
 *
 * @author Adam Polách
 */
public class DataHandler {

    private static final String HEAD = "Player1;Player2;Winner;";
    private static final int MAXLEN = 1000;
    private File input;

    /**
     * Basic constructor for making an instance of this class
     * @param input input file
     */
    public DataHandler(File input){
        this.input = input;
    }

    /**
     * Reads all rows of a csv file and check if the file format is "Player1;Player2;Winner;"
     * @return returns a list of all rows
     * @throws FileNotFoundException This exception should never happen existence of the file is checked
     * before accessing this method
     * @throws FileInvalidException Throws this exception if the file doesn't match the specified format
     */
    public List<String> ReadRows() throws FileNotFoundException, FileInvalidException {
        Scanner reader = new Scanner(input);
        if(!reader.hasNextLine()){
            throw new FileInvalidException("File is empty");
        }
        String header = reader.nextLine();
        if(!header.equals(HEAD)){
            throw new FileInvalidException("File header doesn't match predicate or missing");
        }

        List<String> rows = new ArrayList<>();
        short rowno = 1;
        while(reader.hasNextLine()){
            if(rowno > 1000){
              throw new FileInvalidException("File is larger than the specified limit: " + MAXLEN);
            }
            String line = reader.nextLine();
            String[] split = line.split(";");
            if (split.length != 3){
                throw new FileInvalidException("Data on row number " + (rowno + 1)+ " are in an invalid format");
            }
            if (!split[2].equals(split[0]) && !split[2].equals(split[1])){
                throw new FileInvalidException("Winner name on row number " + (rowno + 1) + " doesn't match either player");
            }
            rows.add(line);
            rowno++;
        }
        return rows;
    }

    /**
     * Finds index of a player in list
     * @param list list of players
     * @param name name to find
     * @return
     */
    private int GetNameInList(List<String[]> list, String name){
        int counter = 0;
        for (String[] arr: list) {
            if(arr[0].equals(name)){
                return counter;
            }
            counter++;
        }
        return -1;
    }

    private void EditPlayer(List<String[]> list,String name, boolean isWinner, int index){
        String[] current = list.get(index);
        int wins = Integer.parseInt(current[1]);
        int games = Integer.parseInt(current[2]) + 1;
        if(isWinner){
            wins++;
        }

        String[] newData = {
                name,
                String.format("%d",wins),
                String.format("%d",games),
                String.format("%d",(int)((wins * 100.0f) / games))
        };
        list.set(index, newData);
    }

    /**
     * Function that extracts game data from a list of Strings
     * @param rows List of strings in format P1;P2;Winner; to extract data from
     * @return returns a List of arrays in format #0 Name, #1 wins #2 games #3 WinRate%
     */
    public List<String[]> ExtractData(List<String> rows){
        List<String[]> data = new ArrayList<>();
        for (String row : rows) {
            String[] rowData = row.split(";");

            int p1Index = GetNameInList(data, rowData[0]);
            int p2Index = GetNameInList(data, rowData[1]);

            if(p1Index == -1){
                String[] newP1 = {
                        rowData[0],
                        rowData[0].equals(rowData[2]) ? "1" : "0",
                        "1",
                        rowData[0].equals(rowData[2]) ? "100" : "0"
                        };
                data.add(newP1);
            }
            else{
                EditPlayer(data, rowData[0], rowData[0].equals(rowData[2]), p1Index);
            }

            if(p2Index == -1){
                String[] newP2 = {
                        rowData[1],
                        rowData[1].equals(rowData[2]) ? "1" : "0",
                        "1",
                        rowData[1].equals(rowData[2]) ? "100" : "0"
                };
                data.add(newP2);
            }
            else{
                EditPlayer(data, rowData[1], rowData[1].equals(rowData[2]), p2Index);
            }
        }
        return data;
    }
}
