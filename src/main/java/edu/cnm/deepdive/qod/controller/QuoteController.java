package edu.cnm.deepdive.qod.controller;

import edu.cnm.deepdive.qod.model.dao.QuoteRepository;
import edu.cnm.deepdive.qod.model.entity.Quote;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quotes") //this is the identifier for the /sources/{sourceId}/quotes/{quoteId} the is the focus with this /quotes/{quoteId}
public class QuoteController { //Used to access a quote from all sources or just getting a quote single

  private QuoteRepository quoteRepository;

  @Autowired
  public QuoteController(QuoteRepository quoteRepository) {
    this.quoteRepository =  quoteRepository;
  }

  @GetMapping(value = "random", //This lets it know it listening for GetRequests the whole Mapping class refers to get post etc...
      produces = MediaType.APPLICATION_JSON_VALUE) //This lets it know what is returned to the consumer which is declairing the MediaType which is a APPLICATION_JSON_VALUE
  public Quote getRandom() {
    return quoteRepository.findRandom().get();
  }

  //TODO Add method for searching by fragment.

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Quote not found") // This handles those exceptions that are thrown HttpStatus.NOT_FOUND IS the 404
   @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {}

}
