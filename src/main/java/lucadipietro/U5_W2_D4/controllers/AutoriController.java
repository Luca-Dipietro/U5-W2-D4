package lucadipietro.U5_W2_D4.controllers;

import lucadipietro.U5_W2_D4.entities.Autore;
import lucadipietro.U5_W2_D4.services.AutoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/autori")
public class AutoriController {
    @Autowired
    private AutoriService autoriService;

    @GetMapping
    public Page<Autore> getAllBlogPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy){
        return this.autoriService.getBlogPosts(page,size,sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Autore saveAutore(@RequestBody Autore body){
        return this.autoriService.save(body);
    }

    @GetMapping("/{autoreId}")
    private Autore findById(@PathVariable UUID autoreId){
        return this.autoriService.findById(autoreId);
    }

    @PutMapping("/{autoreId}")
    private Autore findByIdAndUpdate(@PathVariable UUID autoreId,@RequestBody Autore body){
        return this.autoriService.findByIdAndUpdate(autoreId,body);
    }

    @DeleteMapping("/{autoreId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findByIdAndDelete(@PathVariable UUID autoreId){
        this.autoriService.findByIdAndDelete(autoreId);
    }

    @PostMapping("/{autoreId}/avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile image) throws IOException {
        return this.autoriService.uploadImage(image);
    }
}
