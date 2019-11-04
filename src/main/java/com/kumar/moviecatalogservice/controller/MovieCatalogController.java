/**
 * 
 */
package com.kumar.moviecatalogservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kumar.moviecatalogservice.model.CatalogItem;
import com.kumar.moviecatalogservice.model.MovieItem;
import com.kumar.moviecatalogservice.model.Rating;

/**
 * @author kumhosur
 *
 */
@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(String userId) {

		List<Rating> ratings = Arrays.asList(new Rating("1234", 5), new Rating("3456", 4));
		
		return ratings.stream().map(rating -> {
			MovieItem movie = restTemplate.getForObject("http://localhost:8082/movieinfo/" + rating.getMovieId(),
					MovieItem.class);

			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
		}).collect(Collectors.toList());
	}
}
