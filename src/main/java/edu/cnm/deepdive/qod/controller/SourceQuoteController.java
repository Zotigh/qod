package edu.cnm.deepdive.qod.controller;

import edu.cnm.deepdive.qod.model.dao.QuoteRepository;
import edu.cnm.deepdive.qod.model.dao.SourceRepository;
import edu.cnm.deepdive.qod.model.entity.Quote;
import edu.cnm.deepdive.qod.model.entity.Source;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sources/{sourceId}/quotes")
public class SourceQuoteController {

  private SourceRepository sourceRepository;
  private QuoteRepository quoteRepository;

  @Autowired
  public SourceQuoteController(SourceRepository sourceRepository,
      QuoteRepository quoteRepository) {
    this.sourceRepository = sourceRepository;
    this.quoteRepository = quoteRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Quote> list(@PathVariable("sourceId") UUID sourceId) {
    return sourceRepository.findById(sourceId).get().getQuotes();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Quote post(@PathVariable("sourceId") UUID sourceId, @RequestBody Quote quote) {
    Source source = sourceRepository.findById(sourceId).get();
    quote.setSource(source);
    quoteRepository.save(quote);
    return quote;
  }

  @GetMapping(value = "{quoteId}", produces = MediaType.APPLICATION_JSON_VALUE)//the braces are so you can refer to it later it now looks at /source/{sourceId]/quotes/{quoteId}
  public Quote get(@PathVariable("sourceId") UUID sourceId,
      @PathVariable("quoteId") UUID quoteId) {     //the @PathVariable lets the request convert the quoteId and the sourceId to a string
    Source source = sourceRepository.findById(sourceId).get(); //this causes a no such error exception and spring helps us with that
    return quoteRepository.findBySourceAndId(source, quoteId).get(); //Either one of these can turn an exception Spring takes care of this down below
  }


  @DeleteMapping(value = "{quoteId}")
  @ResponseStatus(HttpStatus.NO_CONTENT) //This is telling the compiler to throw a 206 which is a delete
  public void delete(
      @PathVariable("sourceId") UUID sourceId, @PathVariable("quoteId") UUID quoteId) {
    Quote quote = get(sourceId, quoteId);
    quoteRepository.delete(quote);
  }


  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Source or quote not found") // This handles those exceptions that are thrown
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {}

}
