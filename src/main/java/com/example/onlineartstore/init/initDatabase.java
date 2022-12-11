package com.example.onlineartstore.init;

import com.example.onlineartstore.entity.*;
import com.example.onlineartstore.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Slf4j
@Component
@RequiredArgsConstructor
public class initDatabase implements CommandLineRunner {

    private final PaintingRepository paintingRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final AuctionRepository auctionRepository;

    @Override
    public void run(String... args) throws Exception {

        Author author1 = new Author("artLana", "Svetlana", "Vovk", LocalDate.of(1957, Month.OCTOBER,18), "Many awards", "Food_C245-128.png");
        authorRepository.save(author1);

        User Star = new User("Star","$2a$10$OEKBPlQJhYFgSQ7PqKXfKulkZnj/FnozqYP8E7T2ro.3YPi8fxlGa", true); // 12345
        User NiceCat = new User("NiceCat", "$2a$10$Y9HP/PX0shaO51dWyHunM.MxUkGvNLyLn0zn/uIYLu7eXNnZqCSZ6", true); //2222
        userRepository.save(Star);
        userRepository.save(NiceCat);

        Comment comment1 = new Comment("This is a beautiful picture");

        Painting painting1 = new Painting("Lime", LocalDate.of(2021,Month.APRIL,15), "Food_C245-128.png", "60x100cm", "canvas", "The picture is made in bright colors, yellow-green color will provide you with a good mood", BigDecimal.valueOf(5000));
        author1.addComment(comment1);
        Painting painting2 = new Painting("Equilibrium", LocalDate.of(2021,Month.JANUARY,20), "Food_C245-128.png", "100x100cm", "canvas", "This picture is like a bright comet ", BigDecimal.valueOf(8000));
        Painting painting3 = new Painting("Flecks of sunlight", LocalDate.of(2022,Month.JULY,25), "Food_C245-128.png", "40x50cm", "canvas", "This picture is beautiful ", BigDecimal.valueOf(3000));
        author1.addPainting(painting1,painting2,painting3);

        Category category1 = new Category("acrylic");
        Category category2 = new Category("watercolor");
        Category category3 = new Category("oil");
        Category category4 = new Category("gouache");

        category1.addPainting(painting1, painting2, painting3);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);

        Auction auction1 = new Auction("New Year Auction!", LocalDateTime.of(2023,Month.JANUARY,1,14,15), BigDecimal.valueOf(3000), BigDecimal.valueOf(3000), LocalDateTime.of(2023,Month.JANUARY,2,14,15),"not");
        auctionRepository.save(auction1);



        
    }
}
