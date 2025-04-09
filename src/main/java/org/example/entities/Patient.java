package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient {
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String address;
    private long phone;
    private long medcard_number;
    private String diagnosis;
    private static int tempID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Patient patients = (Patient) o;
        return id == patients.id &&
                Objects.equals(address, patients.address) &&
                patients.medcard_number == medcard_number &&
                Objects.equals(name, patients.name) && Objects.equals(surname,
                patients.surname) &&
                Objects.equals(patronymic, patients.patronymic) &&
                Objects.equals(diagnosis, patients.diagnosis) &&
                Objects.equals(phone, patients.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, address, phone,
                medcard_number, diagnosis);
    }
    @Override
    public String toString(){
        return "\nПацієнт:\n"+
                "ID - "+id+'\n'+
                "Ім'я - "+name+'\n'+
                "Прізвище - "+ surname+'\n'+
                "По батькові - "+patronymic+'\n'+
                "Адреса - "+ address +'\n'+
                "Номер телефона - "+ phone +'\n'+
                "Номер медичної картки - "+ medcard_number +'\n'+
                "Діагноз - "+ diagnosis;
    }
    public Patient(String surname, String name, String patronymic, String
            address, int phone, long medcard_number, String diagnosis){
        tempID++;
        this.id = tempID;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.address = address;
        this.phone = phone;
        this.medcard_number = medcard_number;
        this.diagnosis = diagnosis;
    }
}