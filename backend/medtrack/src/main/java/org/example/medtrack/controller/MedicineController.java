package org.example.medtrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.medtrack.entity.Medicine;
import org.example.medtrack.service.MedicineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @Operation(summary = "Get all medicines")
    @GetMapping
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @Operation(summary = "Get medicine by ID")
    @GetMapping("/{id}")
    public Medicine getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @Operation(summary = "Add new medicine")
    @PostMapping
    public Medicine addMedicine(@Valid @RequestBody Medicine medicine) {
        return medicineService.saveMedicine(medicine);
    }

    @Operation(summary = "Update medicine details")
    @PutMapping("/{id}")
    public Medicine updateMedicine(
            @PathVariable Long id,
            @Valid @RequestBody Medicine medicine) {

        return medicineService.updateMedicine(id, medicine);
    }

    @Operation(summary = "Delete medicine")
    @DeleteMapping("/{id}")
    public String deleteMedicine(@PathVariable Long id) {

        medicineService.deleteMedicine(id);

        return "Medicine deleted successfully";
    }
}