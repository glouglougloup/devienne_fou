package com.deviennefou.devienne_fou_weekly_check;

import com.deviennefou.devienne_fou_weekly_check.model.CharacterRaiderIo;
import com.deviennefou.devienne_fou_weekly_check.model.MemberRaiderIo;
import com.deviennefou.devienne_fou_weekly_check.service.DevienneFouService;
import com.deviennefou.devienne_fou_weekly_check.service.RaiderIoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.deviennefou.devienne_fou_weekly_check.CharacterRaiderIoAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RaiderIoServiceTest {

    @Mock
    RaiderIoService raiderIOService;

    @InjectMocks
    DevienneFouService devienneFouService;

    @Test
    void retrieveInformationAboutGuildMembersReturnA200() {
        String mockJson = createMockResponseRetrieveGuildMembersByRankWith3Members200();
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockJson, HttpStatus.OK);

        when(raiderIOService.getMembersOfGuildFromRealmInRegion(any(), any(), any())).thenReturn(mockResponseEntity);

        Optional<List<MemberRaiderIo>> membersList = devienneFouService.getMembersWithRanks(1);

        assertThat(membersList).isPresent();
        assertThat(membersList.get()).hasSize(1);
        assertThat(membersList.get().getFirst().character().name()).isEqualTo("Moghiro");

        verify(raiderIOService, times(1)).getMembersOfGuildFromRealmInRegion(any(), any(), eq("devienne fou"));
    }

    @Test
    void retrieveInformationAboutGuildMembersReturnA400() {
        String mockJson = createMockResponse400();
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockJson, HttpStatus.BAD_REQUEST);

        when(raiderIOService.getMembersOfGuildFromRealmInRegion(any(), any(), any())).thenReturn(mockResponseEntity);

        Optional<List<MemberRaiderIo>> membersList = devienneFouService.getMembersWithRanks(1);

        assertThat(membersList).isEmpty();

        verify(raiderIOService, times(1)).getMembersOfGuildFromRealmInRegion(any(), any(), eq("devienne fou"));
    }

    @Test
    void retrieveInformationAboutAPlayerReturnA200() {
        String mockJson = createMockResponseGetProfile1Player200();
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockJson, HttpStatus.OK);

        when(raiderIOService.getPlayerProfile(any(), any(), any())).thenReturn(mockResponseEntity);

        Optional<CharacterRaiderIo> character = devienneFouService.getProfile("Moghiro");

        //AssertJ Extracting
        assertThat(character).isPresent()
                .get()
                .extracting(CharacterRaiderIo::name,
                        CharacterRaiderIo::race,
                        CharacterRaiderIo::wowClass)
                        .containsExactly("Moghiro", "Night Elf", "Druid");

        //Custom Assertion class generated with assertj plugin
        assertThat(character.get())
                .hasName("Moghiro")
                .hasRace("Night Elf")
                .hasWowClass("Druid");

        assertThat(character.get().mythic_plus_weekly_highest_level_runs()).isEmpty();
        assertThat(character.get().mythic_plus_previous_weekly_highest_level_runs()).isNotEmpty();
        assertThat(character.get().mythic_plus_previous_weekly_highest_level_runs()).hasSize(2);

        verify(raiderIOService, times(1)).getPlayerProfile(any(),any(),any());
    }

    private static String createMockResponseRetrieveGuildMembersByRankWith3Members200() {
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
    private static String createMockResponseGetProfile1Player200() {
        return """
                            {
                              "name": "Moghiro",
                              "race": "Night Elf",
                              "class": "Druid",
                              "active_spec_name": "Balance",
                              "active_spec_role": "DPS",
                              "gender": "female",
                              "faction": "alliance",
                              "achievement_points": 26900,
                              "honorable_kills": 0,
                              "thumbnail_url": "https://render.worldofwarcraft.com/eu/character/chogall/242/186663154-avatar.jpg?alt=/wow/static/images/2d/avatar/4-1.jpg",
                              "region": "eu",
                              "realm": "Cho'gall",
                              "last_crawled_at": "2024-07-22T01:58:40.000Z",
                              "profile_url": "https://raider.io/characters/eu/chogall/Moghiro",
                              "profile_banner": "alliancebanner1",
                              "mythic_plus_weekly_highest_level_runs": [],
                              "mythic_plus_previous_weekly_highest_level_runs": [
                                {
                                  "dungeon": "The Azure Vault",
                                  "short_name": "AV",
                                  "mythic_level": 13,
                                  "completed_at": "2024-07-25T18:38:10.000Z",
                                  "clear_time_ms": 1525631,
                                  "par_time_ms": 2250999,
                                  "num_keystone_upgrades": 2,
                                  "map_challenge_mode_id": 401,
                                  "zone_id": 13954,
                                  "score": 195,
                                  "affixes": [
                                    {
                                      "id": 10,
                                      "name": "Fortified",
                                      "description": "Non-boss enemies have 20% more health and inflict up to 30% increased damage.",
                                      "icon": "ability_toughness",
                                      "wowhead_url": "https://wowhead.com/affix=10"
                                    },
                                    {
                                      "id": 135,
                                      "name": "Afflicted",
                                      "description": "While in combat, afflicted souls periodically appear and seek the aid of players.",
                                      "icon": "spell_misc_emotionsad",
                                      "wowhead_url": "https://wowhead.com/affix=135"
                                    },
                                    {
                                      "id": 6,
                                      "name": "Raging",
                                      "description": "Non-boss enemies enrage at 30% health remaining, temporarily granting immunity to crowd control effects.",
                                      "icon": "ability_warrior_focusedrage",
                                      "wowhead_url": "https://wowhead.com/affix=6"
                                    }
                                  ],
                                  "url": "https://raider.io/mythic-plus-runs/season-df-4/11384734-13-the-azure-vault"
                                },
                                {
                                  "dungeon": "Ruby Life Pools",
                                  "short_name": "RLP",
                                  "mythic_level": 12,
                                  "completed_at": "2024-07-25T19:06:22.000Z",
                                  "clear_time_ms": 1161716,
                                  "par_time_ms": 1800999,
                                  "num_keystone_upgrades": 2,
                                  "map_challenge_mode_id": 399,
                                  "zone_id": 14063,
                                  "score": 188.4,
                                  "affixes": [
                                    {
                                      "id": 10,
                                      "name": "Fortified",
                                      "description": "Non-boss enemies have 20% more health and inflict up to 30% increased damage.",
                                      "icon": "ability_toughness",
                                      "wowhead_url": "https://wowhead.com/affix=10"
                                    },
                                    {
                                      "id": 135,
                                      "name": "Afflicted",
                                      "description": "While in combat, afflicted souls periodically appear and seek the aid of players.",
                                      "icon": "spell_misc_emotionsad",
                                      "wowhead_url": "https://wowhead.com/affix=135"
                                    },
                                    {
                                      "id": 6,
                                      "name": "Raging",
                                      "description": "Non-boss enemies enrage at 30% health remaining, temporarily granting immunity to crowd control effects.",
                                      "icon": "ability_warrior_focusedrage",
                                      "wowhead_url": "https://wowhead.com/affix=6"
                                    }
                                  ],
                                  "url": "https://raider.io/mythic-plus-runs/season-df-4/11386337-12-ruby-life-pools"
                                }
                              ]
                }
                            }""";
    }
    private static String createMockResponse400() {
        return """
                {
                  "statusCode": 400,
                  "error": "Bad Request",
                  "message": "Failed to find realm cho'gall in region eu"
                }""";
    }
}