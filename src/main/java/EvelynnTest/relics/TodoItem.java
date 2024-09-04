package EvelynnTest.relics;

import EvelynnTest.EvelynnTestChar;

import static EvelynnTest.EvelynnTestMod.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, EvelynnTestChar.Enums.EVELYNN_TEST_COLOR);
    }
}
