package com.suncontrol.domain.control;

import com.suncontrol.domain.service.plantSelectionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class plantSelectController {

    private final plantSelectionService plantSelectionService;

    @PostMapping("/plants/select")
    public String selectPlant(@RequestParam("plantId") Long plantId,
                              @RequestParam(value = "redirectUrl", defaultValue = "/dashboard") String redirectUrl,
                              HttpSession session) {

        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:" + redirectUrl;
        }

        Long resolvedPlantId = plantSelectionService.resolveSelectedPlantId(memberId, plantId);

        if (resolvedPlantId != null) {
            session.setAttribute("selectedPlantId", resolvedPlantId);
        }

        return "redirect:" + redirectUrl;
    }
}