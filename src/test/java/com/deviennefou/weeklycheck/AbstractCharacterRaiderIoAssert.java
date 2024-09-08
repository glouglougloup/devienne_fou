package com.deviennefou.weeklycheck;

import com.deviennefou.weeklycheck.dto.ProfileCharacterRaiderIo;
import org.assertj.core.api.AbstractObjectAssert;


public abstract class AbstractCharacterRaiderIoAssert<S extends AbstractCharacterRaiderIoAssert<S, A>, A extends ProfileCharacterRaiderIo> extends AbstractObjectAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractCharacterRaiderIoAssert}</code> to make assertions on actual CharacterRaiderIo.
   * @param actual the CharacterRaiderIo we want to make assertions on.
   */
  protected AbstractCharacterRaiderIoAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

}
