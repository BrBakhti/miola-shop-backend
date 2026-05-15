package org.cours.springdatarest.web;

import org.cours.springdatarest.modele.Voiture;
import org.cours.springdatarest.modele.VoitureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class VoitureController {

    @Autowired
    private VoitureRepo voitureRepo;

    @GetMapping("/voitures")
    public Iterable<Voiture> getVoitures() {
        return voitureRepo.findAll();
    }

    @GetMapping("/voitures/{id}")
    public Optional<Voiture> getVoiture(@PathVariable Long id) {
        return voitureRepo.findById(id);
    }

    @PostMapping("/voitures")
    public Voiture addVoiture(@RequestBody Voiture voiture) {
        return voitureRepo.save(voiture);
    }

    @PutMapping("/voitures/{id}")
    public Voiture updateVoiture(@PathVariable Long id, @RequestBody Voiture voiture) {
        voiture.setId(id);
        return voitureRepo.save(voiture);
    }

    @DeleteMapping("/voitures/{id}")
    public Voiture deleteVoiture(@PathVariable Long id) {
        Optional<Voiture> voiture = voitureRepo.findById(id);
        if (voiture.isPresent()) {
            voitureRepo.deleteById(id);
            return voiture.get();
        }
        return null;
    }
}