package com.yuzarsif.movieservice;

import com.yuzarsif.movieservice.model.*;
import com.yuzarsif.movieservice.repository.*;
import com.yuzarsif.movieservice.service.MovieCategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MovieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StarRepository starRepository,
											   MovieRepository movieRepository,
											   WriterRepository writerRepository,
											   DirectorRepository directorRepository,
											   MovieCategoryRepository movieCategoryRepository) {
		return args -> {

			Star star = Star
					.builder()
					.name("name")
					.birthDate(Date.from(Instant.now()))
					.bio("bio")
					.imageUrl("imageUrl")
					.height(1f)
					.bornPlace("bornPlace")
					.build();

			starRepository.save(star);

			Star savedStar = starRepository.findById(1L).get();

			Writer savedWriter = writerRepository.findById(1L).get();

			Director savedDirector = directorRepository.findById(1L).get();

			MovieCategory savedMovieCategory = movieCategoryRepository.findById(1L).get();

			Set<MovieCategory> categories = new HashSet<>();
			categories.add(savedMovieCategory);

			Set<Star> stars = new HashSet<>();
			stars.add(savedStar);

			Set<Writer> writers = new HashSet<>();
			writers.add(savedWriter);

			Set<Director> directors = new HashSet<>();
			directors.add(savedDirector);

			Movie movie = Movie
					.builder()
					.name("name")
					.releaseDate(Date.from(Instant.now()))
					.directors(directors)
					.stars(stars)
					.writers(writers)
					.categories(categories)
					.storyLine("storyLine")
					.build();

			movieRepository.save(movie);
		};
	}
}
