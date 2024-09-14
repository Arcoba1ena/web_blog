package com.bloger.web.blog.controllers;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.bloger.web.blog.services.SubscribeService;
import com.bloger.web.blog.services.RegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/subscribers")
    public String subscribers(Model model) {
        List<String> subscribers = subscribeService.getAllSubscribe();

        List<Map<String, Object>> articles = List.of(
                Map.of(
                        "title", "\"Необъятный космос\"",
                        "text", "Первым человеком, который заглянул в космос с помощью телескопа, был Галилей, почти 400 лет назад.",
                        "author", "FactNews",
                        "tags", "#История #Космос #Наука",
                        "comment", "Круто! Напиши еще статей!"
                ),
                Map.of(
                        "title", "\"Факты о природе\"",
                        "text", "Моника Гальяно, доцент кафедры биологии Университета Западной Австралии, провела эксперимент, исследующий поведение мимозы...",
                        "author", "Anonymous",
                        "tags", "#Природа #Наука #Обучение",
                        "comment", "Спасибо за статью! Очень познавательно"
                ),
                Map.of(
                        "title", "\"Интересное о животных\"",
                        "text", "Муравьи никогда не спят. Вместо этого они «отдыхают» по восемь минут два раза в день...",
                        "author", "Teacher",
                        "tags", "#Животные #Факты #Интересное",
                        "comment", "Круто! Напиши еще статей!"
                ),
                Map.of(
                        "title", "\"Интересное о кулинарии\"",
                        "text", "Куриные яйца полезны и питательны, любые. Но какие самые полезные? В этой статье разбираемся с преимуществами и недостатками сырых, вареных и жареных яиц...",
                        "author", "FoodTv",
                        "tags", "#Кулинария #Рецепты",
                        "comment", "Интересно что же будет дальше?"
                ),
                Map.of(
                        "title", "\"Машины и их особенности\"",
                        "text", "Эксперт рассказал об особенностях пневмоподвески некоторых авто....",
                        "author", "CarsShop",
                        "tags", "#Машины #Колеса",
                        "comment", "Так и не понял, нормально ли это!?"
                )
        );

        model.addAttribute("subscribers", subscribers);
        model.addAttribute("articles", articles);

        if (!registrationService.getAllCredentials().isEmpty()) {
            for (var cred : registrationService.getAllCredentials()) {
                if (cred.get("isAuthentication").getAsBoolean()) {
                    return "subscribers";
                }
            }
        }
        return "auth";
    }

    @GetMapping("/subscribers/sort")
    public String sortByTag(Model model) {
        List<String> subscribers = subscribeService.getAllSubscribe();

        List<Map<String, Object>> articles = List.of(
                Map.of(
                        "title", "\"Необъятный космос\"",
                        "text", "Первым человеком, который заглянул в космос с помощью телескопа, был Галилей, почти 400 лет назад.",
                        "author", "FactNews",
                        "tags", "#История #Космос #Наука",
                        "comment", "Круто! Напиши еще статей!"
                ),
                Map.of(
                        "title", "\"Факты о природе\"",
                        "text", "Моника Гальяно, доцент кафедры биологии Университета Западной Австралии, провела эксперимент, исследующий поведение мимозы...",
                        "author", "Anonymous",
                        "tags", "#Природа #Наука #Обучение",
                        "comment", "Спасибо за статью! Очень познавательно"
                ),
                Map.of(
                        "title", "\"Интересное о животных\"",
                        "text", "Муравьи никогда не спят. Вместо этого они «отдыхают» по восемь минут два раза в день...",
                        "author", "Teacher",
                        "tags", "#Животные #Факты #Интересное",
                        "comment", "Круто! Напиши еще статей!"
                ),
                Map.of(
                        "title", "\"Интересное о кулинарии\"",
                        "text", "Куриные яйца полезны и питательны, любые. Но какие самые полезные? В этой статье разбираемся с преимуществами и недостатками сырых, вареных и жареных яиц...",
                        "author", "FoodTv",
                        "tags", "#Кулинария #Рецепты",
                        "comment", "Интересно что же будет дальше?"
                ),
                Map.of(
                        "title", "\"Машины и их особенности\"",
                        "text", "Эксперт рассказал об особенностях пневмоподвески некоторых авто....",
                        "author", "CarsShop",
                        "tags", "#Машины #Колеса",
                        "comment", "Так и не понял, нормально ли это!?"
                )
        );

        List<Map<String, Object>> sortedArticles = articles.stream()
                .sorted((a1, a2) -> {
                    String tags1 = (String) a1.get("tags");
                    String tags2 = (String) a2.get("tags");
                    return tags1.compareTo(tags2);
                })
                .collect(Collectors.toList());

        model.addAttribute("subscribers", subscribers);
        model.addAttribute("articles", sortedArticles);

        return "subscribers";
    }

    @PostMapping("/subscribe")
    public String subscribe(
            @RequestParam("userLogin") String userLogin,
            Model model) {
        subscribeService.addSubscribeObj(userLogin);
        return "blogs";
    }
}
