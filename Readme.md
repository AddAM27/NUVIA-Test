Simple program to extract Rock-Paper-Scizzors championship data from a .csv file and put them into user readable form in a .html file.


Instructions on how to use the program:
 - To run the program simply Open the RUN.bat file
 - the program then asks for the full path to the input .csv file
 - after that the program asks for path to output file directory, and output file name separately
 
Technologies used
 - As the programming language Java was used for its versatility and the fact it is the company's prefered language
 - HTML was used for the output file
 
 Design choices
 - Code is split into 3 basic classes + a custom Exception class
 - Main class contains the user input and output handling and calls the latter classes
 - DataHandler is responsible for extracting data from the input file and throws InvalidFileException if the file is empty or format is invalid
 - HTMLGenerarator is responsible for creating the output file containing all the extracted data from DataHandler
 
 @Author Adam Polách
