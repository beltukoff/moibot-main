package baranow.laba2.telebot.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Jokes2.0")
@Table(name = "Jokes2.0")

public class Joke{
    @Id
    @Column(name = "id_joke")
    @GeneratedValue(generator = "id_joke_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_joke_seq", sequenceName = "id_joke_seq", initialValue = 1, allocationSize = 1)
    private Long idJoke;

    @Column(name = "text_joke")
    private String textJoke;

    @CreationTimestamp
    @Column(name = "date_add_joke")
    private LocalDateTime dateAddJoke;

    @UpdateTimestamp
    @Column(name = "date_changes_joke")
    private LocalDateTime dateChangesJoke;

    @Column(name = "count_call")
    private Long countCall = 0L;

    @OneToMany(mappedBy = "joke", cascade = CascadeType.ALL)
    private List<CallJoke> callJokes;

}
