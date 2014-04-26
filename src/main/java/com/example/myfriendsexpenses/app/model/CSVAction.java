package com.example.myfriendsexpenses.app.model;


import android.os.Environment;

import com.example.myfriendsexpenses.app.controler.Expense;
import com.example.myfriendsexpenses.app.controler.Person;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 19/04/14.
 */
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

    public void removePerson(Person person,Expense expense, int position)
    {
        try
        {
           // String[] strings = new String[]{person.get_name(),person.get_phoneNumber(),Float.toString(expense.getExpenses()),person.get_groupname(),expense.getComments()};

            String[] strings = new String[]{person.get_name(),person.get_phoneNumber(),Float.toString(expense.getExpenses()),person.get_groupname(),expense.getComments()};
            List<String[]> strings1 = getCSV();
            for(int istrings1=0;istrings1<strings1.size();istrings1++)
            {
                if((strings1.get(istrings1)[0].equals(strings[0]))&&(strings1.get(istrings1)[1].equals(strings[1]))&&(Float.toString(Float.parseFloat(strings1.get(istrings1)[2])).equals(strings[2]))&&(strings1.get(istrings1)[3].equals(strings[3]))&&(strings1.get(istrings1)[4].equals(strings[4])))
                {

                    if(position==istrings1)
                    {

                        strings1.remove(istrings1);
                    }


                }
            }
            File file = new File(path_file);
            file.delete();
            createFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(path_file,true));
            for (String[] aStrings1 : strings1) {
                csvWriter.writeNext(aStrings1);
            }
            csvWriter.close();

        }
        catch (Exception e)
        {
            System.out.println("CSVAction -> removePerson -> Exception");
        }

    }
    public void changePerson(Person person_old,Expense expense_old,Person person_new,Expense expense_new, int position)
    {
        try
        {
            // String[] strings = new String[]{person.get_name(),person.get_phoneNumber(),Float.toString(expense.getExpenses()),person.get_groupname(),expense.getComments()};

            String[] strings = new String[]{person_old.get_name(),person_old.get_phoneNumber(),Float.toString(expense_old.getExpenses()),person_old.get_groupname(),expense_old.getComments()};
            List<String[]> strings1 = getCSV();
            for(int istrings1=0;istrings1<strings1.size();istrings1++)
            {
                if((strings1.get(istrings1)[0].equals(strings[0]))&&(strings1.get(istrings1)[1].equals(strings[1]))&&(Float.toString(Float.parseFloat(strings1.get(istrings1)[2])).equals(strings[2]))&&(strings1.get(istrings1)[3].equals(strings[3]))&&(strings1.get(istrings1)[4].equals(strings[4])))
                {
                    if(position==istrings1)
                    {

                        strings1.get(istrings1)[0] = person_new.get_name();
                        strings1.get(istrings1)[1] = person_new.get_phoneNumber();
                        strings1.get(istrings1)[2] = Float.toString(expense_new.getExpenses());
                        strings1.get(istrings1)[3] = person_new.get_groupname();
                        strings1.get(istrings1)[4] = expense_new.getComments();

                    }

                }
            }
            File file = new File(path_file);
            file.delete();
            createFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(path_file,true));
            for (String[] aStrings1 : strings1) {
                csvWriter.writeNext(aStrings1);
            }
            csvWriter.close();

        }
        catch (Exception e)
        {
            System.out.println("CSVAction -> removePerson -> Exception");
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
    public void addPersonCSV(Person person,Expense expense)
    {
        try {
           // System.out.println("addPersonCSV Try 1 ");
            String[] strings = new String[]{person.get_name(),person.get_phoneNumber(),Float.toString(expense.getExpenses()),person.get_groupname(),expense.getComments()};
           /* strings[0] = person.get_name();
            strings[1] = person.get_phoneNumber();
            strings[2] = Float.toString(person.get_expenses());
            strings[3] = person.get_groupname();*/


            CSVWriter csvWriter = new CSVWriter(new FileWriter(path_file,true));
            csvWriter.writeNext(strings);
            csvWriter.close();

        } catch (IOException e) {
            System.out.println("CSVAction -> addPersonCSV -> IOException");
            e.printStackTrace();
        }

    }

    public List<String[]> getCSV()
    {
        List<String[]> questionList = new ArrayList<String[]>();
      //  AssetManager assetManager = context.getAssets();


        try {

            FileReader fileReader = new FileReader(path_file);
            CSVReader csvReader = new CSVReader(fileReader);
            /*InputStream csvStream = assetManager.open(path_file);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);*/
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
