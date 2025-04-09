package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.entities.Patient;

import java.sql.*;
import java.util.*;

@AllArgsConstructor
public class PatientDao {
    private Connection connection;

    public List<Patient> showAllPatients() {
        ArrayList<Patient> list = new ArrayList<Patient>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM patient")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String surname = rs.getString("surname");
                String name = rs.getString("name");
                String patronymic = rs.getString("patronymic");
                String address = rs.getString("address");
                Long phone = rs.getLong("phone");
                Long medcard_number = rs.getLong("medcard_number");
                String diagnosis = rs.getString("diagnosis");
                list.add(new Patient(id, surname, name, patronymic, address, phone, medcard_number, diagnosis));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Patient> defineDiagnosis(String diagnosis) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE diagnosis = ? ORDER BY medcard_number, phone";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, diagnosis);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String surname = rs.getString("surname");
                String name = rs.getString("name");
                String patronymic = rs.getString("patronymic");
                String address = rs.getString("address");
                Long phone = rs.getLong("phone");
                Long medcard_number = rs.getLong("medcard_number");
                list.add(new Patient(id, surname, name, patronymic, address, phone, medcard_number, diagnosis));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while retrieving patients by diagnosis", e);
        }

        return list;
    }

    public List<String> allDiagnosis() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT diagnosis FROM patient";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("diagnosis"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all diagnoses", e);
        }

        return list;
    }

    public Map<String, Integer> allDiagnosisCount() {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT diagnosis, COUNT(*) AS count FROM patient GROUP BY diagnosis";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("diagnosis"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting patients by diagnosis", e);
        }

        return result;
    }

    public List<Map.Entry<String, Integer>> allDiagnosisSorted() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        String sql = "SELECT diagnosis, COUNT(*) AS count FROM patient GROUP BY diagnosis ORDER BY count DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new AbstractMap.SimpleEntry<>(
                        rs.getString("diagnosis"),
                        rs.getInt("count")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching sorted diagnosis counts", e);
        }

        return list;
    }

    public List<Patient> compareMedCardNumbers(long x1, long x2) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE medcard_number BETWEEN ? AND ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, x1);
            ps.setLong(2, x2);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapPatient(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error comparing medcard numbers", e);
        }

        return list;
    }

    public List<Patient> phoneNumbers(int num) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE phone LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, num + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapPatient(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching patients by phone prefix", e);
        }

        return list;
    }

    public int patNumCount(int num) {
        String sql = "SELECT COUNT(*) AS count FROM patient WHERE phone LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, num + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting patients by phone prefix", e);
        }
        return 0;
    }

    private Patient mapPatient(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String surname = rs.getString("surname");
        String name = rs.getString("name");
        String patronymic = rs.getString("patronymic");
        String address = rs.getString("address");
        Long phone = rs.getLong("phone");
        Long medcard_number = rs.getLong("medcard_number");
        String diagnosis = rs.getString("diagnosis");
        return new Patient(id, surname, name, patronymic, address, phone, medcard_number, diagnosis);
    }
}