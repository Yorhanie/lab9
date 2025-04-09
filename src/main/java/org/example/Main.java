package org.example;

import lombok.SneakyThrows;
import org.example.dao.PatientDao;
import org.example.entities.Patient;
import org.example.view.ViewPatient;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private PatientDao patientDao;
    private ViewPatient viewPatient;
    private Scanner scanner;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    @SneakyThrows
    private void run(){
        Properties props = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("C:\\Users\\nautilus\\IdeaProjects\\lab9\\src\\main\\resources\\config.properties"))) {
            props.load(reader);
            Connection connection = DriverManager.getConnection(props.getProperty("url"), props);
            patientDao = new PatientDao(connection);
            viewPatient = new ViewPatient();
        }
        scanner = new Scanner(System.in);
        int m;
        while ((m = menu())!=0){
            switch (m) {
                case 1 -> {
                    showAll();
                }
                case 2 -> {
                    findByDiagnosis();
                }
                case 3 -> {
                    findByCardInterval();
                }
                case 4 -> {
                    findByPhoneNumber();
                }
                case 5 -> {
                    diagnosisAndPatientsDESC();
                }
                case 6 -> {
                    allDiagnosis();
                }
                case 7 -> {
                    numPatDiagnosis();
                }
            }
        }
    }

    private void showAll() {
        List<Patient> patient = patientDao.showAllPatients();
        viewPatient.showList(patient);
    }
    private void findByDiagnosis(){
        System.out.println("Введіть діагноз:");
        String name = scanner.next();
        List<Patient> patient = patientDao.defineDiagnosis(name);
        viewPatient.showList(patient);
    }
    private void findByCardInterval(){
        System.out.println("Введіть початок інтервалу");
        Long minCard = scanner.nextLong();
        System.out.println("Введіть кінець інтервалу");
        Long maxCard = scanner.nextLong();
        List<Patient> patient = patientDao.compareMedCardNumbers(minCard,maxCard);
        viewPatient.showList(patient);
    }
    private void findByPhoneNumber(){
        System.out.println("Введіть цифру");
        int num = scanner.nextInt();
        List<Patient> patient = patientDao.phoneNumbers(num);
        viewPatient.showList(patient);
    }

    private void diagnosisAndPatientsDESC() {
        List<Map.Entry<String, Integer>> patient = patientDao.allDiagnosisSorted();
        viewPatient.showList4(patient);
    }
    private void allDiagnosis() {
        List<String> patient = patientDao.allDiagnosis();
        viewPatient.showList2(patient);
    }
    private void numPatDiagnosis() {
        Map<String, Integer> patient = patientDao.allDiagnosisCount();
        viewPatient.showList3(patient);
    }

    private int menu(){
        System.out.println("""
                1. Показати усіх пацієнтів
                2. Знайти пацієнтів із діагнозом
                3. Знайти пацієнтів із номерами карток, які знаходяться в заданому інтервалі
                4. Знайти пацієнтів із номером телефону, котра починається з вказаної цифри
                5. Список діагнозів в порядку спадання кількості пацієнтів
                6. Список усіх діагнозів
                7. Список усіх діагнозів із кількістю пацієнтів
                0. Exit
                """);
        return scanner.nextInt();
    }
}