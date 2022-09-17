package com.project.prkt.controller;

import com.project.prkt.model.*;
import com.project.prkt.service.*;
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
@RequestMapping("/admin/info-booking")
public class BookingController {

    private final BookingService bookingService;
    private final ClientService clientService;
    private final RiderService riderService;
    private final AssignedEquipmentService assignedEquipmentService;
    private final SnowboardService snowboardService;
    private final SnowboardBootsService snowboardBootsService;
    private final SkiService skiService;
    private final SkiBootsService skiBootsService;
    private final JacketService jacketService;
    private final KneeProtectionService kneeProtectionService;
    private final ProtectiveShortsService protectiveShortsService;
    private final PantsService pantsService;
    private final HelmetService helmetService;
    private final GlovesService glovesService;

    @Autowired
    public BookingController(BookingService bookingService, ClientService clientService, RiderService riderService,
                             AssignedEquipmentService assignedEquipmentService, SnowboardService snowboardService,
                             SnowboardBootsService snowboardBootsService, SkiService skiService,
                             SkiBootsService skiBootsService, JacketService jacketService,
                             KneeProtectionService kneeProtectionService, ProtectiveShortsService protectiveShortsService, PantsService pantsService, HelmetService helmetService, GlovesService glovesService) {
        this.bookingService = bookingService;
        this.clientService = clientService;
        this.riderService = riderService;
        this.assignedEquipmentService = assignedEquipmentService;
        this.snowboardService = snowboardService;
        this.snowboardBootsService = snowboardBootsService;
        this.skiService = skiService;
        this.skiBootsService = skiBootsService;
        this.jacketService = jacketService;
        this.kneeProtectionService = kneeProtectionService;
        this.protectiveShortsService = protectiveShortsService;
        this.pantsService = pantsService;
        this.helmetService = helmetService;
        this.glovesService = glovesService;
    }

    // ----- show all bookings -----
    @GetMapping()
    public String showAllBookings(Model model) {
        model.addAttribute("allBookings", bookingService.showAllBookings());
        return "booking/show_all";
    }

    // ----- add new booking -----
    @GetMapping("/add-new")
    public String createNewBooking(Model model) {
        model.addAttribute("newClientAndNewBookingInfo", new BookingCreationRequest());
        return "booking/add_new";
    }

    @PostMapping()
    public String addNewClientAndBookingToDB(@ModelAttribute("newClientAndNewBookingInfo")
                                                 @Valid BookingCreationRequest newClientAndNewBookingInfo,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "booking/add_new";
        }
        Client newClient = new Client(newClientAndNewBookingInfo.getSurname(),
                newClientAndNewBookingInfo.getPhone1(), newClientAndNewBookingInfo.getPhone2()
        );
        clientService.addNewClientToDB(newClient);
        Booking newBooking = new Booking();
        bookingService.addNewBookingInfoToNewBooking(newBooking, newClient,
                newClientAndNewBookingInfo.getDateOfArrival(), newClientAndNewBookingInfo.getDateOfReturn());
        bookingService.addNewBookingToDB(newBooking);
        return "redirect:/admin/info-riders/add-new?id=" + newBooking.getId();
    }

    // ----- edit booking info -----
    @GetMapping("/edit/{id}")
    public String showOneBooking(@PathVariable("id") Long bookingToBeUpdatedId, Model model) {
        Booking bookingToBeUpdated = bookingService.showOneBookingById(bookingToBeUpdatedId);
        Client clientToBeUpdated = bookingToBeUpdated.getClient();

        BookingCreationRequest clientAndBookingInfoToBeUpdated = new BookingCreationRequest(
                clientToBeUpdated.getId(), bookingToBeUpdatedId, clientToBeUpdated.getSurname(),
                clientToBeUpdated.getPhone1(), clientToBeUpdated.getPhone2(), bookingToBeUpdated.getDateOfArrival(),
                bookingToBeUpdated.getDateOfReturn(), bookingToBeUpdated.getListOfRiders(), 0L);

        model.addAttribute("clientAndBookingInfoToBeUpdated", clientAndBookingInfoToBeUpdated);
        model.addAttribute("allRiders", riderService.showAllRiders());

        model.addAttribute("allSnowboardsAvailable",
                snowboardService.showAllAvailableSnowboards(bookingToBeUpdated.getDateOfArrival(),
                bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        model.addAttribute("allSnowboardBootsAvailable",
                snowboardBootsService.showAllAvailableSnowboardBoots(bookingToBeUpdated.getDateOfArrival(),
                        bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        model.addAttribute("allSkiAvailable",
                skiService.showAllAvailableSki(bookingToBeUpdated.getDateOfArrival(),
                        bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        model.addAttribute("allSkiBootsAvailable",
                skiBootsService.showAllAvailableSkiBoots(bookingToBeUpdated.getDateOfArrival(),
                        bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        model.addAttribute("allJacketsAvailable",
                jacketService.showAllAvailableJackets(bookingToBeUpdated.getDateOfArrival(),
                        bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        model.addAttribute("allKneeProtectionAvailable",
                kneeProtectionService.showAllAvailableKneeProtection(bookingToBeUpdated.getDateOfArrival(),
                        bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        model.addAttribute("allProtectiveShortsAvailable",
                protectiveShortsService.showAllAvailableProtectiveShorts(bookingToBeUpdated.getDateOfArrival(),
                        bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        model.addAttribute("allGlovesAvailable",
                glovesService.showAllAvailableGloves(bookingToBeUpdated.getDateOfArrival(),
                        bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        model.addAttribute("allPantsAvailable",
                pantsService.showAllAvailablePants(bookingToBeUpdated.getDateOfArrival(),
                        bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        model.addAttribute("allHelmetsAvailable",
                helmetService.allAvailableHelmets(bookingToBeUpdated.getDateOfArrival(),
                        bookingToBeUpdated.getDateOfReturn(), bookingService.showAllBookings()));
        return "booking/edit";
    }

    //// ----- edit booking info / edit booking and client info -----
    @PatchMapping("/edit/{id}")
    public String updateBookingById(@PathVariable("id") Long bookingToBeUpdatedId,
                                    @ModelAttribute("clientAndBookingInfoToBeUpdated") @Valid BookingCreationRequest updatedClientAndBookingInfo,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", bookingToBeUpdatedId);
            updatedClientAndBookingInfo.setListOfRiders(bookingService.showOneBookingById(bookingToBeUpdatedId).getListOfRiders());
            model.addAttribute("clientAndBookingInfoToBeUpdated", updatedClientAndBookingInfo);
            model.addAttribute("allRiders", riderService.showAllRiders());
            return "booking/edit";
        }
        Booking bookingToBeUpdated = bookingService.showOneBookingById(bookingToBeUpdatedId);

        clientService.updateClientById(bookingToBeUpdated.getClient().getId(), new Client(updatedClientAndBookingInfo.getSurname(),
                updatedClientAndBookingInfo.getPhone1(), updatedClientAndBookingInfo.getPhone2()));

        bookingService.updateBookingById(bookingToBeUpdatedId, new Booking(bookingToBeUpdated.getClient(),
                updatedClientAndBookingInfo.getDateOfArrival(), updatedClientAndBookingInfo.getDateOfReturn()));
        return "redirect:/admin/info-booking/edit/{id}";
    }

    //// ----- edit booking info / assign equipment to riders -----
    @PatchMapping("/edit/assign-equipment")
    public String assignEquipmentToOneRider(@RequestParam("bid") Long bookingToBeUpdatedId,
                                            @RequestParam("rid") Long riderToBeUpdatedId,
                                            @ModelAttribute("oneRider.assignedEquipment") AssignedEquipment assignedEquipment) {
        Rider updatedRider = riderService.showOneRiderById(riderToBeUpdatedId);
        AssignedEquipment newAssignedEquipment = new AssignedEquipment();

        if (assignedEquipment.getSnowboard().getId() != null) {
            Snowboard newSnowboard = snowboardService.showOneSnowboardById(assignedEquipment.getSnowboard().getId());
            newAssignedEquipment.setSnowboard(newSnowboard);
        }
        if (assignedEquipment.getSnowboardBoots().getId() != null) {
            SnowboardBoots newSnowboardBoots = snowboardBootsService.showOneSnowboardBootsById(assignedEquipment.getSnowboardBoots().getId());
            newAssignedEquipment.setSnowboardBoots(newSnowboardBoots);
        }
        if (assignedEquipment.getSki().getId() != null) {
            Ski newSki = skiService.showOneSkiById(assignedEquipment.getSki().getId());
            newAssignedEquipment.setSki(newSki);
        }
        if (assignedEquipment.getSkiBoots().getId() != null) {
            SkiBoots newSkiBoots = skiBootsService.showOneSkiBootsById(assignedEquipment.getSkiBoots().getId());
            newAssignedEquipment.setSkiBoots(newSkiBoots);
        }
        if (assignedEquipment.getJacket().getId() != null) {
            Jacket newJacket = jacketService.showOneJacketById(assignedEquipment.getJacket().getId());
            newAssignedEquipment.setJacket(newJacket);
        }
        if (assignedEquipment.getKneeProtection().getId() != null) {
            KneeProtection newKneeProtection = kneeProtectionService.showOneKneeProtectionById(assignedEquipment.getKneeProtection().getId());
            newAssignedEquipment.setKneeProtection(newKneeProtection);
        }
        if (assignedEquipment.getProtectiveShorts().getId() != null) {
            ProtectiveShorts newProtectiveShorts = protectiveShortsService.showOneProtectiveShortsById(assignedEquipment.getProtectiveShorts().getId());
            newAssignedEquipment.setProtectiveShorts(newProtectiveShorts);
        }
        if(assignedEquipment.getHelmet().getId() != null){
            Helmet newHelmet = helmetService.showOneHelmetById(assignedEquipment.getHelmet().getId());
            newAssignedEquipment.setHelmet(newHelmet);
        }
        if(assignedEquipment.getPants().getId() != null){
            Pants newPants = pantsService.showOneById(assignedEquipment.getPants().getId());
            newAssignedEquipment.setPants(newPants);
        }
        if(assignedEquipment.getGloves().getId() != null){
            Gloves newGloves = glovesService.showOneById(assignedEquipment.getGloves().getId());
            newAssignedEquipment.setGloves(newGloves);
        }

        assignedEquipmentService.addNewAssignedEquipmentToDB(newAssignedEquipment);
        riderService.assignEquipmentToRider(updatedRider, newAssignedEquipment);
        return "redirect:/admin/info-booking/edit/" + bookingToBeUpdatedId;
    }

    //// ----- edit booking info / add existing rider to booking -----
    @PatchMapping("/edit/add-existing-rider/{bid}")
    public String addExistingRiderToBooking(@PathVariable("bid") Long bookingToBeUpdatedId,
                                            @ModelAttribute("clientAndBookingInfoToBeUpdated") BookingCreationRequest clientAndBookingInfoToBeUpdated) {
        Rider existingRiderToBeAdded = riderService.showOneRiderById(clientAndBookingInfoToBeUpdated.getExistingRiderToBeAddedId());
        Booking bookingToBeUpdated = bookingService.showOneBookingById(bookingToBeUpdatedId);
        bookingService.addExistingRiderToBooking(bookingToBeUpdated, existingRiderToBeAdded);
        return "redirect:/admin/info-booking/edit/" + bookingToBeUpdatedId;
    }

    //// ----- edit booking info / remove rider from booking -----
    @GetMapping("/edit/remove")
    public String removeRiderFromBooking(@RequestParam("bid") Long bookingToBeUpdatedId,
                                         @RequestParam("rid") Long riderToBeRemovedId, Model model) {
        Booking bookingToBeUpdated = bookingService.showOneBookingById(bookingToBeUpdatedId);
        Rider riderToBoUpdated = riderService.showOneRiderById(riderToBeRemovedId);

        riderService.removeAssignedEquipment(riderToBoUpdated);

        bookingService.removeRiderFromBooking(bookingToBeUpdated, riderToBoUpdated);
        model.addAttribute("allRiders", riderService.showAllRiders());
        return "redirect:/admin/info-booking/edit/" + bookingToBeUpdatedId;
    }

    // ----- delete booking -----
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable("id") Long id) {
        for (Rider rider : bookingService.showOneBookingById(id).getListOfRiders()) {
            riderService.removeAssignedEquipment(rider);
        }
        bookingService.deleteBookingById(id);
        return "redirect:/admin/info-booking";
    }

    // ----- mark booking completed -----
    @GetMapping("/change-booking-completed/{id}")
    public String changeBookingCompleted(@PathVariable("id") Long bookingId) {
        bookingService.markBookingCompleted(bookingId);
        return "redirect:/admin/info-booking";
    }

    // ----- search -----
    @GetMapping("/search")
    public String searchBookingsByParameter(@RequestParam("parameter") String parameter, Model model) {
        model.addAttribute("bookingsByParameter", bookingService.showBookingsByParameter(parameter));
        return "booking/search";
    }

    // ----- sort -----
    @GetMapping("/sort")
    public String sortBookingsByParameter(@RequestParam("parameter") String parameter,
                                          @RequestParam("sortDirection") String sortDirection,
                                          Model model) {
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("allBookings", bookingService.sortAllBookingsByParameter(parameter, sortDirection));
        return "booking/show_all";
    }
}
