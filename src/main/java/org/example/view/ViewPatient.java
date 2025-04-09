package org.example.view;

import org.example.entities.Patient;

import java.util.List;
import java.util.Map;

public class ViewPatient {
    public void showList(List<Patient> patient) {
        System.out.println(" --- All Patients --- ");
        patient.forEach(System.out::println);
        System.out.println(" --------------------- ");
    }
    public void showList2(List<String> patient) {
        for (int i = 0; i < patient.size(); i++) {
            System.out.println(patient.get(i));
        }
        System.out.println("--------------------");
    }
    public static void showList3(Map<String, Integer> patient){
        for (Map.Entry entry : patient.entrySet()) {
            System.out.println(entry.getKey()+" - "+ entry.getValue());
        }
    }
    public static void showList4(List<Map.Entry<String, Integer>> patient){
        for (Map.Entry entry : patient) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}