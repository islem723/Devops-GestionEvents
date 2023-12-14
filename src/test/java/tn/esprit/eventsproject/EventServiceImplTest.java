package tn.esprit.eventsproject;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;

import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;


import java.util.*;

import static tn.esprit.eventsproject.entities.Tache.INVITE;
import static tn.esprit.eventsproject.entities.Tache.ORGANISATEUR;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {
    @Mock
    private ParticipantRepository participantRepository;
    @Mock
    private LogisticsRepository logisticsRepository;

    @Mock
    private EventRepository eventRepository;

    private EventServicesImpl eventServices;

    private Participant participant, p1, p2;

    private Event event;

    @BeforeEach
    void setUp() {
        eventServices = new EventServicesImpl(eventRepository, participantRepository, logisticsRepository);

        participant = new Participant(1, "islem", "naffeti", INVITE, null);


        p1 = new Participant(1, "naffeti", "islem", INVITE, null);
        p2 = new Participant(2, "ben foulena", "foulena", ORGANISATEUR, null);

    }

    @Test
    @Order(1)
    @DisplayName("ajouter un participant")
    void testAddParticipant() {
        Mockito.when(participantRepository.save(participant)).thenReturn(participant);

        Participant result = eventServices.addParticipant(participant);

        Assertions.assertEquals(participant, result);

        Mockito.verify(participantRepository).save(participant);
    }


    @Test
    @Order(2)
    @DisplayName("Add event")
    public void testAddAffectEvenParticipant() {

        Event event = new Event();
        Set<Participant> participants = new HashSet<>();
        Participant participant = new Participant();
        participant.setIdPart(1);
        participants.add(participant);
        event.setParticipants(participants);

        Mockito.when(participantRepository.findById(1)).thenReturn(Optional.of(participant));


        Mockito.when(eventRepository.save(event)).thenReturn(event);


        Event resultEvent = eventServices.addAffectEvenParticipant(event);


        Mockito.verify(participantRepository, Mockito.times(1)).findById(1);


        Mockito.verify(eventRepository, Mockito.times(1)).save(event);


        Assertions.assertNotNull(resultEvent);

    }
}

