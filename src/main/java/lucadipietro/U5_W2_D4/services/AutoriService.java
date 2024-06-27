package lucadipietro.U5_W2_D4.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lucadipietro.U5_W2_D4.entities.Autore;
import lucadipietro.U5_W2_D4.exceptions.BadRequestException;
import lucadipietro.U5_W2_D4.exceptions.NotFoundException;
import lucadipietro.U5_W2_D4.repositories.AutoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Service
public class AutoriService {
    @Autowired
    private AutoriRepository autoriRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;

    Random rnd = new Random();

    public Page<Autore> getBlogPosts(int pageNumber, int pageSize, String sortBy){
        if(pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        return autoriRepository.findAll(pageable);
    }

    public Autore save(Autore newAutore){
        this.autoriRepository.findByEmail(newAutore.getEmail()).ifPresent(
                autore -> {
                    throw new BadRequestException("L'email " + newAutore.getEmail() + " è già in uso!");
                }
        );
        newAutore.setAvatar("https://ui-avatars.com/api/?name=" + newAutore.getNome() + "+" + newAutore.getCognome());
        return autoriRepository.save(newAutore);
    }

    public Autore findById(UUID autoreId) {
        return this.autoriRepository.findById(autoreId).orElseThrow(() -> new NotFoundException(autoreId));
    }

    public Autore findByIdAndUpdate(UUID autoreId,Autore updateAutore){
        Autore found = this.findById(autoreId);
        found.setNome(updateAutore.getNome());
        found.setCognome(updateAutore.getCognome());
        found.setEmail(updateAutore.getEmail());
        found.setDataDiNascita(updateAutore.getDataDiNascita());
        found.setAvatar("https://ui-avatars.com/api/?name=" + updateAutore.getNome() + "+" + updateAutore.getCognome());
        return autoriRepository.save(found);
    }

    public void findByIdAndDelete(UUID autoreId) {
        Autore found = this.findById(autoreId);
        this.autoriRepository.delete(found);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }
}
