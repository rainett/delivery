package io.rainett.deliverybackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    @GetMapping
    public ResponseEntity<List<String>> getContents() {
        List<String> contents = List.of(
                "The first message",
                "The very second message",
                "Not the last message",
                "This could be the last message",
                "The fifth message",
                "Finally, the last one"
        );
        return ResponseEntity.ok(contents);
    }

}
