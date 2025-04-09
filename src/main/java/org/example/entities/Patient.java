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
        return  "\nID: " + id +
                ". Ім'я: " + name +
                ". Прізвище: " + surname +
                ". По-батькові: " + patronymic +
                ". Адреса: " + address +
                ". Номер телефона: " + phone +
                ". Номер медичної картки: " + medcard_number +
                ". Діагноз: " + diagnosis;
    }
}