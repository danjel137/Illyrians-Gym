package model.utilities;

import model.operational.db.User;
import org.apache.beam.sdk.coders.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserCoder extends StructuredCoder<User> {

    private static final UserCoder INSTANCE = new UserCoder();
    private static final StringUtf8Coder STRING_UTF_8_CODER = StringUtf8Coder.of();
    private static final BigEndianIntegerCoder BIG_ENDIAN_INT_CODER = BigEndianIntegerCoder.of();

    private static final BooleanCoder BOOLEAN_CODER = BooleanCoder.of();

    public static Coder<User> of() {
        return INSTANCE;
    }

    @Override
    public void encode(User value, OutputStream outStream) throws IOException {

//        if (value.getFirstName() != null
//                && value.getLastName() != null
//                && value.getBirthday() != null
//                && value.getEmail() != null
//                && value.getPassword() != null
//                && value.getUserType() != null
//                && value.getPhoneNumber() != null
//                && value.getTimeDateRegistered() != null
//                && value.getEndTimeSubscription() != null
//        ) {}

        BOOLEAN_CODER.encode(Objects.nonNull(value), outStream);
        if (value != null) {
            BIG_ENDIAN_INT_CODER.encode(value.getUserId(), outStream);
            BIG_ENDIAN_INT_CODER.encode(value.getGymId(), outStream);

            if (value.getFirstName() != null) {
                STRING_UTF_8_CODER.encode(value.getFirstName(), outStream);
            }

            if (value.getLastName() != null) {
                STRING_UTF_8_CODER.encode(value.getLastName(), outStream);
            }

            if (value.getEmail() != null) {
                STRING_UTF_8_CODER.encode(value.getEmail(), outStream);
            }

            if (value.getPassword() != null) {
                STRING_UTF_8_CODER.encode(value.getPassword(), outStream);
            }

            if (value.getUserType() != null) {
                STRING_UTF_8_CODER.encode(value.getUserType(), outStream);
            }

            if (value.getBirthday() != null) {
                STRING_UTF_8_CODER.encode(value.getBirthday(), outStream);
            }

            if (value.getGender() != null) {
                STRING_UTF_8_CODER.encode(value.getGender(), outStream);
            }

            if (value.getPhoneNumber() != null) {
                STRING_UTF_8_CODER.encode(value.getPhoneNumber(), outStream);
            }

            if (value.getTimeDateRegistered() != null) {
                STRING_UTF_8_CODER.encode(value.getTimeDateRegistered(), outStream);
            }

            if (value.getTrainerInstagramAccount() != null) {
                STRING_UTF_8_CODER.encode(value.getTrainerInstagramAccount(), outStream);
            }

            if (value.getTrainerTitle() != null) {
                STRING_UTF_8_CODER.encode(value.getTrainerTitle(), outStream);
            }

            if (value.getTrainerDescription() != null) {
                STRING_UTF_8_CODER.encode(value.getTrainerDescription(), outStream);
            }

            if (value.getEndTimeSubscription() != null) {
                STRING_UTF_8_CODER.encode(value.getEndTimeSubscription(), outStream);
            }
        }
    }

    @Override
    public User decode(InputStream inStream) throws IOException {
        if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
            return User.builder().userId(BIG_ENDIAN_INT_CODER.decode(inStream))
                    .firstName(STRING_UTF_8_CODER.decode(inStream))
                    .lastName(STRING_UTF_8_CODER.decode(inStream))
                    .email(STRING_UTF_8_CODER.decode(inStream))
                    .password(STRING_UTF_8_CODER.decode(inStream))
                    .userType(STRING_UTF_8_CODER.decode(inStream))
                    .birthday(STRING_UTF_8_CODER.decode(inStream))
                    .gender(STRING_UTF_8_CODER.decode(inStream))
                    .phoneNumber(STRING_UTF_8_CODER.decode(inStream))
                    .timeDateRegistered(STRING_UTF_8_CODER.decode(inStream))
                    .timeDateRegistered(STRING_UTF_8_CODER.decode(inStream))
                    .trainerTitle(STRING_UTF_8_CODER.decode(inStream))
                    .trainerInstagramAccount(STRING_UTF_8_CODER.decode(inStream))
                    .trainerTitle(STRING_UTF_8_CODER.decode(inStream))
                    .trainerDescription(STRING_UTF_8_CODER.decode(inStream))
                    .gymId(BIG_ENDIAN_INT_CODER.decode(inStream))
                    .endTimeSubscription(STRING_UTF_8_CODER.decode(inStream)).build();
        }
        return null;
    }

    @Override
    public List<? extends Coder<?>> getCoderArguments() {
        return Collections.emptyList();
    }

    @Override
    public void verifyDeterministic() throws NonDeterministicException {
        STRING_UTF_8_CODER.verifyDeterministic();
        BIG_ENDIAN_INT_CODER.verifyDeterministic();
        BOOLEAN_CODER.verifyDeterministic();
    }
}

