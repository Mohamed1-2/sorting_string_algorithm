import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class sort {


    // Main driver method
    public static void main(String[] args) throws Exception {
        // extract the words from the text file into arraylist
        URL url = new URL("https://www-cs-faculty.stanford.edu/~knuth/sgb-words.txt");
        Scanner words = new Scanner(url.openStream());
        ArrayList<String> mylist = new ArrayList<String>();
        //add the words to mylist arraylist
        while (words.hasNext()) {
            mylist.add(words.nextLine());
        }
        //replace the signs with space
        mylist.toString().replaceAll("\\[|\\]|[,][ ]", "\t");

        String temp;
        int n = mylist.size();
        String [] tmp = new String[n];
        mergeSort(mylist, tmp,  0,  n - 1);
        System.out.println("Words after sorted By merge Sort : ");
        Disply_sorted_list(mylist);

        qsort(mylist, 0, n - 1);
        System.out.println("Words after sorted By Quick Sort : ");
        Disply_sorted_list(mylist);

        three_way_qort(mylist,0,n-1);
        System.out.println("Words after sorted By 3 Way Quick Sort : ");
        Disply_sorted_list(mylist);

        execution_Time(mylist);

    }
  // display the list after it sorted the words
    public static void Disply_sorted_list(ArrayList<String> input) {

        for (int i = 0; i < input.size(); i++) {
            System.out.print(input.get(i) + " ");
        }
        System.out.println("\n");
    }


    public static void qsort(ArrayList<String> mylist, int left, int right) {
        //base case

        if (right <= left || left >= right) {
        } else {
            String pivot = mylist.get(left);
            int i = left + 1;
            String tmp;
            //partition array
            for (int j = left+1 ; j <= right; j++) {
                if (pivot.charAt(0) > mylist.get(j).charAt(0)) {
                    tmp = mylist.get(j);
                    mylist.set(j, mylist.get(i));
                    mylist.set(i, tmp);

                    i++;
                }
            }
            //put pivot in right position
            mylist.set(left, mylist.get(i - 1));
            mylist.set(i - 1, pivot);

            //call qsort on right and left sides of pivot
            qsort(mylist, left, i - 2);
            qsort(mylist, i, right);
        }

    }
    private static void mergeSort(ArrayList<String> mylist, String tmp[ ], int left, int right)
    {
        if( left < right )
        {
            int mid = (left + right) / 2;
            mergeSort(mylist, tmp, left, mid);
            mergeSort(mylist, tmp, mid + 1, right);
            merge(mylist, tmp, left, mid + 1, right);
        }
    }


    private static void merge(ArrayList<String> mylist,String tmp[ ], int left, int right, int rightEnd )
    {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd)
            if(mylist.get(left).charAt(0) <= mylist.get(right).charAt(0))
                tmp[k++] = mylist.get(left++);
            else
                tmp[k++] = mylist.get(right++);

        while(left <= leftEnd)    // Copy rest of first half
            tmp[k++] = mylist.get(left++);

        while(right <= rightEnd)  // Copy rest of right half
            tmp[k++] = mylist.get(right++);

        // Copy tmp back
        for(int i = 0; i < num; i++, rightEnd--)
            mylist.set(rightEnd, tmp[rightEnd]);
    }

    private static void three_way_qort(ArrayList<String> mylist, int left, int right) {
        int less_piv = left;// less_piv: to store the index lower then the  pivot
        int greater_piv = right; //greater_piv: to store the index greater then the  pivot
        int n = left + 1; // declare integer n to store the left index (usually= 0)  +1
        if (left >= right)   return;// base case
        int mid = left + (right - left) / 2; // get the middle element as the pivot
        String pivot = mylist.get(mid); // assign the middle element in the list to the pivot
        // move pivot to the front
        String temp = mylist.get(left); // assign the first element in the list inside temp varible

        mylist.set(left, mylist.get(mid)); // move the middle element as the  first element
        mylist.set(mid, temp);
        while (n <= greater_piv) { // while n not reaching greater_piv index go through the loop
            if (mylist.get(n).charAt(0) < pivot.charAt(0))     // check the first char of each word if its less then the fist char of the pivot
                swap_words(mylist, n++, less_piv++);                // then  move the words that are less then the pivot to the front
            else if (mylist.get(n).charAt(0) > pivot.charAt(0)) //if the current char of a word index n greater then the pivot
                swap_words(mylist, n, greater_piv--);//then decrease the greater_piv by one index

            else    n++; // if equal to the pivont then keep the words on the same position
        }
        // Invariant: A[start .. lt - 1] < A[lt .. gt] = pivot < A[gt + 1 .. end]
        three_way_qort(mylist, left, less_piv - 1);
        three_way_qort(mylist, greater_piv + 1, right);
    }


// function to make swap for words by given left and right indexes
    private static void swap_words(ArrayList<String> mylist, int left, int right) {
        String temp = mylist.get(left);
        mylist.set(left, mylist.get(right));
        mylist.set(right, temp);
    }


    static void execution_Time(ArrayList<String> mylist) {

        double totaltime_qsort;
        double totaltime_merge;
        double totaltime_3qsort;

        // create an array with 10  elements
        int[] Total_run_time = new int[10];
        //create an array for each algo type double to store all times execution per each range of words
        double[] all_times_qsort = new double[10];
        double[] all_times_merge = new double[10];
        double[] all_times_3qsort = new double[10];

        ArrayList<String> list_1 = new ArrayList<String>();
        ArrayList<String> list_2 = new ArrayList<String>();
        ArrayList<String> list_3 = new ArrayList<String>();

        int i = 0;

        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 100));
            list_2.addAll(mylist.subList(0, 100));
            list_3.addAll(mylist.subList(0, 100));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);



             // convert time to seconds
            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;


            // clear lists to insert another rang of words
            list_1.clear();
            list_2.clear();
            list_3.clear();

            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;
        }
        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 300));
            list_2.addAll(mylist.subList(0, 300));
            list_3.addAll(mylist.subList(0, 300));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);



            // convert time to seconds
            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;


            // clear lists to insert another rang of words
            list_1.clear();
            list_2.clear();
            list_3.clear();

            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;
        }
        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 500));
            list_2.addAll(mylist.subList(0, 500));
            list_3.addAll(mylist.subList(0, 500));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);


            // convert time to seconds
            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;


            // clear lists to insert another rang of words
            list_1.clear();
            list_2.clear();
            list_3.clear();

            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;
        }
        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 800));
            list_2.addAll(mylist.subList(0, 800));
            list_3.addAll(mylist.subList(0, 800));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);



            // convert time to seconds
            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;


            // clear lists to insert another rang of words
            list_1.clear();
            list_2.clear();
            list_3.clear();

            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;
        }
        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 1200));
            list_2.addAll(mylist.subList(0, 1200));
            list_3.addAll(mylist.subList(0, 1200));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);



            // convert time to seconds
            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;


            // clear lists to insert another rang of words
            list_1.clear();
            list_2.clear();
            list_3.clear();

            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;
        }
        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 1800));
            list_2.addAll(mylist.subList(0, 1800));
            list_3.addAll(mylist.subList(0, 1800));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);



            // convert time to seconds
            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;


            // clear lists to insert another rang of words
            list_1.clear();
            list_2.clear();
            list_3.clear();

            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;
        }
        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 2500));
            list_2.addAll(mylist.subList(0, 2500));
            list_3.addAll(mylist.subList(0, 2500));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);



            // convert time to seconds
            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;


            // clear lists to insert another rang of words
            list_1.clear();
            list_2.clear();
            list_3.clear();

            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;
        }
        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 3500));
            list_2.addAll(mylist.subList(0, 3500));
            list_3.addAll(mylist.subList(0, 3500));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);



            // convert time to seconds
            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;


            // clear lists to insert another rang of words
            list_1.clear();
            list_2.clear();
            list_3.clear();

            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;
        }
        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 4500));
            list_2.addAll(mylist.subList(0, 4500));
            list_3.addAll(mylist.subList(0, 4500));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);



            // convert time to seconds
            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;


            // clear lists to insert another rang of words
            list_1.clear();
            list_2.clear();
            list_3.clear();

            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;
        }
        for (; i < Total_run_time.length; ) {
            // insert firt 100 words in the lists
            list_1.addAll(mylist.subList(0, 5757));
            list_2.addAll(mylist.subList(0, 5757));
            list_3.addAll(mylist.subList(0, 5757));

            int low = 0;
            int n = list_1.size();
            int high = n - 1;
            String [] tmp = new String[n];
            // start timer for quick sort execution
            long startTime_qsort = System.nanoTime();
            qsort(list_1,
                    low,
                    high);
            long endTime_qsort = System.nanoTime();
            // start timer for mergeSort execution
            long startTime_merge = System.nanoTime();
            mergeSort(list_2, tmp,  low,  high);
            long endTime_merge = System.nanoTime();
            // start timer for three_way_qort execution
            long startTime_3qsort = System.nanoTime();
            three_way_qort(list_3,  low,  high);
            long endTime_3qsort = System.nanoTime();
            // get the total time execution by subtract the end time from start time
            totaltime_qsort = (endTime_qsort - startTime_qsort);
            totaltime_merge = (endTime_merge- startTime_merge);
            totaltime_3qsort = (endTime_3qsort - startTime_3qsort);




            // convert time to seconds

            double elapsedTimeInSecond_qsort = totaltime_qsort / 1_000_000_000;
            double elapsedTimeInSecond_merge = totaltime_merge / 1_000_000_000;
            double elapsedTimeInSecond_3qsort = totaltime_3qsort / 1_000_000_000;

            //get the average for each algo  by dividing sum of the results (elapsedTimeInSecond_qsort)  and number of Total run time
            totaltime_qsort = (elapsedTimeInSecond_qsort / Total_run_time.length);
            totaltime_merge = (elapsedTimeInSecond_merge / Total_run_time.length);
            totaltime_3qsort = (elapsedTimeInSecond_3qsort / Total_run_time.length);

            // assign the total times for each algo inside array containing all times for that algo
            all_times_qsort[i] = totaltime_qsort;
            all_times_merge[i] = totaltime_merge;
            all_times_3qsort[i] = totaltime_3qsort;



            // increase i by 1
            i++;
            //break the for loop coz we want only one loop
            break;

        }
        // display the lists with the size of each sorting wards range
        int[] size = new int[] {100, 300, 500, 800, 1200, 1800, 2500, 3500,4500,5757};
        System.out.println(""+"Size"+"                  "+ " Quick Sort" +"                    "+"Merge Sort"+ "                  "+ "3 Way Quick Sort" );
        for (int j = 0; j < 10; j++) {

            System.out.println(""+size[j]+"                  "+ all_times_qsort[j]  +"                  "+all_times_merge[j]+"                  "+ all_times_3qsort[j]);

        }


    }
}

