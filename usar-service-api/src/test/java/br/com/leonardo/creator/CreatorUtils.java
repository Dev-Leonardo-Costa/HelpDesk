package br.com.leonardo.creator;

import lombok.experimental.UtilityClass;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@UtilityClass
public class CreatorUtils {

    private static final PodamFactory podamFactory = new PodamFactoryImpl();

    public static <T> T generateMock(final Class<T> clazz) {
        return podamFactory.manufacturePojo(clazz);
    }

}
