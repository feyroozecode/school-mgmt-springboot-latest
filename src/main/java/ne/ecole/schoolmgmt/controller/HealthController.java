package ne.ecole.schoolmgmt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Santé", description = "Endpoints de vérification de l'état de l'application")
public class HealthController {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/health")
    @Operation(summary = "Vérification de santé", description = "Vérifie l'état de l'application")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", appName);
        response.put("version", appVersion);
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "Application fonctionnelle");
        
        return ResponseEntity.ok(response);
    }
}

