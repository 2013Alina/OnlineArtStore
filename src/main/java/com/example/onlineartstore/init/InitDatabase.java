package com.example.onlineartstore.init;

import com.example.onlineartstore.entity.*;
import com.example.onlineartstore.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDatabase implements CommandLineRunner {

    private final PaintingRepository paintingRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final AuctionRepository auctionRepository;
    private final UserDetailRepository userDetailRepository;

    @Override
    public void run(String... args) throws Exception {

        Author author1 = new Author("artLana", "Svetlana", "Vovk", LocalDate.of(1957, Month.OCTOBER,18), "Many awards", "Food_C245-128.png");
        authorRepository.save(author1);

        User star = new User("Star","$2a$10$OEKBPlQJhYFgSQ7PqKXfKulkZnj/FnozqYP8E7T2ro.3YPi8fxlGa", true); // 12345
        User niceCat = new User("NiceCat", "$2a$10$Y9HP/PX0shaO51dWyHunM.MxUkGvNLyLn0zn/uIYLu7eXNnZqCSZ6", true); //2222
        userRepository.save(star);
        userRepository.save(niceCat);

        Category category1 = new Category("acrylic");
        Category category2 = new Category("watercolor");
        Category category3 = new Category("oil");
        Category category4 = new Category("gouache");
        Category category5 = new Category("coal");

        Comment comment1 = new Comment("This is a beautiful picture");

        Auction auction1 = new Auction("New Year Auction!", LocalDateTime.of(2023,Month.JANUARY,1,14,15),LocalDateTime.of(2023,Month.JANUARY,11,14,15), BigDecimal.valueOf(3000),true);
        auction1.addAuctionParticipants(star);
        auctionRepository.save(auction1);
        Auction auction2 = new Auction("Merry Christmas Auction!", LocalDateTime.of(2023, Month.FEBRUARY, 1, 11, 10), LocalDateTime.of(2023, Month.FEBRUARY, 10,11,10), BigDecimal.valueOf(4000), true);
        auction2.addAuctionParticipants(niceCat);
        auctionRepository.save(auction2);
        Auction auction3 = new Auction("Green Spring!", LocalDateTime.of(2023,Month.MARCH,1,12,00),LocalDateTime.of(2023,Month.MARCH,3,14,00), BigDecimal.valueOf(5000),false);
        auctionRepository.save(auction3);
        Auction auction4 = new Auction("Hot Summer!", LocalDateTime.of(2023,Month.JULY,1,12,00),LocalDateTime.of(2023,Month.JULY,3,14,00), BigDecimal.valueOf(2000),false);
        auctionRepository.save(auction4);


        Painting painting1 = new Painting("Lime", LocalDate.of(2021,Month.APRIL,15), "Food_C245-128.png", "60x100cm", "canvas", "The picture is made in bright colors, yellow-green color will provide you with a good mood", BigDecimal.valueOf(5000),false);
        author1.addComment(comment1);
        Painting painting2 = new Painting("Equilibrium", LocalDate.of(2021,Month.JANUARY,20), "Food_C245-128.png", "100x100cm", "canvas", "This picture is like a bright comet ", BigDecimal.valueOf(8000), false);
        Painting painting3 = new Painting("Flecks of sunlight", LocalDate.of(2022,Month.JULY,25), "Food_C245-128.png", "40x50cm", "canvas", "This picture is beautiful ", BigDecimal.valueOf(3000), false);
        author1.addPainting(painting1,painting2,painting3);

        category1.addPainting(painting1, painting2, painting3);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);
        categoryRepository.save(category5);


        auction1.addPainting(painting1);
        paintingRepository.save(painting1);
        auction2.addPainting(painting2);
        auction2.addPainting(painting3);
        paintingRepository.save(painting2);
        paintingRepository.save(painting3);
        auction1.addAuctionParticipants(star);
        auction2.addAuctionParticipants(niceCat);
        auctionRepository.save(auction1);
        auctionRepository.save(auction2);


        UserDetail userDetail1 = new UserDetail("Alina", "Muntian", LocalDate.of(1986, Month.MARCH,5),"muntian@gmail.com", "095-797-51-67");
        UserDetail userDetail2 = new UserDetail("Alina", "Muntian", LocalDate.of(1986, Month.MARCH,5),"muntian@gmail.com", "095-797-51-67");
        UserDetail userDetail3 = new UserDetail("Anna", "Petrova", LocalDate.of(2003, Month.DECEMBER,11),"petrova@gmail.com", "099-941-78-55");
        UserDetail userDetail4 = new UserDetail("Victoria", "Omelchenko", LocalDate.of(2001, Month.APRIL,2),"omelchenko@gmail.com", "067-831-81-27");
        userDetailRepository.save(userDetail1);
        userDetailRepository.save(userDetail2);
        userDetailRepository.save(userDetail3);
        userDetailRepository.save(userDetail4);

    }
}