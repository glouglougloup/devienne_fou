package com.deviennefou.devienne_fou_weekly_check;

import com.deviennefou.devienne_fou_weekly_check.model.CharacterRaiderIo;
import com.deviennefou.devienne_fou_weekly_check.model.MemberRaiderIo;
import com.deviennefou.devienne_fou_weekly_check.service.DevienneFouService;
import com.deviennefou.devienne_fou_weekly_check.service.RaiderIOService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RaiderIOServiceTest {

    @Mock
    RaiderIOService raiderIOService;

    @InjectMocks
    DevienneFouService devienneFouService;

    List<MemberRaiderIo> dataMembersList;

    @BeforeEach
    void setUp() {
        dataMembersList = new ArrayList<>();
        dataMembersList.add(new MemberRaiderIo(0, new CharacterRaiderIo("Moghiro", "Night Elf", "Druid", "https://raider.io/characters/eu/chogall/Moghiro")));
        dataMembersList.add(new MemberRaiderIo(1, new CharacterRaiderIo("Dingqt", "Night Elf", "Priest", "https://raider.io/characters/eu/chogall/Dingqt")));
        dataMembersList.add(new MemberRaiderIo(2, new CharacterRaiderIo("Nightsorrow", "Blood Elf", "Death Knight", "https://raider.io/characters/eu/chogall/Nightsorrow")));
    }

    @Test
    void whenTheResponseFromApiIs200() {
        String mockJson = createMockResponseWith3Members();
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockJson, HttpStatus.OK);

        when(raiderIOService.getMembersFromGuildOfRealm(any(), any(), any())).thenReturn(mockResponseEntity);

        Optional<List<MemberRaiderIo>> membersList = devienneFouService.getMembersWithRanks(1);

        assertThat(membersList).isPresent();
        assertThat(membersList.get()).hasSize(1);
        assertThat(membersList.get().getFirst().character().name()).isEqualTo("Moghiro");

        verify(raiderIOService, times(1)).getMembersFromGuildOfRealm(any(), any(), eq("devienne fou"));
    }

    @Test
    void whenTheResponseFromApiIs400() {
        String mockJson = createMockResponse400();
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockJson, HttpStatus.BAD_REQUEST);

        when(raiderIOService.getMembersFromGuildOfRealm(any(), any(), any())).thenReturn(mockResponseEntity);

        Optional<List<MemberRaiderIo>> membersList = devienneFouService.getMembersWithRanks(1);

        assertThat(membersList).isEmpty();

        verify(raiderIOService, times(1)).getMembersFromGuildOfRealm(any(), any(), eq("devienne fou"));
    }

    private static String createMockResponse400(){
        return """
                {
                  "statusCode": 400,
                  "error": "Bad Request",
                  "message": "Failed to find realm cho'gall in region eu"
                }""";
    }
    private static String createMockResponseWith3Members() {
        return """
                {
                	"name": "DEVIENNE FOU",
                	"faction": "horde",
                	"region": "eu",
                	"realm": "Cho'gall",
                	"last_crawled_at": "2024-07-21T23:37:02.000Z",
                	"profile_url": "https://raider.io/guilds/eu/chogall/DEVIENNE%20FOU",
                	"members": [
                		{
                			"rank": 1,
                			"character": {
                				"name": "Moghiro",
                				"race": "Night Elf",
                				"class": "Druid",
                				"active_spec_name": "Balance",
                				"active_spec_role": "DPS",
                				"gender": "female",
                				"faction": "alliance",
                				"achievement_points": 26900,
                				"honorable_kills": 0,
                				"region": "eu",
                				"realm": "Cho'gall",
                				"last_crawled_at": "2024-07-22T01:58:40.000Z",
                				"profile_url": "https://raider.io/characters/eu/chogall/Moghiro",
                				"profile_banner": "alliancebanner1"
                			}
                		},
                		{
                			"rank": 3,
                			"character": {
                				"name": "Dingqt",
                				"race": "Night Elf",
                				"class": "Priest",
                				"active_spec_name": "Shadow",
                				"active_spec_role": "DPS",
                				"gender": "female",
                				"faction": "alliance",
                				"achievement_points": 30885,
                				"honorable_kills": 0,
                				"region": "eu",
                				"realm": "Cho'gall",
                				"last_crawled_at": "2024-06-29T02:15:26.000Z",
                				"profile_url": "https://raider.io/characters/eu/chogall/Dingqt",
                				"profile_banner": "alliancebanner1"
                			}
                		},
                		{
                			"rank": 3,
                			"character": {
                				"name": "Nightsorrow",
                				"race": "Blood Elf",
                				"class": "Death Knight",
                				"active_spec_name": "Unholy",
                				"active_spec_role": "DPS",
                				"gender": "female",
                				"faction": "horde",
                				"achievement_points": 15845,
                				"honorable_kills": 0,
                				"region": "eu",
                				"realm": "Cho'gall",
                				"last_crawled_at": "2024-06-11T07:06:20.000Z",
                				"profile_url": "https://raider.io/characters/eu/chogall/Nightsorrow",
                				"profile_banner": "classbanner_death-knight1"
                			}
                		}
                	]
                }""";
    }
}
