package org.cours.springdatarest;

import org.cours.springdatarest.modele.Proprietaire;
import org.cours.springdatarest.modele.ProprietaireRepo;
import org.cours.springdatarest.modele.Voiture;
import org.cours.springdatarest.modele.VoitureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MavenApplication {

    @Autowired
    private VoitureRepo voitureRepo;

    @Autowired
    private ProprietaireRepo proprietaireRepo;

    public static void main(String[] args) {
        SpringApplication.run(MavenApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {

            Proprietaire p1 = new Proprietaire("Ali", "Hassan");
            Proprietaire p2 = new Proprietaire("Najat", "Bani");
            proprietaireRepo.save(p1);
            proprietaireRepo.save(p2);

            voitureRepo.save(new Voiture(
                    "Toyota", "Corolla", "Grise", "A-1-9090", 2018, 95000, p1));
            voitureRepo.save(new Voiture(
                    "Ford", "Fiesta", "Rouge", "A-2-8090", 2015, 90000, p1));
            voitureRepo.save(new Voiture(
                    "Honda", "CRV", "Bleu", "A-3-7090", 2016, 140000, p2));
        };
    }
}