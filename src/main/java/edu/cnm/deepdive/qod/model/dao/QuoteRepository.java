package edu.cnm.deepdive.qod.model.dao;

import edu.cnm.deepdive.qod.model.entity.Quote;
import edu.cnm.deepdive.qod.model.entity.Source;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Quote, UUID> {   //these are like DAOs just smarter

  Optional<Quote> findBySourceAndId(Source source, UUID quoteID); //It knows we have a source called source and a UUID called quote id by the parameters given to it

  @Query(value = "SELECT * FROM quote ORDER BY RANDOM() OFFSET 0 ROWS FETCH FIRST 1 ROW ONLY", //This takes all the coloums Orders them and finds a random one
  nativeQuery = true) //This says do not compile with jpa just send this to the server nativeQuery = true
  Optional<Quote> findRandom(); //The above can be said in multiple ways

  List<Quote> findAllByTextContainingOrderByTextAsc(String fragment); //this orders by text that will search by some portion of the quote

}
