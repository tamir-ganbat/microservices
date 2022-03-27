package spring.mircoservice.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import spring.mircoservice.moviecatalogservice.models.CatalogItem;
import spring.mircoservice.moviecatalogservice.models.Movie;
import spring.mircoservice.moviecatalogservice.models.Rating;
import spring.mircoservice.moviecatalogservice.models.UserRating;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    @Qualifier("internalTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // list of rating
        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);

        return userRating.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
            // putting them all together
            return new CatalogItem(movie.getName(), "Test Movie", rating.getRating());
        }).collect(Collectors.toList());
        /*
        return Collections.singletonList(
                new CatalogItem("Avengers", "Test Movie", 4)
        );*/

    }

    /*

    webclient call webservice
        Movie movie = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/movies/" + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
        */
}
