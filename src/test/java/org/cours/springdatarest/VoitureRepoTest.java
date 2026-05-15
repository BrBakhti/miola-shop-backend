package org.cours.springdatarest;

import static org.assertj.core.api.Assertions.assertThat;
import org.cours.springdatarest.modele.Voiture;
import org.cours.springdatarest.modele.VoitureRepo;
import org.cours.springdatarest.modele.Proprietaire;
import org.cours.springdatarest.modele.ProprietaireRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class VoitureRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    VoitureRepo voitureRepo;

    @Autowired
    ProprietaireRepo proprietaireRepo;

    @Test
    public void ajouterVoiture() {
        Proprietaire p = new Proprietaire("Test", "User");
        entityManager.persistAndFlush(p);
        Voiture voiture = new Voiture(
                "MiolaCar", "Uber", "Blanche", "M-2020", 2021, 180000, p);
        entityManager.persistAndFlush(voiture);
        assertThat(voiture.getId()).isNotNull();
    }

    @Test
    public void supprimerVoiture() {
        Proprietaire p = new Proprietaire("Test", "User");
        entityManager.persistAndFlush(p);
        entityManager.persistAndFlush(
                new Voiture("MiolaCar", "Uber", "Blanche", "M-2020", 2021, 180000, p));
        entityManager.persistAndFlush(
                new Voiture("MiniCooper", "Uber", "Rouge", "C-2020", 2021, 180000, p));
        voitureRepo.deleteAll();
        assertThat(voitureRepo.findAll()).isEmpty();
    }
}