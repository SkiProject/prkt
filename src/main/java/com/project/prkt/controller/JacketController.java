package com.project.prkt.controller;

import com.project.prkt.model.Jacket;
import com.project.prkt.service.JacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Nikolai Khriapov
 */

@Controller
@RequestMapping("/admin/info-equipment/jacket")
public class JacketController {

    private final JacketService jacketService;

    @Autowired
    public JacketController(JacketService jacketService) {
        this.jacketService = jacketService;
    }

    // ----- show all -----
    @GetMapping()
    public String showAllJackets(Model model) {
        model.addAttribute("allJackets", jacketService.showAllJackets());
        return "jacket/show_all";
    }

    // ----- add new -----
    @GetMapping("/add-new")
    public String createNewJacket(Model model) {
        model.addAttribute("newJacket", new Jacket());
        return "jacket/add_new";
    }

    @PostMapping()
    public String addNewJacketToDB(@ModelAttribute("newJacket") @Valid Jacket jacket,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "jacket/add_new";
        }
        jacketService.addNewJacketToDB(jacket);
        return "redirect:/admin/info-equipment/jacket";
    }

    // ----- edit -----
    @GetMapping("/edit/{id}")
    public String showOneJacket(@PathVariable("id") Long id, Model model) {
        model.addAttribute("jacketToUpdate", jacketService.showOneJacketById(id));
        return "jacket/edit";
    }

    @PatchMapping("/edit/{id}")
    public String updateJacket(@PathVariable("id") Long id,
                               @ModelAttribute("jacketToUpdate") @Valid Jacket updatedJacket,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "jacket/edit";
        }
        jacketService.updateJacketById(id, updatedJacket);
        return "redirect:/admin/info-equipment/jacket";
    }

    // ----- delete -----
    @DeleteMapping("/{id}")
    public String deleteJacket(@PathVariable("id") Long id) {
        jacketService.deleteJacketById(id);
        return "redirect:/admin/info-equipment/jacket";
    }

    // ----- search -----
    @GetMapping("/search")
    public String showJacketsBySearch(@RequestParam("search") String search, Model model) {
        model.addAttribute("jacketsBySearch", jacketService.showJacketsBySearch(search));
        return "jacket/search";
    }

    // ----- sort -----
    @GetMapping("/sort")
    public String sortAllJacketsByParameter(@RequestParam("parameter") String parameter,
                                            @RequestParam("sortDirection") String sortDirection,
                                            Model model) {
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("allJackets", jacketService.sortAllJacketsByParameter(parameter, sortDirection));
        return "jacket/show_all";
    }
}
