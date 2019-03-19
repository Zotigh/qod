package edu.cnm.deepdive.qod.controller;

import edu.cnm.deepdive.qod.model.dao.SourceRepository;
import edu.cnm.deepdive.qod.model.entity.Source;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sources")
public class SourceController {

  private SourceRepository sourceReposirory;

  @Autowired
  public SourceController(SourceRepository sourceReposirory) {
    this.sourceReposirory = sourceReposirory;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Source> list() {
    return sourceReposirory.findAllByOrderByNameAsc();
  }

  @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Source> search(@RequestParam("q") String fragment) {
  return sourceReposirory.findAllByNameContainingOrderByNameAsc(fragment);
  }

  @GetMapping(value = "{sourceId}",produces = MediaType.APPLICATION_JSON_VALUE) //the curley braces is a place holder
  public Source get(@PathVariable("sourceId")UUID sourceId) {
    return sourceReposirory.findById(sourceId).get();//every CRUD repository has a findById
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public Source post(@RequestBody Source source) {
    sourceReposirory.save(source);
    return source;//a post should always return the object that was created
  }

  @DeleteMapping(value = "{sourceId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)//This tells the consumer dont expect anything back from the response
  public void delete(@PathVariable("sourceId") UUID sourceId) {
    sourceReposirory.delete(get(sourceId));
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Source not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {}

}
