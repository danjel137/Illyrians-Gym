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

//        BIG_ENDIAN_INT_CODER.encode(value.getUserId(), outStream);
//        BIG_ENDIAN_INT_CODER.encode(value.getGymId(), outStream);
//        STRING_UTF_8_CODER.encode(value.getFirstName(), outStream);
//        STRING_UTF_8_CODER.encode(value.getLastName(), outStream);
//        STRING_UTF_8_CODER.encode(value.getEmail(), outStream);
//        STRING_UTF_8_CODER.encode(value.getPassword(), outStream);
//        STRING_UTF_8_CODER.encode(value.getUserType(), outStream);
//        STRING_UTF_8_CODER.encode(value.getBirthday(), outStream);
//        STRING_UTF_8_CODER.encode(value.getGender(), outStream);
//        STRING_UTF_8_CODER.encode(value.getPhoneNumber(), outStream);
//        STRING_UTF_8_CODER.encode(value.getTimeDateRegistered(), outStream);
//        STRING_UTF_8_CODER.encode(value.getTrainerInstagramAccount(), outStream);
//        STRING_UTF_8_CODER.encode(value.getTrainerTitle(), outStream);
//        STRING_UTF_8_CODER.encode(value.getEndTimeSubscription(), outStream);


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

            User user = new User();

            if (BIG_ENDIAN_INT_CODER.decode(inStream) != null) {
                user.setUserId(BIG_ENDIAN_INT_CODER.decode(inStream));
            }

            if (BIG_ENDIAN_INT_CODER.decode(inStream) != null) {
                user.setGymId(BIG_ENDIAN_INT_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setFirstName(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setLastName(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setEmail(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setPassword(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setUserType(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setBirthday(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setGender(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setPhoneNumber(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setTimeDateRegistered(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setTrainerInstagramAccount(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setTrainerTitle(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setTrainerDescription(STRING_UTF_8_CODER.decode(inStream));
            }

            if (STRING_UTF_8_CODER.decode(inStream) != null) {
                user.setEndTimeSubscription(STRING_UTF_8_CODER.decode(inStream));
            }
            return user;
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

