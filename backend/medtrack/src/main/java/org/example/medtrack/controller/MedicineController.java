package org.example.medtrack.controller;

import org.example.medtrack.dto.MedicineRequestDTO;
import org.example.medtrack.dto.MedicineResponseDTO;
import org.example.medtrack.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    // ➤ ADD MEDICINE
    @PostMapping
    public MedicineResponseDTO addMedicine(@RequestBody MedicineRequestDTO dto) {
        return medicineService.saveMedicine(dto);
    }

    // ➤ GET ALL
    @GetMapping
    public List<MedicineResponseDTO> getAll() {
        return medicineService.getAllMedicines();
    }

    // ➤ GET BY ID
    @GetMapping("/{id}")
    public MedicineResponseDTO getById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    // ➤ UPDATE
    @PutMapping("/{id}")
    public MedicineResponseDTO update(@PathVariable Long id,
                                      @RequestBody MedicineRequestDTO dto) {
        return medicineService.updateMedicine(id, dto);
    }

    // ➤ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "Medicine deleted successfully";
    }
}