package lucadipietro.U5_W2_D4.payloads;

import java.util.UUID;

public record NewBlogPostResponseDTO(String categoria, String titolo, String contenuto, int tempoDiLettura, UUID autoreId) {
}
