package com.deviennefou.devienne_fou_weekly_check;

//Useful when having multiple custom assertions classes
public class Assertions {

  @org.assertj.core.util.CheckReturnValue
  public static com.deviennefou.devienne_fou_weekly_check.CharacterRaiderIoAssert assertThat(com.deviennefou.devienne_fou_weekly_check.model.CharacterRaiderIo actual) {
    return new com.deviennefou.devienne_fou_weekly_check.CharacterRaiderIoAssert(actual);
  }


  protected Assertions() {
    // empty
  }
}
