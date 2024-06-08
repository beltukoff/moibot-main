package baranow.laba2.telebot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;


@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "callJoke")
@Table(name = "callJoke")


public class CallJoke {

    @Id
    @Column(name = "id_call_joke")
    @GeneratedValue(generator = "id_call_joke_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_call_joke_seq", sequenceName = "id_call_joke_seq", initialValue = 1, allocationSize = 1)
    private Long idCall;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_joke")
    private Joke joke;

    @Column(name = "time_call")
    private Date timeCall;

    @Column(name = "id_user_call")
    private Long idUserCall;

}
