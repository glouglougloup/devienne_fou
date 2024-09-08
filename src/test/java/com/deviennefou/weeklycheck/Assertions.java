package com.deviennefou.weeklycheck;

import com.deviennefou.weeklycheck.dto.ProfileCharacterRaiderIo;

//Useful when having multiple custom assertions classes
public class Assertions {

  @org.assertj.core.util.CheckReturnValue
  public static com.deviennefou.weeklycheck.CharacterRaiderIoAssert assertThat(ProfileCharacterRaiderIo actual) {
    return new com.deviennefou.weeklycheck.CharacterRaiderIoAssert(actual);
  }


  protected Assertions() {
    // empty
  }
}
