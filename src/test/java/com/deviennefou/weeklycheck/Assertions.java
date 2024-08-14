package com.deviennefou.weeklycheck;

//Useful when having multiple custom assertions classes
public class Assertions {

  @org.assertj.core.util.CheckReturnValue
  public static com.deviennefou.weeklycheck.CharacterRaiderIoAssert assertThat(com.deviennefou.weeklycheck.model.CharacterRaiderIo actual) {
    return new com.deviennefou.weeklycheck.CharacterRaiderIoAssert(actual);
  }


  protected Assertions() {
    // empty
  }
}
