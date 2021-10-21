package com.arty.springVueConference.controllers;

import com.arty.springVueConference.domains.Presentation;
import com.arty.springVueConference.domains.Room;
import com.arty.springVueConference.repos.IPresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/presentations")
public class PresentationController
{
    @Autowired
    private IPresentationRepository presentationRepository;

    @GetMapping("/getAllPresentations")
    @PreAuthorize("hasAuthority('presentation:read')")
    public ResponseEntity<Map<String, Object>> getAllPresentations(@RequestParam(required = false) String searchStr,
                                                                 @RequestParam(defaultValue="0") int page,
                                                                 @RequestParam(defaultValue="3") int pageSize){
        try {
            Pageable paging = PageRequest.of(page, pageSize);

            Page<Presentation> presentations = null;

            if (searchStr == null || searchStr.length() == 0)
                presentations = presentationRepository.findAll(paging);
            else
                presentations = presentationRepository.findPresentationByTitleContaining(searchStr, paging);

            if (presentations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("presentations", presentations.getContent());
            response.put("currentPage", presentations.getTotalPages());
            response.put("totalItems", presentations.getTotalElements());
            response.put("totalPages", presentations.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAllPresentations")
    @PreAuthorize("hasAuthority('presentation:write')")
    public ResponseEntity<Presentation> deleteAllPresentations(){
        try {
            presentationRepository.deleteAll();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createPresentation")
    @PreAuthorize("hasAuthority('presentation:write')")
    public ResponseEntity<Presentation> createPresentation(@RequestBody Presentation presentation){
        try {
            Presentation _presentation = presentationRepository.save(presentation);
            return new ResponseEntity<>(_presentation, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPresentationById/{id}")
    @PreAuthorize("hasAuthority('presentation:read')")
    public ResponseEntity<Presentation> getPresentationById(@PathVariable("id") Long id){
        try {
            Presentation presentation = presentationRepository.getById(id);
            if (presentation != null) {
                return new ResponseEntity<>(presentation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePresentationById/{id}")
    @PreAuthorize("hasAuthority('presentation:write')")
    public ResponseEntity<Presentation> deletePresentationById(@PathVariable("id") Long id){
        try {
            presentationRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePresentation/{id}")
    @PreAuthorize("hasAuthority('presentation:write')")
    public ResponseEntity<Presentation> updatePresentation(@PathVariable("id") Long id, @RequestBody Presentation presentation){
        try {
            Presentation newPresentation = presentationRepository.getById(id);
            if(newPresentation == null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


            if (presentation != null) {
                newPresentation.setDescription(presentation.getDescription());
                newPresentation.setRoom(presentation.getRoom());
                newPresentation.setSubject(presentation.getSubject());
                newPresentation.setTitle(presentation.getTitle());
                newPresentation.setLasts(presentation.getLasts());
                newPresentation.setDate(presentation.getDate());
                newPresentation.setPresenters(presentation.getPresenters());
                newPresentation.setStartTime(presentation.getStartTime());
                newPresentation = presentationRepository.save(newPresentation);
            }

            return new ResponseEntity<>(newPresentation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getPresentationsByRoom")
    @PreAuthorize("hasAuthority('presentation:read')")
    public ResponseEntity<Map<String, Object>> getPresentationsByRoom(@RequestBody Room room,
                                                                      @RequestParam(defaultValue="0") int page,
                                                                     @RequestParam(defaultValue="10") int pageSize) {
        try {

            Pageable paging = PageRequest.of(page, pageSize);

            Page<Presentation> presentations = null;

            presentations = presentationRepository.findPresentationsByRoom(room, paging);

            if (presentations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("presentations", presentations.getContent());
            response.put("currentPage", presentations.getTotalPages());
            response.put("totalItems", presentations.getTotalElements());
            response.put("totalPages", presentations.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/IsDateTimeUnique")
    @PreAuthorize("hasAuthority('presentation:read')")
    public ResponseEntity<String> isDateTimeUnique() {
        try {
            return new ResponseEntity<>("sad", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
