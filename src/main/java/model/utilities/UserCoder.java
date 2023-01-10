package model.utilities;

import model.operational.db.User;
import org.apache.beam.sdk.coders.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

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


        BOOLEAN_CODER.encode(value != null, outStream);

        BIG_ENDIAN_INT_CODER.encode(value.getUserId(), outStream);

        BIG_ENDIAN_INT_CODER.encode(value.getGymId(), outStream);

        if (value.getFirstName() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getFirstName(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getLastName() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getLastName(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getEmail() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getEmail(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getPassword() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getPassword(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getUserType() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getUserType(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getBirthday() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getBirthday(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getGender() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getGender(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getPhoneNumber() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getPhoneNumber(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getTimeDateRegistered() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getTimeDateRegistered(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getTrainerInstagramAccount() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getTrainerInstagramAccount(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getTrainerTitle() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getTrainerTitle(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getTrainerDescription() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getTrainerDescription(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }

        if (value.getEndTimeSubscription() != null) {
            BOOLEAN_CODER.encode(true, outStream);
            STRING_UTF_8_CODER.encode(value.getEndTimeSubscription(), outStream);
        } else {
            BOOLEAN_CODER.encode(false, outStream);
        }


    }

    @Override
    public User decode(InputStream inStream) throws IOException {

        if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {

            User user = new User();

            user.setUserId(BIG_ENDIAN_INT_CODER.decode(inStream));
            user.setGymId(BIG_ENDIAN_INT_CODER.decode(inStream));

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setFirstName(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setLastName(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setEmail(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setPassword(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setUserType(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setBirthday(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setGender(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setPhoneNumber(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setTimeDateRegistered(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setTrainerInstagramAccount(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setTrainerTitle(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
                user.setTrainerDescription(STRING_UTF_8_CODER.decode(inStream));
            }

            if (Boolean.TRUE.equals(BOOLEAN_CODER.decode(inStream))) {
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


//
//public class JsonCoder<T> extends AtomicCoder<T> {
//    private static final long serialVersionUID = -1L;
//
//    private static final Coder<Boolean> BOOLEAN_CODER = BooleanCoder.of();
//    private static final Coder<String> STRING_CODER = StringUtf8Coder.of();
//
//    private Class<T> clazz;
//
//    @Override
//    public void encode(T value, OutputStream outStream) throws IOException {
//        if (value == null) {
//            BOOLEAN_CODER.encode(Boolean.FALSE, outStream);
//        } else {
//            BOOLEAN_CODER.encode(Boolean.TRUE, outStream);
//            STRING_CODER.encode(JsonUtil.toJsonString(value), outStream);
//        }
//    }
//
//    @Override
//    @Nullable
//    public T decode(InputStream inStream) throws IOException {
//        if (BOOLEAN_CODER.decode(inStream)) {
//            return JsonUtil.serializeToObject(STRING_CODER.decode(inStream), clazz);
//        } else {
//            return null;
//        }
//    }
//
//    public static <T> JsonCoder<T> of(Class<T> clazz) {
//        return new JsonCoder<>(clazz);
//    }
//}

