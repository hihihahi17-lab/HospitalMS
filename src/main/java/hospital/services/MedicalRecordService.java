package hospital.services;

import hospital.exceptions.EntityNotFoundException;
import hospital.models.MedicalRecord;
import hospital.persistence.MedicalRecordRepository;
import hospital.utils.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordService {

    private final List<MedicalRecord> records;
    private final MedicalRecordRepository repository;

    public MedicalRecordService() {
        this.repository = new MedicalRecordRepository();
        this.records = repository.loadAll();
    }

    public List<MedicalRecord> getAllRecords() {
        return records;
    }

    public MedicalRecord findById(int id) throws EntityNotFoundException {
        for (MedicalRecord r : records) {
            if (r.getRecordId() == id) return r;
        }
        throw new EntityNotFoundException("MedicalRecord", String.valueOf(id));
    }

    public List<MedicalRecord> getByPatient(int patientId) {
        List<MedicalRecord> result = new ArrayList<>();
        for (MedicalRecord r : records) {
            if (r.getPatientId() == patientId) result.add(r);
        }
        return result;
    }

    public List<MedicalRecord> getByDoctor(int doctorId) {
        List<MedicalRecord> result = new ArrayList<>();
        for (MedicalRecord r : records) {
            if (r.getDoctorId() == doctorId) result.add(r);
        }
        return result;
    }

    public MedicalRecord addRecord(int patientId, int doctorId,
                                   int appointmentId, String diagnosis,
                                   String notes, LocalDate date) {

        int id = IdGenerator.generateRecordId(records.size());

        MedicalRecord rec = new MedicalRecord(
                id, patientId, doctorId,
                appointmentId, diagnosis, notes, date
        );

        records.add(rec);
        persist();
        return rec;
    }

    public void update(int id, String diagnosis, String notes)
            throws EntityNotFoundException {

        MedicalRecord r = findById(id);
        r.setDiagnosis(diagnosis);
        r.setNotes(notes);
        persist();
    }

    public void delete(int id) throws EntityNotFoundException {
        records.remove(findById(id));
        persist();
    }

    private void persist() {
        repository.saveAll(records);
    }
}