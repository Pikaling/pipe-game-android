package uk.co.plasmabeamgames.pipegame.assertj;

import org.assertj.core.api.AbstractAssert;

import java.util.List;

public class ListAssert extends AbstractAssert<ListAssert, List> {

    public ListAssert(List actual) {
        super(actual, ListAssert.class);
    }

    public static ListAssert assertThat(List actual) {
        return new ListAssert(actual);
    }

    public ListAssert startsWith(List other) {
        isNotNull();

        for (int i = 0; i < other.size(); i++) {
            if (!actual.get(i).equals(other.get(i))) {
                failWithMessage("Expected list item %s to be <%s> but was <%s>", i, other.get(i), actual.get(i));
            }
        }

        return this;
    }

}
