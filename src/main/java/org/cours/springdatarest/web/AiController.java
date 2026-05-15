package org.cours.springdatarest.web;

import org.cours.springdatarest.modele.Voiture;
import org.cours.springdatarest.modele.VoitureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private VoitureRepo voitureRepo;

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        String msg = message.toLowerCase();

        if (msg.contains("bonjour") || msg.contains("salut") || msg.contains("hello")) {
            return "Bonjour ! Je suis votre conseiller automobile virtuel. " +
                    "Je peux vous aider a choisir une voiture, " +
                    "vous donner des informations sur notre stock, " +
                    "ou repondre a vos questions automobiles. Comment puis-je vous aider ?";
        }
        if (msg.contains("voiture") || msg.contains("stock") || msg.contains("liste")) {
            List<Voiture> voitures = StreamSupport
                    .stream(voitureRepo.findAll().spliterator(), false)
                    .collect(Collectors.toList());
            StringBuilder sb = new StringBuilder("Voici notre stock actuel :\n\n");
            for (Voiture v : voitures) {
                sb.append("- ").append(v.getMarque()).append(" ")
                        .append(v.getModele()).append(" : ")
                        .append(v.getCouleur()).append(", ")
                        .append(v.getAnnee()).append(", ")
                        .append(v.getPrix()).append(" DH\n");
            }
            return sb.toString();
        }
        if (msg.contains("prix") || msg.contains("budget") || msg.contains("moins cher")) {
            List<Voiture> voitures = StreamSupport
                    .stream(voitureRepo.findAll().spliterator(), false)
                    .sorted((a, b) -> Integer.compare(a.getPrix(), b.getPrix()))
                    .collect(Collectors.toList());
            if (!voitures.isEmpty()) {
                Voiture moins = voitures.get(0);
                return "La voiture la moins chere de notre stock est la " +
                        moins.getMarque() + " " + moins.getModele() +
                        " a " + moins.getPrix() + " DH, couleur " +
                        moins.getCouleur() + ", annee " + moins.getAnnee() + ".";
            }
        }
        if (msg.contains("merci")) {
            return "De rien ! N hesitez pas si vous avez d autres questions. Bonne journee !";
        }
        return "Je suis votre assistant automobile. Vous pouvez me demander : " +
                "la liste des voitures disponibles, les prix, " +
                "des recommandations selon votre budget. " +
                "Que souhaitez-vous savoir ?";
    }

    @PostMapping("/recommander")
    public String recommanderVoiture(@RequestBody String criteres) {
        List<Voiture> voitures = StreamSupport
                .stream(voitureRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());

        String c = criteres.toLowerCase();

        // Filtrer par couleur
        List<Voiture> filtrees = voitures.stream()
                .filter(v -> {
                    if (c.contains("rouge") && v.getCouleur().equalsIgnoreCase("Rouge")) return true;
                    if (c.contains("bleu") && v.getCouleur().equalsIgnoreCase("Bleu")) return true;
                    if (c.contains("blanc") && v.getCouleur().equalsIgnoreCase("Blanc")) return true;
                    if (c.contains("gris") && v.getCouleur().equalsIgnoreCase("Grise")) return true;
                    return false;
                })
                .collect(Collectors.toList());

        // Filtrer par budget
        if (c.contains("moins de 100000") || c.contains("budget")) {
            filtrees = voitures.stream()
                    .filter(v -> v.getPrix() < 100000)
                    .collect(Collectors.toList());
        }

        if (filtrees.isEmpty()) {
            filtrees = voitures;
        }

        if (filtrees.isEmpty()) {
            return "Aucune voiture disponible dans notre stock pour le moment.";
        }

        Voiture recommandee = filtrees.get(0);
        return "Je vous recommande la " +
                recommandee.getMarque() + " " + recommandee.getModele() +
                " (couleur " + recommandee.getCouleur() +
                ", annee " + recommandee.getAnnee() +
                ", prix " + recommandee.getPrix() + " DH). " +
                "C est une excellente option selon vos criteres. " +
                "Souhaitez-vous plus d informations sur ce vehicule ?";
    }

    @GetMapping("/decrire/{id}")
    public String decrireVoiture(@PathVariable Long id) {
        return voitureRepo.findById(id).map(v ->
                "La " + v.getMarque() + " " + v.getModele() +
                        " est un vehicule de qualite, disponible en couleur " +
                        v.getCouleur() + ". Avec son annee de fabrication " +
                        v.getAnnee() + " et son prix competitif de " +
                        v.getPrix() + " DH, c est une opportunite a ne pas manquer !"
        ).orElse("Voiture non trouvee.");
    }
}