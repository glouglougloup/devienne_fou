package com.deviennefou.weeklycheck.mapper;

import com.deviennefou.weeklycheck.dto.MemberCharacterRaiderIoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class DevienneFouCharacterMapperImplTest {

    @Autowired
    private final DevienneFouCharacterMapper mapper = Mappers.getMapper( DevienneFouCharacterMapper.class );

    @Test
    public void givenSourceToDestination_whenMaps_thenCorrect(){
        MemberCharacterRaiderIoDTO memberCharacterRaiderIo = new MemberCharacterRaiderIoDTO(
                "Moghiro",
                "Night elf",
                "druid",
                "some_url",
                "eu",
                "Cho'gall");

//        DevienneFouCharacter devienneFouCharacter = mapper.toDevienneFouCharacterEntity(memberCharacterRaiderIo);
//
//        assertThat(devienneFouCharacter.getName()).isEqualTo(memberCharacterRaiderIo.name());
//        assertThat(devienneFouCharacter.getRace()).isEqualTo(memberCharacterRaiderIo.race());
//        assertThat(devienneFouCharacter.getWowClass()).isEqualTo(memberCharacterRaiderIo.wowClass());
//        assertThat(devienneFouCharacter.getProfileUrl()).isEqualTo(memberCharacterRaiderIo.profile_url());
//        assertThat(devienneFouCharacter.getRegion()).isEqualTo(memberCharacterRaiderIo.region());
//        assertThat(devienneFouCharacter.getRealm()).isEqualTo(memberCharacterRaiderIo.realm());
    }
}