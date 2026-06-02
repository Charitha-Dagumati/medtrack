package org.example.medtrack.service;

import org.example.medtrack.entity.Medicine;
import org.example.medtrack.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    // Add medicine
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    // Get all medicines
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // Get medicine by ID
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
    }

    // Update medicine
    public Medicine updateMedicine(Long id, Medicine updatedMedicine) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        medicine.setName(updatedMedicine.getName());
        medicine.setCategory(updatedMedicine.getCategory());
        medicine.setQuantity(updatedMedicine.getQuantity());
        medicine.setPrice(updatedMedicine.getPrice());
        medicine.setExpiryDate(updatedMedicine.getExpiryDate());

        return medicineRepository.save(medicine);
    }

    // Delete medicine
    public void deleteMedicine(Long id) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        medicineRepository.delete(medicine);
    }
}