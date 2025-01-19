package jbar.service_core.Site.Controller;

import jbar.service_core.Site.Model.SiteDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/site")
public class SiteController {

    private final SiteService siteService;

    @Autowired
    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllSites() {
        return siteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getSiteById(@PathVariable Integer id) {
        return siteService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createSite(@RequestBody SiteDTO siteDTO) {
        return siteService.create(siteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateSite(@PathVariable Integer id, @RequestBody SiteDTO siteDTO) {
        return siteService.update(id, siteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteSite(@PathVariable Integer id) {
        return siteService.delete(id);
    }
}
