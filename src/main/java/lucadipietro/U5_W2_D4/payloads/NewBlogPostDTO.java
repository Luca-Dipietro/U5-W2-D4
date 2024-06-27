package lucadipietro.U5_W2_D4.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewBlogPostDTO(
        @NotEmpty(message = "La categoria è un dato obbligatorio!")
        @Size(min = 3, max = 15, message = "La categoria deve essere compresa tra i 3 ed i 15 caratteri!") String categoria,
        @NotEmpty(message = "Il titolo è un dato obbligatorio!")
        @Size(min = 3, max = 25, message = "Il titolo deve essere compreso tra i 3 ed i 25 caratteri!") String titolo,
        @NotEmpty(message = "Il contenuto è un dato obbligatorio!")
        @Size(min = 10, max=100, message = "Il contenuto deve essere compreso tra i 10 ed i 100 caratteri!") String contenuto,
        @NotNull(message = "Il tempo di lettura è un dato obbligatorio!")
        @Min(value = 1, message = "Il tempo di lettura deve essere almeno di 1 minuto!") int tempoDiLettura,
        UUID autoreId)
{ }
