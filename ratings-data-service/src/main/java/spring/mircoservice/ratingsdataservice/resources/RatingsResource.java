package spring.mircoservice.ratingsdataservice.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.mircoservice.ratingsdataservice.models.Rating;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public List<Rating> getUserRating(@PathVariable("userId") String userId){
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 1),
                new Rating("5678", 2)
        );
        return ratings;
    }
}
