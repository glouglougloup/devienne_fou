package com.deviennefou.weeklycheck;

import com.deviennefou.weeklycheck.dto.ProfileCharacterRaiderIo;


public class CharacterRaiderIoAssert extends AbstractCharacterRaiderIoAssert<CharacterRaiderIoAssert, ProfileCharacterRaiderIo> {

  public CharacterRaiderIoAssert(ProfileCharacterRaiderIo actual) {
    super(actual, CharacterRaiderIoAssert.class);
  }

  @org.assertj.core.util.CheckReturnValue
  public static CharacterRaiderIoAssert assertThat(ProfileCharacterRaiderIo actual) {
    return new CharacterRaiderIoAssert(actual);
  }

  public CharacterRaiderIoAssert hasName(String name){
    isNotNull();
    if(!actual.name().equals(name)){
      failWithMessage("Expected name to be <%s> but was <%s>", name, actual.name());
    }
    return this;
  }

  public CharacterRaiderIoAssert hasRace(String race){
    isNotNull();
    if(!actual.race().equals(race)){
      failWithMessage("Expected race to be <%s> but was <%s>", race, actual.race());
    }
    return this;
  }

  public CharacterRaiderIoAssert hasWowClass(String wowClass){
    isNotNull();
    if(!actual.wowClass().equals(wowClass)){
      failWithMessage("Expected wow class to be <%s> but was <%s>", wowClass, actual.wowClass());
    }
    return this;
  }

}
