package lucadipietro.U5_W2_D4.services;

import lucadipietro.U5_W2_D4.entities.Autore;
import lucadipietro.U5_W2_D4.entities.BlogPost;
import lucadipietro.U5_W2_D4.exceptions.BadRequestException;
import lucadipietro.U5_W2_D4.exceptions.NotFoundException;
import lucadipietro.U5_W2_D4.payloads.NewBlogPostDTO;
import lucadipietro.U5_W2_D4.repositories.BlogPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class BlogPostsService {
    @Autowired
    private BlogPostsRepository blogPostsRepository;
    @Autowired
    private AutoriService autoriService;

    Random rnd = new Random();

    public Page<BlogPost> getBlogPosts(int pageNumber, int pageSize, String sortBy){
        if(pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        return blogPostsRepository.findAll(pageable);
    }

    public BlogPost save(NewBlogPostDTO body){
        this.blogPostsRepository.findByTitolo(body.titolo()).ifPresent(
                blogPost -> {
                    throw new BadRequestException("Esiste già un post con questo titolo " + body.titolo());
                }
        );
        Autore autore = autoriService.findById(body.autoreId());
        BlogPost newPost = new BlogPost(body.categoria(),body.titolo(),body.contenuto(),body.tempoDiLettura(),autore);
        newPost.setCover("https://picsum.photos/" + rnd.nextInt(1,300) + "/" + rnd.nextInt(1,300));
        return this.blogPostsRepository.save(newPost);
    }

    public BlogPost findById(UUID blogPostId) {
        return this.blogPostsRepository.findById(blogPostId).orElseThrow(() -> new NotFoundException(blogPostId));
    }

    public BlogPost findByIdAndUpdate(UUID blogPostId, BlogPost updateBlogPost){
        BlogPost found = this.findById(blogPostId);
        found.setCategoria(updateBlogPost.getCategoria());
        found.setTitolo(updateBlogPost.getTitolo());
        found.setCover("https://picsum.photos/" + rnd.nextInt(1,300) + "/" + rnd.nextInt(1,300));
        found.setContenuto(updateBlogPost.getContenuto());
        found.setTempoDiLettura(updateBlogPost.getTempoDiLettura());
        found.setAutore(updateBlogPost.getAutore());
        return this.blogPostsRepository.save(found);
    }

    public void findByIdAndDelete(UUID blogPostId) {
        BlogPost found = this.findById(blogPostId);
        this.blogPostsRepository.delete(found);
    }
}
