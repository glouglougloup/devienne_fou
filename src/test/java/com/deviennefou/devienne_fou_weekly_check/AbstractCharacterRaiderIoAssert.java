package com.deviennefou.devienne_fou_weekly_check;

import com.deviennefou.devienne_fou_weekly_check.model.CharacterRaiderIo;
import org.assertj.core.api.AbstractObjectAssert;


public abstract class AbstractCharacterRaiderIoAssert<S extends AbstractCharacterRaiderIoAssert<S, A>, A extends CharacterRaiderIo> extends AbstractObjectAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractCharacterRaiderIoAssert}</code> to make assertions on actual CharacterRaiderIo.
   * @param actual the CharacterRaiderIo we want to make assertions on.
   */
  protected AbstractCharacterRaiderIoAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

}
