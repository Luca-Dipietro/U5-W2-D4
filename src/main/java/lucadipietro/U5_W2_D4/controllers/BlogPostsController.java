package lucadipietro.U5_W2_D4.controllers;

import lucadipietro.U5_W2_D4.entities.BlogPost;
import lucadipietro.U5_W2_D4.exceptions.BadRequestException;
import lucadipietro.U5_W2_D4.payloads.NewBlogPostDTO;
import lucadipietro.U5_W2_D4.payloads.NewBlogPostResponseDTO;
import lucadipietro.U5_W2_D4.services.BlogPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/blogPosts")
public class BlogPostsController {
    @Autowired
    private BlogPostsService blogPostsService;

    @GetMapping
    public Page<BlogPost> getAllBlogPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy){
        return this.blogPostsService.getBlogPosts(page,size,sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewBlogPostResponseDTO saveBlogPost(@RequestBody @Validated NewBlogPostDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewBlogPostResponseDTO(this.blogPostsService.save(body).getId());
    }

    @GetMapping("/{blogPostId}")
    public BlogPost findById(@PathVariable UUID blogPostId){
        return this.blogPostsService.findById(blogPostId);
    }

    @PutMapping("/{blogPostId}")
    public BlogPost findByIdAndUpdate(@PathVariable UUID blogPostId,@RequestBody BlogPost body){
        return this.blogPostsService.findByIdAndUpdate(blogPostId,body);
    }

    @DeleteMapping("/{blogPostId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID blogPostId){
        this.blogPostsService.findByIdAndDelete(blogPostId);
    }
}
