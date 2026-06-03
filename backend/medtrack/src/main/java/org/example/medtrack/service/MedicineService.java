package org.example.medtrack.service;

import org.example.medtrack.dto.MedicineRequestDTO;
import org.example.medtrack.dto.MedicineResponseDTO;
import org.example.medtrack.entity.Medicine;
import org.example.medtrack.mapper.MedicineMapper;
import org.example.medtrack.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    // ➤ ADD MEDICINE
    public MedicineResponseDTO saveMedicine(MedicineRequestDTO dto) {

        Medicine medicine = MedicineMapper.toEntity(dto);

        Medicine saved = medicineRepository.save(medicine);

        return MedicineMapper.toDTO(saved);
    }

    // ➤ GET ALL MEDICINES
    public List<MedicineResponseDTO> getAllMedicines() {

        return medicineRepository.findAll()
                .stream()
                .map(MedicineMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ➤ GET BY ID
    public MedicineResponseDTO getMedicineById(Long id) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        return MedicineMapper.toDTO(medicine);
    }

    // ➤ UPDATE MEDICINE
    public MedicineResponseDTO updateMedicine(Long id, MedicineRequestDTO dto) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        medicine.setName(dto.getName());
        medicine.setCategory(dto.getCategory());
        medicine.setQuantity(dto.getQuantity());
        medicine.setPrice(dto.getPrice());
        medicine.setExpiryDate(dto.getExpiryDate());

        Medicine updated = medicineRepository.save(medicine);

        return MedicineMapper.toDTO(updated);
    }

    // ➤ DELETE MEDICINE
    public void deleteMedicine(Long id) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        medicineRepository.delete(medicine);
    }
}