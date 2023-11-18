package ro.ubbcluj.cs.map.domain.validators;

import ro.ubbcluj.cs.map.domain.Friendship;

import java.util.Objects;

public class FriendshipValidator implements Validator<Friendship> {

    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (Objects.equals(entity.getUser1_id(), entity.getUser2_id()))
            throw new ValidationException("Error - the IDs are identical");
    }
}
