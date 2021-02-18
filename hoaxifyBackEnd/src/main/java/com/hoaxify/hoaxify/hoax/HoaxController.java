package com.hoaxify.hoaxify.hoax;

import javax.validation.Valid;

import com.hoaxify.hoaxify.hoax.vm.HoaxVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.hoaxify.hoaxify.shared.CurrentUser;
import com.hoaxify.hoaxify.user.User;

@RestController
@RequestMapping("/api/1.0")
public class HoaxController {

    @Autowired
    HoaxService hoaxService;

    @PostMapping("/hoaxes")
    HoaxVM createHoax(@Valid @RequestBody Hoax hoax, @CurrentUser User user) {
        return new HoaxVM(hoaxService.save(user, hoax));
    }

    @GetMapping("/hoaxes")
    Page<HoaxVM> getAllHoaxes(Pageable pageable) {
        return hoaxService.getAllHoaxes(pageable).map(HoaxVM::new);
    }
}