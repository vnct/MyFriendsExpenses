package com.example.myfriendsexpenses.app.model;


import android.os.Environment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 19/04/14
 */
@SuppressWarnings("unused")
public class CSVAction {
    private String path_file="";
    private String filelocation="";
    private String filename="expenses.csv";
    private String[] stringsTitleCSV = new String[]{"Name","Phone","Amount","Group","Comment"};
    public void createFile()
    {
        File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "FriendExpenses");
        if(!fileDir.exists()){
            try{

                fileDir.mkdir();
                //System.out.println("Dossier Créé");
                filelocation=fileDir.getAbsolutePath().toString();
            } catch (Exception e) {
                System.out.println("CSVAction -> createFile -> Dir Exist -> IOException");
                e.printStackTrace();
            }
        }
        else
        {
            //System.out.println("Dossier Existant");
            filelocation=fileDir.getAbsolutePath().toString();
        }
        File file = new File(filelocation+File.separator+filename);
        path_file = file.getAbsolutePath().toString();

        if(!file.exists()){
            try {
                //file.createNewFile();

                CSVWriter csvWriter = new CSVWriter(new FileWriter(path_file,true));
                csvWriter.writeNext(stringsTitleCSV);
                csvWriter.close();
            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("CSVAction -> createFile -> File Exist -> IOException");

            }

        }
    }
    public void rewritefile(List<String[]> strings1)
    {
        File file = new File(path_file);
        file.delete();
        createFile();
        CSVWriter csvWriter = null;
        try {
            csvWriter = new CSVWriter(new FileWriter(path_file,true));

        for (String[] aStrings1 : strings1) {
            csvWriter.writeNext(aStrings1);
        }

        csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void appendfile( String[] strings)
    {
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(path_file, true));
            csvWriter.writeNext(strings);
            csvWriter.close();
        }
        catch (IOException e) {
                System.out.println("CSVAction -> appendfile -> IOException");
                e.printStackTrace();
            }
    }
    public List<String[]> inversestrings(List<String[]> strings)
    {
        List<String[]> strings1 = new ArrayList<String[]>();
        for(int i=strings.size()-1; i>=0; i--)
        {
            strings1.add(strings.get(i));
        }
        return strings1;
    }
    public List<String[]> getCSV()
    {
        List<String[]> questionList = new ArrayList<String[]>();


        try {

            FileReader fileReader = new FileReader(path_file);
            CSVReader csvReader = new CSVReader(fileReader);

            String[] line;

            // throw away the header
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {
                questionList.add(line);
            }
            csvReader.close();
        } catch (IOException e) {
            System.out.println("CSVAction -> getCSV -> IOException");

        }
        return questionList;
    }
    public String getPath_file() {
        return path_file;
    }

    public void setPath_file(String path_file) {
        this.path_file = path_file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilelocation() {
        return filelocation;
    }

    public void setFilelocation(String filelocation) {
        this.filelocation = filelocation;
    }
}
