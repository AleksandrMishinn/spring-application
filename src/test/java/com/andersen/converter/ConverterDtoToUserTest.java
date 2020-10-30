package com.andersen.converter;

import com.andersen.domain.User;
import com.andersen.dto.UserDto;
import junit.framework.TestCase;
import org.junit.Assert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ConverterDtoToUserTest extends TestCase {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserDto userDto = new UserDto("Aleksandr", "ADMIN", 1, bCryptPasswordEncoder.encode("123"), "Sashok55");
    private final Converter<UserDto, User> converter = new ConverterDtoToUser(bCryptPasswordEncoder);

    public void testConvert() {
        User user = converter.convert(userDto);
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("Aleksandr", user.getName());
        Assert.assertEquals("Sashok55", user.getUsername());
    }
}