/*
 * INSTRUCTION:
 *     This is a Java starting code for hw11_2.
 *     When you finish the development, download this file.
 *     Note that the current filename is "Main.java".
 *     But rename it to "Main_hw11_2.java".
 *     After that, upload the renamed file on Canvas.
 */

// Finish the head comment with Abstract, ID, Name, and Date.
/*
 * Title: Main_hw11_2.java
 * Abstract: A program to simulate the operations of linear probing covered in the class.
 * ID: 3111
 * Name: Aaron Johnson
 * Date: 05/05/2023
 */

import java.util.Scanner;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
        int initialSize = sc.nextInt();
        
        // For the table, the first column of the 2D array is the value 
        // and the second column is the state. For the state I will use 
        // 0 as empty, 1 as active, and 2 as deleted. 
        ArrayList<ArrayList<Integer>> table = new ArrayList<>();
        for (int i = 0; i < initialSize; i++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            row.add(0);  
            row.add(0);
            table.add(row);
        }

        int numberOfCommands = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < numberOfCommands; i++) {
            String operation = sc.next();
            if (operation.equals("displayStatus")) {
                int num = sc.nextInt();
                displayStatus(table, num);
            } else if (operation.equals("insert")) {
                int num = sc.nextInt();
                insert(table, num);
            } else if (operation.equals("tableSize")) {
                tableSize(table);
            } else if (operation.equals("delete")) {
                int num = sc.nextInt();
                delete(table, num);
            } else if (operation.equals("search")) {
                int num = sc.nextInt();
                search(table, num);
            }
        }
        sc.close();
        
    }

    public static void displayStatus (ArrayList<ArrayList<Integer>> table, int num) {
        if (table.get(num).get(1) == 0) System.out.println("Empty");
        else { 
            System.out.print(table.get(num).get(0) + " ");
            if (table.get(num).get(1) == 1) System.out.println("Active");
            else System.out.println("Deleted");
        }
    }

    public static void tableSize (ArrayList<ArrayList<Integer>> table) {
        if (table.size() == 0) System.out.println("Empty");
        else System.out.println(table.size());
    }

    public static void delete (ArrayList<ArrayList<Integer>> table, int num) {
        for (int index = 0; index < table.size(); index++) 
            if (table.get(index).get(0) == num && table.get(index).get(1) == 1) table.get(index).set(1, 2);
    }
        

    public static void search (ArrayList<ArrayList<Integer>> table, int num) {
        boolean found = false;
        System.out.print(num + " ");
        for (int i = 0; i < table.size(); i++) if (table.get(i).get(0) == num && table.get(i).get(1) == 1) found = true;
        
        if (found) System.out.println("Found");
        else System.out.println("Not found");
    }

    public static void insert (ArrayList<ArrayList<Integer>> table, int num) {
        int index = num % table.size();
        int count = 1;
        for (int i = 0; i < table.size(); i++) if (table.get(i).get(1) == 1) count++;
        if ((double)count/table.size() < 0.5) {
            while (table.get(index).get(1) == 1) index = (index + 1) % table.size();
            table.get(index).set(0, num);
            table.get(index).set(1, 1);
        } else {
            rehash(table);
            insert(table, num);
        }
    }

    public static void rehash(ArrayList<ArrayList<Integer>> table) {
        int newSize = table.size() * 2;
        while (!isPrime(newSize)) newSize++;
        ArrayList<ArrayList<Integer>> newTable = new ArrayList<>();
        for (int i = 0; i < newSize; i++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            row.add(0);
            row.add(0);
            newTable.add(row);
        }
    
        for (int i = 0; i < table.size(); i++) {
            int value = table.get(i).get(0);
            if (value != 0 && table.get(i).get(1) == 1) {
                int index = value % newSize;
                while (newTable.get(index).get(1) == 1) index = (index + 1) % newSize;
                newTable.get(index).set(0, value);
                newTable.get(index).set(1, 1);
            }
        }
        table.clear();
        table.addAll(newTable);
    }

    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) if (n % i == 0) return false;
        return true;
    }

    
}

